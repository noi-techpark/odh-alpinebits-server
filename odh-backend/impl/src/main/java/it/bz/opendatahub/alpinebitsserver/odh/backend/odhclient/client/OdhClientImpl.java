/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.ApiKeyResponse;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
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
public class OdhClientImpl implements AuthenticatedOdhClient {

    // The Sonar warning "'PASSWORD' detected in this expression" can be ignored
    @SuppressWarnings({"squid:S2068"})
    private static final String PASSWORD_PARAM_NAME = "password";
    private static final String USERNAME_PARAM_NAME = "username";
    private static final String GRANT_TYPE_PARAM_NAME = "grant_type";

    private static final String GRANT_TYPE = "password";

    private static final String AUTHENTICATION_PATH = "token";

    private final WebTarget webTarget;

    private String apiKey = "UNDEFINED";
    private boolean isAuthenticated;

    public OdhClientImpl(String baseUrl, String username, String password) {
        Client client = this.buildClient();
        this.webTarget = client.target(baseUrl);

        try {
            this.apiKey = this.fetchApiKey(username, password);
            this.isAuthenticated = true;
        } catch (ProcessingException | WebApplicationException e) {
            this.isAuthenticated = false;
        }
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
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

    private Client buildClient() {
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(om);

        ClientConfig config = new ClientConfig(provider);

        return ClientBuilder.newClient(config);
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
