package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client;

import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.ApiKeyResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * The client provides methods to access ODH. It is intended for
 * internal usage.
 * <p>
 * Note that the provided username and password are kept in-memory,
 * since the client may need to request new ODH API keys at any
 * time. This may pose a security risk. Please take that into
 * consideration when using this class.
 */
public class OdhClientImpl implements OdhClient {

    // The Sonar warning "'PASSWORD' detected in this expression" can be ignored
    @SuppressWarnings({"squid:S2068"})
    private static final String PASSWORD_PARAM_NAME = "password";
    private static final String USERNAME_PARAM_NAME = "username";
    private static final String GRANT_TYPE_PARAM_NAME = "grant_type";

    private static final String GRANT_TYPE = "password";

    private static final String AUTHENTICATION_PATH = "token";

    private final String username;
    private final String password;
    private final WebTarget webTarget;

    private String apiKey = "UNDEFINED";

    public OdhClientImpl(String baseUrl, String username, String password) {
        this.username = username;
        this.password = password;

        Client client = ClientBuilder.newClient();
        this.webTarget = client.target(baseUrl);
    }

    /**
     * Fetch the resource of type <code>T</code> from ODH.
     * <p>
     * The resource is fetched from the given <code>path</code> using the specified
     * HTTP <code>method</code>. The <code>queryParams</code> are appended to the
     * request URL, the <code>body</code> is used as request body.
     * <p>
     * If the request fails with a {@link Response.Status#UNAUTHORIZED} HTTP status code,
     * an automatic authentication is attempted exactly once. If the authentication succeeds,
     * the request is repeated using the new API token and its result is returned. If the
     * repeated request fails again, no matter the cause, it won't be retried.
     *
     * @param path        this path is used to fetch the resource
     * @param method      use this HTTP request method, e.g. GET, POST, PUT, ...
     * @param queryParams queryParams are appended to the URL
     * @param body        this {@link Entity} is send as request body
     * @param genericType the genericType is useful for more advanced use cases, e.g. when
     *                    the result is expected to be a List of type <code>T</code>. Take
     *                    a look at {@link #fetch(String, String, Map, Entity, Class)} if you just want to
     *                    provide a class as type parameter
     * @param <T>         result type
     * @return the response body as instance of <code>T</code> if successful
     */
    @Override
    public <T> T fetch(String path, String method, Map<String, String> queryParams, Entity<?> body, GenericType<T> genericType) {
        return this.fetchWithAutomaticAuthentication(path, method, queryParams, body).readEntity(genericType);
    }

    /**
     * Fetch the resource of type <code>T</code> from ODH.
     * <p>
     * The resource is fetched from the given <code>path</code> using the specified
     * HTTP <code>method</code>. The <code>queryParams</code> are appended to the
     * request URL, the <code>body</code> is used as request body.
     * <p>
     * If the request fails with a {@link Response.Status#UNAUTHORIZED} HTTP status code,
     * an automatic authentication is attempted exactly once. If the authentication succeeds,
     * the request is repeated using the new API token and its result is returned. If the
     * repeated request fails again, no matter the cause, it won't be retried.
     *
     * @param path        this path is used to fetch the resource
     * @param method      use this HTTP request method, e.g. GET, POST, PUT, ...
     * @param queryParams queryParams are appended to the URL
     * @param body        this {@link Entity} is send as request body
     * @param resultClass the resultClass defines the class of the result type. Take
     *                    a look at {@link #fetch(String, String, Map, Entity, GenericType)} for more
     *                    complex cases e.g. List of type <code>T</code>
     * @param <T>         result type
     * @return the response body as instance of <code>T</code> if successful
     */
    @Override
    public <T> T fetch(String path, String method, Map<String, String> queryParams, Entity<?> body, Class<T> resultClass) {
        return this.fetchWithAutomaticAuthentication(path, method, queryParams, body).readEntity(resultClass);
    }

    /**
     * Fetch an ODH API key with <code>username</code> and <code>password</code> as credentials.
     * <p>
     * On success, this method returns an ODH API key that can be used for further authenticated
     * ODH requests.
     *
     * @param username the username for ODH authentication
     * @param password the password for ODH authentication
     * @return an ODH API key that can be used for further authenticated ODH requests
     */
    private String fetchApiKey(String username, String password) {
        MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add(GRANT_TYPE_PARAM_NAME, GRANT_TYPE);
        formData.add(USERNAME_PARAM_NAME, username);
        formData.add(PASSWORD_PARAM_NAME, password);

        WebTarget apiKeyTarget = this.webTarget.path(AUTHENTICATION_PATH);

        Invocation.Builder request = apiKeyTarget.request(MediaType.APPLICATION_JSON);

        return request
                .post(Entity.form(formData), ApiKeyResponse.class)
                .getAccessToken();
    }

    /**
     * Fetch the resource from the given <code>path</code>, appending <code>queryParams</code>
     * to the request URL.
     * <p>
     * If the fetch returns a {@link Response.Status#UNAUTHORIZED} HTTP status code, an
     * authentication is attempted and the fetch is repeated one more time.
     *
     * @param path        this path is used to fetch the resource
     * @param queryParams are appended to the URL
     * @return {@link Response} that can be further handled
     */
    private Response fetchWithAutomaticAuthentication(String path, String method, Map<String, String> queryParams, Entity<?> body) {
        Invocation.Builder builder = this.prepareFetch(path, queryParams);

        // Fetch resource
        Response response = builder.method(method, body);

        if (response.getStatus() != Response.Status.UNAUTHORIZED.getStatusCode()) {
            return response;
        }

        // If response status code is 401 (UNAUTHORIZED), attempt to fetch new API key
        // and retry the request with that key
        this.apiKey = this.fetchApiKey(this.username, this.password);

        // Retry request
        builder = this.prepareFetch(path, queryParams);
        return builder.method(method, body);
    }

    private Invocation.Builder prepareFetch(String path, Map<String, String> queryParams) {
        WebTarget fetchTarget = this.buildFetchTarget(path, queryParams);

        Invocation.Builder request = fetchTarget.request(MediaType.APPLICATION_JSON);

        return request.header("Authorization", "Bearer " + this.apiKey);
    }

    private WebTarget buildFetchTarget(String path, Map<String, String> queryParamsMap) {
        WebTarget fetchTarget = this.webTarget.path(path);

        if (queryParamsMap != null) {
            for (Map.Entry<String, String> entry : queryParamsMap.entrySet()) {
                fetchTarget = fetchTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return fetchTarget;
    }
}
