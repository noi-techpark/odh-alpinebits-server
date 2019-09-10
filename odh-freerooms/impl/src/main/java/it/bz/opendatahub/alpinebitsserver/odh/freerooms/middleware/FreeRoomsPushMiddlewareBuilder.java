/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms.middleware;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.FreeRoomsMapperInstances;
import it.bz.opendatahub.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.FreeRoomsContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.FreeRoomsContextProvider;
import it.bz.opendatahub.alpinebits.validation.middleware.ValidationMiddleware;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.OTAHotelAvailNotifRQValidator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.middleware.config.XmlMiddlewareBuilder;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link FreeRoomsPushMiddleware}.
 */
public final class FreeRoomsPushMiddlewareBuilder {

    private static final Key<OTAHotelAvailNotifRQ> OTA_FREE_ROOMS_PUSH_REQUEST
            = Key.key("freerooms push request", OTAHotelAvailNotifRQ.class);
    private static final Key<OTAHotelAvailNotifRS> OTA_FREE_ROOMS_PUSH_RESPONSE
            = Key.key("freerooms push response", OTAHotelAvailNotifRS.class);


    private static final Key<GenericResponse> MAPPED_FREE_ROOMS_PUSH_RESPONSE_KEY =
            Key.key("mapped freerooms push response", GenericResponse.class);

    private FreeRoomsPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsPushMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_FREE_ROOMS_PUSH_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_FREE_ROOMS_PUSH_RESPONSE),
                buildValidationMiddleware(),
                buildFreeRoomsPushResponseMappingMiddleware(),
                new FreeRoomsPushMiddleware(
                        OTA_FREE_ROOMS_PUSH_REQUEST,
                        MAPPED_FREE_ROOMS_PUSH_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildValidationMiddleware() {
        Validator<OTAHotelAvailNotifRQ, FreeRoomsContext> validator = new OTAHotelAvailNotifRQValidator();
        ValidationContextProvider<FreeRoomsContext> validationContextProvider = new FreeRoomsContextProvider();
        return new ValidationMiddleware<>(OTA_FREE_ROOMS_PUSH_REQUEST, validator, validationContextProvider);
    }

    private static Middleware buildFreeRoomsPushResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                MAPPED_FREE_ROOMS_PUSH_RESPONSE_KEY,
                OTA_FREE_ROOMS_PUSH_RESPONSE,
                FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER::toOTAHotelAvailNotifRS
        );
    }
}
