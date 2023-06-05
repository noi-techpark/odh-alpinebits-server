// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Tests for {@link XmlMiddlewareBuilder}.
 */
public class XmlMiddlewareBuilderTest {

    private static final Key<String> DEFAULT_KEY = Key.key("ID", String.class);
    private static final String DEFAULT_ALPINEBITS_VERSION = AlpineBitsVersion.V_2020_10;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildXmlToObjectConvertingMiddleware_ShouldThrow_WhenKeyIsMissing() {
        XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(null, DEFAULT_ALPINEBITS_VERSION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildXmlToObjectConvertingMiddleware_ShouldThrow_WhenAlpineBitsVersionIsMissing() {
        XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(null, DEFAULT_ALPINEBITS_VERSION);
    }

    @Test
    public void testBuildXmlToObjectConvertingMiddleware_ShouldReturnMiddleware_OnSuccess() {
        Middleware middleware = XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(DEFAULT_KEY, DEFAULT_ALPINEBITS_VERSION);
        assertNotNull(middleware);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildObjectToXmlConvertingMiddleware_ShouldThrow_WhenKeyIsMissing() {
        XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(null, DEFAULT_ALPINEBITS_VERSION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildObjectToXmlConvertingMiddleware_ShouldThrow_WhenAlpineBitsVersionIsMissing() {
        XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(DEFAULT_KEY, null);
    }

    @Test
    public void testBuildObjectToXmlConvertingMiddleware_ShouldReturnMiddleware_OnSuccess() {
        Middleware middleware = XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(DEFAULT_KEY, DEFAULT_ALPINEBITS_VERSION);
        assertNotNull(middleware);
    }

}