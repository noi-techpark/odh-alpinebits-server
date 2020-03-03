/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2018_10;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService;

/**
 * This middleware invokes one of the two constructor provided
 * {@link Middleware} implementations, depending on the credentials
 * provided in the AlpineBits request.
 * <p>
 * ODH decides if the the credentials are accepted.
 */
public class AuthenticationBasedRoutingMiddleware implements Middleware {

    private final Middleware middlewareToRunWhenAuthenticated;
    private final Middleware middlewareToRunWhenNotAuthenticated;

    public AuthenticationBasedRoutingMiddleware(
            Middleware middlewareToRunWhenAuthenticated,
            Middleware middlewareToRunWhenNotAuthenticated
    ) {
        this.middlewareToRunWhenAuthenticated = middlewareToRunWhenAuthenticated;
        this.middlewareToRunWhenNotAuthenticated = middlewareToRunWhenNotAuthenticated;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain middlewareChain) {
        OdhBackendService service = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE_2018_10);
        if (service.isAuthenticated()) {
            this.middlewareToRunWhenAuthenticated.handleContext(ctx, middlewareChain);
        } else {
            this.middlewareToRunWhenNotAuthenticated.handleContext(ctx, middlewareChain);
        }
    }
}
