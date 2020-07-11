/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.FreeRoomsContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.FreeRoomsContextProvider;
import it.bz.opendatahub.alpinebits.validation.middleware.ValidationMiddleware;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.OTAHotelAvailNotifRQValidator;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.XmlMiddlewareBuilder;

import javax.xml.validation.Schema;
import java.util.Arrays;

/**
 * Utility class to build a {@link FreeRoomsPushMiddleware}.
 */
public final class FreeRoomsPushMiddlewareBuilder {

    private static final Key<OTAHotelAvailNotifRQ> OTA_FREE_ROOMS_PUSH_REQUEST
            = Key.key("freerooms push request", OTAHotelAvailNotifRQ.class);
    private static final Key<OTAHotelAvailNotifRS> OTA_FREE_ROOMS_PUSH_RESPONSE
            = Key.key("freerooms push response", OTAHotelAvailNotifRS.class);

    private FreeRoomsPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsPushMiddleware() {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_FREE_ROOMS_PUSH_REQUEST, AlpineBitsVersion.V_2017_10),
                buildObjectToXmlConvertingMiddleware(),
                buildValidationMiddleware(),
                new FreeRoomsPushMiddleware(
                        OTA_FREE_ROOMS_PUSH_REQUEST,
                        OTA_FREE_ROOMS_PUSH_RESPONSE
                )
        ));
    }

    private static Middleware buildValidationMiddleware() {
        Validator<OTAHotelAvailNotifRQ, FreeRoomsContext> validator = new OTAHotelAvailNotifRQValidator();
        ValidationContextProvider<FreeRoomsContext> validationContextProvider = new FreeRoomsContextProvider();
        return new ValidationMiddleware<>(OTA_FREE_ROOMS_PUSH_REQUEST, validator, validationContextProvider);
    }

    private static Middleware buildObjectToXmlConvertingMiddleware() {
        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(AlpineBitsVersion.V_2017_10);
        ObjectToXmlConverter converter = new JAXBObjectToXmlConverter.Builder()
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, OTA_FREE_ROOMS_PUSH_RESPONSE);
    }

}
