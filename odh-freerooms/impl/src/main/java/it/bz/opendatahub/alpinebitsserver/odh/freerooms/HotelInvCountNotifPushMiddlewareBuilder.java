/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelInvCountNotifContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelInvCountNotifContextProvider;
import it.bz.opendatahub.alpinebits.validation.middleware.ValidationMiddleware;
import it.bz.opendatahub.alpinebits.validation.schema.v_2020_10.freerooms.OTAHotelInvCountNotifRQValidator;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.XmlMiddlewareBuilder;

import javax.xml.validation.Schema;
import java.util.Arrays;

/**
 * Utility class to build a {@link HotelInvCountNotifPushMiddleware}.
 */
public final class HotelInvCountNotifPushMiddlewareBuilder {

    private static final Key<OTAHotelInvCountNotifRQ> OTA_FREE_ROOMS_PUSH_REQUEST
            = Key.key("OTAHotelInvCountNotifRQ freerooms push request", OTAHotelInvCountNotifRQ.class);
    private static final Key<OTAHotelInvCountNotifRS> OTA_FREE_ROOMS_PUSH_RESPONSE
            = Key.key("OTAHotelInvCountNotifRS freerooms push response", OTAHotelInvCountNotifRS.class);

    private HotelInvCountNotifPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsPushMiddleware(String alpineBitsVersion) {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_FREE_ROOMS_PUSH_REQUEST, alpineBitsVersion),
                buildObjectToXmlConvertingMiddleware(alpineBitsVersion),
                buildValidationMiddleware(),
                new HotelInvCountNotifPushMiddleware(
                        OTA_FREE_ROOMS_PUSH_REQUEST,
                        OTA_FREE_ROOMS_PUSH_RESPONSE
                )
        ));
    }

    private static Middleware buildValidationMiddleware() {
        Validator<OTAHotelInvCountNotifRQ, HotelInvCountNotifContext> validator = new OTAHotelInvCountNotifRQValidator();
        ValidationContextProvider<HotelInvCountNotifContext> validationContextProvider = new HotelInvCountNotifContextProvider();
        return new ValidationMiddleware<>(OTA_FREE_ROOMS_PUSH_REQUEST, validator, validationContextProvider);
    }

    private static Middleware buildObjectToXmlConvertingMiddleware(String alpineBitsVersion) {
        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(alpineBitsVersion);
        ObjectToXmlConverter converter = new JAXBObjectToXmlConverter.Builder()
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, OTA_FREE_ROOMS_PUSH_RESPONSE);
    }

}
