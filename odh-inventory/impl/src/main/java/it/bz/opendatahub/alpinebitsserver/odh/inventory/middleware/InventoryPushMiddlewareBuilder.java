/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.middleware;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContextProvider;
import it.bz.opendatahub.alpinebits.validation.middleware.ValidationMiddleware;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.OTAHotelDescriptiveContentNotifRQValidator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.middleware.config.XmlMiddlewareBuilder;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link InventoryPushMiddleware}.
 */
public final class InventoryPushMiddlewareBuilder {

    private static final Key<OTAHotelDescriptiveContentNotifRQ> OTA_INVENTORY_PUSH_REQUEST
            = Key.key("inventory push request", OTAHotelDescriptiveContentNotifRQ.class);

    private static final Key<OTAHotelDescriptiveContentNotifRS> OTA_INVENTORY_PUSH_RESPONSE
            = Key.key("inventory push request", OTAHotelDescriptiveContentNotifRS.class);

    private static final Key<GenericResponse> MAPPED_INVENTORY_PUSH_RESPONSE_KEY =
            Key.key("mapped inventory push response", GenericResponse.class);

    private InventoryPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildInventoryPushMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_INVENTORY_PUSH_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_INVENTORY_PUSH_RESPONSE),
                buildValidationMiddleware(),
                buildInventoryPushResponseMappingMiddleware(),
                new InventoryPushMiddleware(
                        OTA_INVENTORY_PUSH_REQUEST,
                        MAPPED_INVENTORY_PUSH_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildValidationMiddleware() {
        Validator<OTAHotelDescriptiveContentNotifRQ, InventoryContext> validator = new OTAHotelDescriptiveContentNotifRQValidator();
        ValidationContextProvider<InventoryContext> validationContextProvider = new InventoryContextProvider();
        return new ValidationMiddleware<>(OTA_INVENTORY_PUSH_REQUEST, validator, validationContextProvider);
    }

    private static Middleware buildInventoryPushResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                MAPPED_INVENTORY_PUSH_RESPONSE_KEY,
                OTA_INVENTORY_PUSH_RESPONSE,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER::toOTAHotelDescriptiveContentNotifRS
        );
    }

}
