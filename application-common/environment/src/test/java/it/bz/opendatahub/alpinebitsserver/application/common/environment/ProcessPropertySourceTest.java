// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.environment;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Tests for {@link ProcessPropertySource}.
 */
public class ProcessPropertySourceTest {

    private static final String DEFAULT_KEY = "DEFAULT_KEY";
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";

    @Test
    public void testGetValue_whenKeyIsNull_thenResultIsNull() {
        PropertySource source = new ProcessPropertySource();
        assertNull(source.getValue(null));
    }

    @Test
    public void testGetValue_whenKeyIsDefinedAndPropertyNotFound_thenResultIsNull() {
        PropertySource source = new ProcessPropertySource();
        assertNull(source.getValue("some key"));
    }

    @Test
    public void testGetValue_whenKeyIsDefinedAndPropertyIsFound_thenResultIsValue() {
        System.setProperty(DEFAULT_KEY, DEFAULT_VALUE);
        PropertySource source = new ProcessPropertySource();
        assertEquals(source.getValue(DEFAULT_KEY), DEFAULT_VALUE);
        System.clearProperty(DEFAULT_KEY);
    }

}