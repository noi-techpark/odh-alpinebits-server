// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.environment;

/**
 * The PropertyProvider can be used to read configuration values
 * from different sources.
 */
public final class PropertyProvider {

    private final PropertySource filePropertySource;
    private final PropertySource systemPropertySource;
    private final PropertySource processPropertySource;

    private PropertyProvider(
            PropertySource filePropertySource,
            PropertySource systemPropertySource,
            PropertySource processPropertySource
    ) {
        this.filePropertySource = filePropertySource;
        this.systemPropertySource = systemPropertySource;
        this.processPropertySource = processPropertySource;
    }

    /**
     * Return the value of the property for the given <code>key</code>.
     * <p>
     * Properties can be defined in different property sources:
     * <ul>
     * <li>a file</li>
     * <li>specified as system environment variables</li>
     * <li>
     * specified in the process environments during JVM startup
     * (e.g. java ... -Dkey=value)
     * </li>
     * </ul>
     * <p>
     * The property sources have different priorities, where values derived
     * from lower priority sources get overwritten by values from higher
     * priority sources. The priority is defined as follows (from lowest to
     * highest): file -> system environment -> process environment
     *
     * @return <code>null</code> if the <code>key</code> is null or it couldn't
     * be found in any property source. Otherwise, the value of the property is
     * returned. If the property is defined in several sources, the value of the
     * source with highest priority (containing the <code>key</code>) is returned.
     */
    public String getValue(final String key) {
        if (key == null) {
            return null;
        }

        // Process properties have highest priority
        String processConfigValue = this.processPropertySource.getValue(key);
        if (processConfigValue != null) {
            return processConfigValue;
        }

        // If the property was not found at this point,
        // try to find it in the system environment
        String systemConfigValue = this.systemPropertySource.getValue(key);
        if (systemConfigValue != null) {
            return systemConfigValue;
        }

        // If the property was not found at this point,
        // try to find it in the file. Note, that the
        // filePropertySource returns null if the property
        // wasn't found there
        return this.filePropertySource.getValue(key);
    }

    /**
     * Builder for {@link PropertyProvider}.
     */
    public static class Builder {

        private PropertySource filePropertySource;
        private PropertySource systemPropertySource;
        private PropertySource processPropertySource;

        public PropertyProvider build() {
            if (this.filePropertySource == null) {
                this.filePropertySource = new EmptyPropertySource();
            }
            if (this.systemPropertySource == null) {
                this.systemPropertySource = new EmptyPropertySource();
            }
            if (this.processPropertySource == null) {
                this.processPropertySource = new EmptyPropertySource();
            }
            return new PropertyProvider(this.filePropertySource, this.systemPropertySource, this.processPropertySource);
        }

        /**
         * Configure the file property source. The file property source has
         * the lowest priority on resolution (see {@link #getValue(String)}.
         *
         * @param source the file property source
         * @return the current Builder's instance
         */
        public Builder withFilePropertySource(PropertySource source) {
            this.filePropertySource = source;
            return this;
        }

        /**
         * Configure the system property source. The system property source has
         * the second highest priority on resolution (see {@link #getValue(String)}.
         *
         * @param source the system property source
         * @return the current Builder's instance
         */
        public Builder withSystemPropertySource(PropertySource source) {
            this.systemPropertySource = source;
            return this;
        }

        /**
         * Configure the process property source. The process property source has
         * the highest priority on resolution (see {@link #getValue(String)}.
         *
         * @param source the process property source
         * @return the current Builder's instance
         */
        public Builder withProcessPropertySource(PropertySource source) {
            this.processPropertySource = source;
            return this;
        }

    }

}
