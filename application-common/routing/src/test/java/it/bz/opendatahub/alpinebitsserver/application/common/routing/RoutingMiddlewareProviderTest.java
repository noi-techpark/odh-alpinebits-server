/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.routing;

import it.bz.opendatahub.alpinebits.middleware.Middleware;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Tests for {@link RoutingMiddlewareProvider}.
 */
public class RoutingMiddlewareProviderTest {

    @Test
    public void testBuildRoutingMiddleware_ShouldReturnMiddleware_OnSuccess() {
        Middleware middleware = RoutingMiddlewareProvider.buildRoutingMiddleware();
        assertNotNull(middleware);
    }
}