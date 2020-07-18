/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.routing;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.handshaking.DefaultContextSerializer;
import it.bz.opendatahub.alpinebits.handshaking.middleware.HandshakingMiddleware;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.RoutingBuilder;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import it.bz.opendatahub.alpinebitsserver.odh.freerooms.HotelInvCountNotifPushMiddlewareBuilder;

/**
 * Route definitions for AlpineBits 2020-10.
 */
public final class RoutesFor202010 {

    private RoutesFor202010() {
        // Empty
    }

    /**
     * Add 2020-10 routes to the given builder.
     *
     * @param builder The routes will be added to this builder.
     * @return A {@link RoutingBuilder.FinalBuilder} that can be used for further route building.
     */
    public static RoutingBuilder.FinalBuilder routes(DefaultRouter.Builder builder) {
        return builder.version(AlpineBitsVersion.V_2020_10)
                .supportsAction(Action.HANDSHAKING)
                .withCapabilities()
                .using(new HandshakingMiddleware(new DefaultContextSerializer(AlpineBitsVersion.V_2020_10)))
                .and()
                .supportsAction(Action.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_FREE_ROOMS)
                .withCapabilities(
                        AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF,
                        AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_ROOMS,
                        AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_CATEGORIES
                )
                .using(HotelInvCountNotifPushMiddlewareBuilder.buildFreeRoomsPushMiddleware(AlpineBitsVersion.V_2020_10))
                .versionComplete();
    }
}
