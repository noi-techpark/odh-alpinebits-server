/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * This interface defines methods for property sources.
 */
public interface PropertySource {

    /**
     * Return the configuration value for the given <code>key</code>.
     * <p>
     * If <code>key</code> is empty or the value was not found, null
     * is returned. Otherwise, the value associated with the <code>key</code>
     * is returned.
     *
     * @param key used to find the value
     * @return null if <code>key</code> is null or it wasn't found. The
     * value associated with <code>key</code> otherwise
     */
    String getValue(String key);

}
