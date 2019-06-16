package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * This property source returns always and for each key null.
 */
public class EmptyPropertySource implements PropertySource {

    @Override
    public String getValue(String key) {
        return null;
    }

}
