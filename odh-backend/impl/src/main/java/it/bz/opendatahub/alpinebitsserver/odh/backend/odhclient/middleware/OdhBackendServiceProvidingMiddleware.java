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
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.AuthenticatedOdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClientImpl;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendServiceImpl;

/**
 * On invocation, this middleware puts an {@link OdhBackendService} instance
 * into the context.
 */
public class OdhBackendServiceProvidingMiddleware implements Middleware {

    private final String odhClientBaseUrl;

    public OdhBackendServiceProvidingMiddleware(String odhClientBaseUrl) {
        this.odhClientBaseUrl = odhClientBaseUrl;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);
        String password = (String) ctx.getOrThrow(RequestContextKey.REQUEST_PASSWORD_SUPPLIER).get();

        AuthenticatedOdhClient odhClient = new OdhClientImpl(this.odhClientBaseUrl, username, password);
        OdhBackendService service = new OdhBackendServiceImpl(odhClient);
        ctx.put(OdhBackendContextKey.ODH_BACKEND_SERVICE, service);

        chain.next();
    }
}