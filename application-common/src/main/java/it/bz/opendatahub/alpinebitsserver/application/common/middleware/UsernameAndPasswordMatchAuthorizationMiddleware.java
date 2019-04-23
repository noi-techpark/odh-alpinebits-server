package it.bz.opendatahub.alpinebitsserver.application.common.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.servlet.ServletContextKey;
import it.bz.opendatahub.alpinebits.servlet.impl.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This middleware checks the Basic Authentication authorization.
 *
 * If the username and password match, the request proceeds.
 * Otherwise, an {@link AlpineBitsException} is thrown with
 * HTTP status code 401 (Unauthorized).
 */
public class UsernameAndPasswordMatchAuthorizationMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(UsernameAndPasswordMatchAuthorizationMiddleware.class);

    private static final String UNAUTHORIZED = "Unauthorized";

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);
        String password = (String)ctx.getOrThrow(RequestContextKey.REQUEST_PASSWORD_SUPPLIER).get();

        if (!username.equals(password)) {
            String maskedPassword = this.mask(password, 1);
            LOG.error("Username [{}] and password [{}] don't match", username, maskedPassword);
            String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
            HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);

            try {
                ResponseWriter.writeError(response, HttpServletResponse.SC_UNAUTHORIZED, requestId, UNAUTHORIZED);
            } catch(IOException e) {
                throw new AlpineBitsException(UNAUTHORIZED, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            chain.next();
        }
    }

    private String mask(String s, int plainCharLength) {
        if (s == null || s.length() < plainCharLength) {
            return s;
        }
        return s.substring(0, plainCharLength) + "*****";
    }
}
