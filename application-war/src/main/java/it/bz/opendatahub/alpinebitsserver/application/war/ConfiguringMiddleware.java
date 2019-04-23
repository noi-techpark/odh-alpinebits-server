/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.war;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.StatisticsMiddleware;
import it.bz.opendatahub.alpinebitsserver.application.common.exception.StartupConditionFailedException;
import it.bz.opendatahub.alpinebitsserver.application.common.middleware.UsernameAndPasswordMatchAuthorizationMiddleware;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.impl.ODHInventoryPullServiceBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.impl.OdhInventoryPullService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.middleware.InventoryPullMiddlewareBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * This {@link Middleware} configures a set of middlewares, such that
 * the resulting server is able to respond to AlpineBits Inventory requests.
 * <p>
 * The resulting server supports the {@link AlpineBitsVersion#V_2017_10}
 * version only.
 * <p>
 * A basic authentication check is enforced, although any username and
 * password combination is valid. In other words: a request MUST contain
 * basic authentication information, but that information is not checked
 * any further.
 */
public class ConfiguringMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(ConfiguringMiddleware.class);

    private Middleware middleware;
    private String startupError;

    @SuppressWarnings("checkstyle:illegalcatch")
    public ConfiguringMiddleware() throws JAXBException {
        OdhInventoryPullService inventoryPullService = null;
        try {
            inventoryPullService = ODHInventoryPullServiceBuilder.buildInventoryPullService();
        } catch (StartupConditionFailedException e) {
            LOG.error("Could not start application", e);
            this.startupError = e.getMessage();
        }

        if (this.isStartupOk()) {
            this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                    new StatisticsMiddleware(),
                    new AlpineBitsClientProtocolMiddleware(),
                    new BasicAuthenticationMiddleware(),
                    new UsernameAndPasswordMatchAuthorizationMiddleware(),
                    new GzipUnsupportedMiddleware(),
                    new MultipartFormDataParserMiddleware(),
                    this.buildRoutingMiddleware(inventoryPullService)
            ));
        }
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        if (this.isStartupOk()) {
            this.middleware.handleContext(ctx, chain);
        } else {
            throw new AlpineBitsException("Server error - " + this.startupError, 500);
        }
    }

    private boolean isStartupOk() {
        return this.startupError == null;
    }

    private Middleware buildRoutingMiddleware(OdhInventoryPullService inventoryPullService) throws JAXBException {
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
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware(inventoryPullService))
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware(inventoryPullService))
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

}