package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * This {@link PropertySource} implementation reads values from the
 * system environment.
 */
public class SystemPropertySource implements PropertySource {

    @Override
    public String getValue(String key) {
        if (key == null) {
            return null;
        }
        return System.getenv(key);
    }

}
