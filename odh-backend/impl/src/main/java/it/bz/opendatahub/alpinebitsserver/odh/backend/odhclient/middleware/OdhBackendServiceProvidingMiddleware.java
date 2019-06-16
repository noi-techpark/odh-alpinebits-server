package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.HashUtil;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClientImpl;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClientPool;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendServiceImpl;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

/**
 * On invocation, this middleware puts an {@link OdhBackendService} instance
 * into the context.
 */
public class OdhBackendServiceProvidingMiddleware implements Middleware {

    private final OdhClientPool pool;
    private final String odhClientBaseUrl;

    public OdhBackendServiceProvidingMiddleware(String odhClientBaseUrl, int clientPoolSize, Duration clientMaxAge) {
        this.odhClientBaseUrl = odhClientBaseUrl;
        this.pool = new OdhClientPool(clientPoolSize, clientMaxAge);
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);
        String password = (String) ctx.getOrThrow(RequestContextKey.REQUEST_PASSWORD_SUPPLIER).get();

        try {
            String odhClientHash = HashUtil.hash256(username, password);
            OdhClient odhClient = getOdhClient(username, password, odhClientHash);

            OdhBackendService service = new OdhBackendServiceImpl(odhClient);
            ctx.put(OdhBackendContextKey.ODH_BACKEND_SERVICE, service);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new AlpineBitsException(
                    "Error while taking ODH client from pool",
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    e
            );
        }

        chain.next();

    }

    private OdhClient getOdhClient(String username, String password, String odhClientHash) {
        return this.pool.getClient(
                        odhClientHash,
                        // Need to turn checkstyle off because it can't
                        // handle lambda functions the right way
                        // CHECKSTYLE:OFF
                        unknownEntry -> new OdhClientImpl(this.odhClientBaseUrl, username, password)
                        // CHECKSTYLE:ON
                );
    }
}