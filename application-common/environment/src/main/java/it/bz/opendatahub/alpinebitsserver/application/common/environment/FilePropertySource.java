/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This {@link PropertySource} implementation reads values from a file
 * specified in the constructor.
 * <p>
 * For the file to be found, it needs to be loadable from classpath. No
 * exception is thrown if the file was not found, instead, any invocation
 * of {@link #getValue(String)} returns null. This allows to have optional
 * property files.
 * <p>
 * The file content has to follow standard properties file convention
 * with key=property values defined on separate rows.
 */
public class FilePropertySource implements PropertySource {

    public static final String DEFAULT_PROPERTIES_FILENAME = "application.properties";

    private static final Logger LOG = LoggerFactory.getLogger(FilePropertySource.class);

    private Properties properties;

    /**
     * Create an instance of {@link FilePropertySource} that loads its
     * properties from the file specified by <code>filename</code>.
     *
     * @param filename load file properties from the file with this name
     */
    public FilePropertySource(String filename) {
        this.properties = this.loadPropertiesFromFile(filename);
    }

    /**
     * Return an instance of {@link FilePropertySource} that loads its
     * properties from the default file {@link FilePropertySource#DEFAULT_PROPERTIES_FILENAME}.
     *
     * @return an instance of {@link FilePropertySource}
     */
    public static FilePropertySource fromDefaultFile() {
        return new FilePropertySource(DEFAULT_PROPERTIES_FILENAME);
    }

    @Override
    public String getValue(String key) {
        if (key == null) {
            return null;
        }
        return this.properties.getProperty(key);
    }

    private Properties loadPropertiesFromFile(String filename) {
        Properties fileProperties = new Properties();
        if (filename == null) {
            return fileProperties;
        }
        try (InputStream is = FilePropertySource.class.getResourceAsStream("/" + filename)) {
            // Check if resource with filename was found
            if (is != null) {
                fileProperties.load(is);
            }
            return fileProperties;
        } catch (IOException e) {
            LOG.error("Could not read property file {}", filename, e);
        }
        return fileProperties;
    }
}
