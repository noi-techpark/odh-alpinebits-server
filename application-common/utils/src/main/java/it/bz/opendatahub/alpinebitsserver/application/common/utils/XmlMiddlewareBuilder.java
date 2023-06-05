// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlRequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;

import javax.xml.validation.Schema;

/**
 * Utility class to build XML middlewares.
 */
public final class XmlMiddlewareBuilder {

    private XmlMiddlewareBuilder() {
        // Empty
    }

    /**
     * Build a {@link Middleware} that converts a XML to an object of type <code>T</code>.
     *
     * @param key               The converted object is put in the middleware {@link Context} using this key.
     * @param alpineBitsVersion The AlpineBits version used for XML validation.
     * @param <T>               The type of the converted object.
     * @return A Middleware that converts a XML to an object of type <code>T</code>.
     */
    public static <T> Middleware buildXmlToObjectConvertingMiddleware(Key<T> key, String alpineBitsVersion) {
        validateParams(key, alpineBitsVersion);

        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(alpineBitsVersion);
        XmlToObjectConverter<T> converter = new JAXBXmlToObjectConverter.Builder<>(key.getType()).schema(schema).build();
        return new XmlRequestMappingMiddleware<>(converter, key);
    }

    /**
     * Build a {@link Middleware} that converts an object of type <code>T</code> to XML.
     *
     * @param key               The object to convert is read from the middleware {@link Context} using this key.
     * @param alpineBitsVersion The AlpineBits version used for XML validation.
     * @param <T>               The type of the object to convert.
     * @return A Middleware that converts an object of type <code>T</code> to a XML.
     */
    public static <T> Middleware buildObjectToXmlConvertingMiddleware(Key<T> key, String alpineBitsVersion) {
        validateParams(key, alpineBitsVersion);

        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(alpineBitsVersion);
        ObjectToXmlConverter converter = new JAXBObjectToXmlConverter.Builder()
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, key);
    }

    private static <T> void validateParams(Key<T> key, String version) {
        if (key == null) {
            throw new IllegalArgumentException("The context key must not be null");
        }
        if (version == null) {
            throw new IllegalArgumentException("The version must not be null");
        }
    }

}
