/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * This {@link PropertySource} implementation reads values from the
 * system environment.
 */
public class SystemPropertySource implements PropertySource {

    /**
     * Try to get the value for the associated key from the system environment.
     * <p>
     * Note, that if the key is not found, than a second attempt to find a value
     * will be done with an altered key: the key is transformed to its uppercase form
     * and all dots inside the key are replaced with underscores. This should resemble
     * common practice an mimic the behaviour of Spring.
     * <p>
     * For example the key "some.key" will be transformed to "SOME_FORM" and a resolution
     * with that key is attempted.
     *
     * @param key Used to find a value in the ENV.
     * @return A value for the given key or its uppercase and dot-cleaned version. If
     * no value is found, <code>null</code> is returned. <code>null</code> is also
     * returned when the key is <code>null</code>.
     */
    @Override
    public String getValue(String key) {
        if (key == null) {
            return null;
        }

        String value = System.getenv(key);
        if (value != null) {
            return value;
        }

        String upperCaseAndDotReplaceKey = key.toUpperCase().replace(".", "_");
        return System.getenv(upperCaseAndDotReplaceKey);
    }

}
