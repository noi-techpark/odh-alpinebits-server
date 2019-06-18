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

    @Override
    public String getValue(String key) {
        if (key == null) {
            return null;
        }
        return System.getenv(key);
    }

}
