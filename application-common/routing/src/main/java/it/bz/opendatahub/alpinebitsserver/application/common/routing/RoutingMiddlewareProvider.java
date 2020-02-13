/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.routing;

import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.RoutingBuilder;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;

import javax.xml.bind.JAXBException;

/**
 * This class provides methods to build routing middlewares.
 */
public final class RoutingMiddlewareProvider {

    private RoutingMiddlewareProvider() {
        // Empty
    }

    /**
     * Build and return the a routing middleware that supports the
     * AlpineBits Housekeeping, FreeRooms and Inventory actions.
     *
     * @return a routing middleware supporting the AlpineBits
     * Housekeeping and Inventory actions
     * @throws JAXBException if there was an error while building
     *                       the necessary XML parts
     */
    public static Middleware buildRoutingMiddleware() throws JAXBException {
        DefaultRouter.Builder builder = new DefaultRouter.Builder();
        RoutingBuilder.FinalBuilder fb = RoutesFor201810.routes(builder);
        fb = RoutesFor201710.routes(fb.and());
        return new RoutingMiddleware(fb.buildRouter());
    }
}
