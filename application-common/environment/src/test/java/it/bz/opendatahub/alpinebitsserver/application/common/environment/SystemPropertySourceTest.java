package it.bz.opendatahub.alpinebitsserver.application.common.environment;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;

/**
 * Tests for {@link SystemPropertySource}.
 */
public class SystemPropertySourceTest {

    @Test
    public void testGetValue_whenKeyIsNull_thenResultIsNull() {
        PropertySource source = new SystemPropertySource();
        assertNull(source.getValue(null));
    }

    @Test
    public void testGetValue_whenKeyIsDefinedAndPropertyNotFound_thenResultIsNull() {
        PropertySource source = new SystemPropertySource();
        assertNull(source.getValue("some key"));
    }

}