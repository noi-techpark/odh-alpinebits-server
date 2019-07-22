/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.routing;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.middleware.InventoryPullMiddlewareBuilder;

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
     * AlpineBits Housekeeping and Inventory actions.
     *
     * @return a routing middleware supporting the AlpineBits
     * Housekeeping and Inventory actions
     * @throws JAXBException if there was an error while building
     * the necessary XML parts
     */
    public static Middleware buildRoutingMiddleware() throws JAXBException {
        Router router = new DefaultRouter.Builder()
                .version(AlpineBitsVersion.V_2017_10)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_BASIC_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }
}
