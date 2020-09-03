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
 * Tests for {@link PropertyProvider}.
 */
public class PropertyProviderTest {

    private static final String DEFAULT_KEY = "DEFAULT_KEY";
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";

    @Test
    public void testGetValue_givenNoProviders_thenReturnNull() {
        PropertyProvider provider = new PropertyProvider.Builder().build();
        String value = provider.getValue(DEFAULT_KEY);
        assertNull(value);
    }

    @Test
    public void testGetValue_givenSingleProvider_whenKeyIsNull_thenReturnNull() {
        PropertyProvider provider = this.buildFilePropertyProviderOnly();
        String value = provider.getValue(null);
        assertNull(value);
    }

    @Test
    public void testGetValue_givenSingleProvider_whenKeyIsUndefined_thenReturnNull() {
        PropertyProvider provider = this.buildFilePropertyProviderOnly();
        String value = provider.getValue("some undefined key");
        assertNull(value);
    }

    @Test
    public void testGetValue_givenSingleProvider_whenKeyIsFound_thenReturnValue() {
        PropertyProvider provider = this.buildFilePropertyProviderOnly();

        String value = provider.getValue(DEFAULT_KEY);
        assertEquals(value, DEFAULT_VALUE);
    }

    @Test
    public void testGetValue_givenAllProviders_whenKeyIsNull_thenReturnNull() {
        PropertyProvider provider = this.buildFullPropertyProvider();
        String value = provider.getValue(null);
        assertNull(value);
    }

    @Test
    public void testGetValue_givenAllProviders_whenKeyIsUndefined_thenReturnNull() {
        PropertyProvider provider = this.buildFullPropertyProvider();
        String value = provider.getValue("some undefined key");
        assertNull(value);
    }

    @Test
    public void testGetValue_givenAllProviders_whenKeyIsFound_thenReturnValue() {
        PropertyProvider provider = this.buildFullPropertyProvider();
        String value = provider.getValue(DEFAULT_KEY);
        assertEquals(value, DEFAULT_VALUE);
    }

    @Test
    public void testGetValue_givenAllProviders_whenKeyIsFoundInFileAndSystemEnv_thenReturnValueFromSourceWithHighestPriority1() {
        // System environment properties have higher precedence than
        // file properties. Mock thy system environment source implementation
        String expectedPropertyValue = "expectedPropertyValue";
        PropertySource systemEnvironmentSource = new TestPropertySource(expectedPropertyValue);

        PropertyProvider provider = new PropertyProvider
                .Builder()
                .withFilePropertySource(FilePropertySource.fromDefaultFile())
                .withSystemPropertySource(systemEnvironmentSource)
                .withProcessPropertySource(new ProcessPropertySource())
                .build();
        String value = provider.getValue(DEFAULT_KEY);
        assertEquals(value, expectedPropertyValue);
    }

    @Test
    public void testGetValue_givenAllProviders_whenKeyIsFound_thenReturnValueFromSourceWithHighestPriority2() {
        // Process properties have highest precedence
        String expectedPropertyValue = "expectedPropertyValue";
        System.setProperty(DEFAULT_KEY, expectedPropertyValue);

        PropertyProvider provider = this.buildFullPropertyProvider();
        String value = provider.getValue(DEFAULT_KEY);
        System.clearProperty(DEFAULT_KEY);

        assertEquals(value, expectedPropertyValue);
    }

    private PropertyProvider buildFilePropertyProviderOnly() {
        return new PropertyProvider
                .Builder()
                .withFilePropertySource(FilePropertySource.fromDefaultFile())
                .build();
    }

    private PropertyProvider buildFullPropertyProvider() {
        return new PropertyProvider
                .Builder()
                .withFilePropertySource(FilePropertySource.fromDefaultFile())
                .withSystemPropertySource(new SystemPropertySource())
                .withProcessPropertySource(new ProcessPropertySource())
                .build();
    }
}