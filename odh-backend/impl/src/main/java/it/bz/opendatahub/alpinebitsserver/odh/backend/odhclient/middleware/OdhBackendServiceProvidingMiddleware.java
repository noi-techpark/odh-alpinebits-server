/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.middleware;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.AuthenticatedOdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClientImpl;

/**
 * On invocation, this middleware puts an OdhBackendService instance
 * into the context.
 * <p>
 * The OdhBackendService version depends on the AlpineBits version in
 * the request.
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

        String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        if (AlpineBitsVersion.V_2017_10.equals(version)) {
            injectService_2017_10(ctx, odhClient);
        } else if (AlpineBitsVersion.V_2018_10.equals(version)) {
            injectService_2018_10(ctx, odhClient);
        } else {
            throw new UnsupportedOperationException("Version " + version + " is not supported by current implementation");
        }

        chain.next();
    }

    @SuppressWarnings("checkstyle:MethodName")
    private void injectService_2017_10(Context ctx, AuthenticatedOdhClient odhClient) {
        it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.OdhBackendService service =
                new it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.OdhBackendServiceImpl(odhClient);
        ctx.put(OdhBackendContextKey.ODH_BACKEND_SERVICE_2017_10, service);
    }

    @SuppressWarnings("checkstyle:MethodName")
    private void injectService_2018_10(Context ctx, AuthenticatedOdhClient odhClient) {
        it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService service =
                new it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendServiceImpl(odhClient);
        ctx.put(OdhBackendContextKey.ODH_BACKEND_SERVICE_2018_10, service);
    }

}