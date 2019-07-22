/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.middleware.config;

import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlRequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * Utility class to build XML middlewares.
 */
public final class XmlMiddlewareBuilder {

    private XmlMiddlewareBuilder() {
        // Empty
    }

    public static <T> Middleware buildXmlToObjectConvertingMiddleware(Key<T> key) throws JAXBException {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<T> converter = new JAXBXmlToObjectConverter.Builder<>(key.getType()).schema(schema).build();
        return new XmlRequestMappingMiddleware<>(converter, key);
    }

    public static <T> Middleware buildObjectToXmlConvertingMiddleware(Key<T> key) throws JAXBException {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        ObjectToXmlConverter<T> converter = new JAXBObjectToXmlConverter.Builder<>(key.getType())
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, key);
    }

}
