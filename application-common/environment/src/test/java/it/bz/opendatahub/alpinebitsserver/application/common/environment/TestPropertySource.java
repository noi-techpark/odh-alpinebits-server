package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * This {@link PropertySource} returns on each invocation of
 * {@link #getValue(String)} the value provided in the constructor.
 *
 * This class is for testing purposes only.
 */
public class TestPropertySource implements PropertySource {

    private final String value;

    public TestPropertySource(String value) {
        this.value = value;
    }

    @Override
    public String getValue(String key) {
        return this.value;
    }
}
