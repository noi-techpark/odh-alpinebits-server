// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.routing;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RoutingBuilder;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link RoutesFor201710}.
 */
public class RoutesFor201710Test {

    private static final String ALPINEBITS_VERSION = AlpineBitsVersion.V_2017_10;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRoutes_ShouldThrow_WhenBuilderIsNull() {
        RoutesFor201710.routes(null);
    }

    @Test
    public void testRoutes_ShouldReturnFinalBuild_OnSuccess() {
        RoutingBuilder.FinalBuilder builder = RoutesFor201710.routes(new DefaultRouter.Builder());
        Router router = builder.buildRouter();

        assertEquals(router.getVersions().size(), 1);
        assertTrue(router.getVersions().contains(ALPINEBITS_VERSION));

        assertTrue(router.getActionsForVersion(ALPINEBITS_VERSION).isPresent());
        Set<Action> actions = router.getActionsForVersion(ALPINEBITS_VERSION).get();

        assertEquals(actions.size(), 7);
        assertTrue(actions.contains(Action.GET_VERSION));
        assertTrue(actions.contains(Action.GET_CAPABILITIES));
        assertTrue(actions.contains(Action.INVENTORY_BASIC_PULL));
        assertTrue(actions.contains(Action.INVENTORY_HOTEL_INFO_PULL));
        assertTrue(actions.contains(Action.INVENTORY_BASIC_PUSH));
        assertTrue(actions.contains(Action.INVENTORY_HOTEL_INFO_PUSH));
        assertTrue(actions.contains(Action.FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS));

        assertTrue(router.getCapabilitiesForVersion(ALPINEBITS_VERSION).isPresent());
        Set<String> capabilities = router.getCapabilitiesForVersion(ALPINEBITS_VERSION).get();

        assertEquals(capabilities.size(), 9);
        assertTrue(capabilities.contains(AlpineBitsCapability.GET_VERSION));
        assertTrue(capabilities.contains(AlpineBitsCapability.GET_CAPABILITIES));
        assertTrue(capabilities.contains(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INVENTORY));
        assertTrue(capabilities.contains(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO));
        assertTrue(capabilities.contains(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY));
        assertTrue(capabilities.contains(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY_USE_ROOMS));
        assertTrue(capabilities.contains(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO));
        assertTrue(capabilities.contains(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF));
        assertTrue(capabilities.contains(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_ROOMS));
    }


}