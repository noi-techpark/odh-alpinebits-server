/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.AuthenticatedOdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClientImpl;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.auth.OpenIdAuthProvider;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * On invocation, this middleware puts an OdhBackendService instance
 * into the context.
 * <p>
 * The OdhBackendService version depends on the AlpineBits version in
 * the request.
 */
public class OdhBackendServiceProvidingMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(OdhBackendServiceProvidingMiddleware.class);

    private final String odhClientBaseUrl;
    private final OpenIdAuthProvider.Builder openIdAuthBuilder;

    public OdhBackendServiceProvidingMiddleware(String odhClientBaseUrl, String authUrl, String clientId, String clientSecret) {
        this.odhClientBaseUrl = odhClientBaseUrl;
        this.openIdAuthBuilder = new OpenIdAuthProvider.Builder(authUrl, clientId, clientSecret);


        logOdhConfig(odhClientBaseUrl, authUrl, clientId, clientSecret);
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);
        String password = (String) ctx.getOrThrow(RequestContextKey.REQUEST_PASSWORD_SUPPLIER).get();

        OpenIdAuthProvider authProvider = buildAuthProvider(username, password);
        AuthenticatedOdhClient odhClient = new OdhClientImpl(this.odhClientBaseUrl, authProvider);

        OdhBackendService service = new OdhBackendServiceImpl(odhClient);
        ctx.put(OdhBackendContextKey.ODH_BACKEND_SERVICE, service);

        chain.next();
    }

    private void logOdhConfig(String odhClientBaseUrl, String authUrl, String clientId, String clientSecret) {
        String hiddenClientSecret = getHiddenClientSecret(clientSecret);

        LOG.info("ODH URL: {}, ODH auth URL: {}, ODH auth client ID: {}, ODH auth client secret: {}",
                odhClientBaseUrl,
                authUrl,
                clientId,
                hiddenClientSecret
        );
    }

    private String getHiddenClientSecret(String clientSecret) {
        int hiddenCharsCount = 32;

        // create a string made up of hiddenCharsCount copies of "*"
        String hiddenClientSecret = String.join("", Collections.nCopies(hiddenCharsCount, "*"));

        if (clientSecret != null && clientSecret.length() >= hiddenCharsCount) {
            hiddenClientSecret += clientSecret.substring(hiddenCharsCount);
        }
        return hiddenClientSecret;
    }

    private OpenIdAuthProvider buildAuthProvider(String username, String password) {
        return this.openIdAuthBuilder
                .username(username)
                .password(password)
                .build();
    }

}