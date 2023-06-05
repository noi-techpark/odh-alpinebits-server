// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.common.utils.response.ResponseOutcomeBuilder;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelAvailNotifContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelAvailNotifContextProvider;
import it.bz.opendatahub.alpinebits.validation.middleware.ValidationMiddleware;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.OTAHotelAvailNotifRQValidator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.ActionExceptionHandler;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeExtractor;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeMissingChecker;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.XmlMiddlewareBuilder;

import java.util.Arrays;

/**
 * Utility class to build a {@link HotelAvailNotifPushMiddleware}.
 */
public final class HotelAvailNotifPushMiddlewareBuilder {

    private static final Key<OTAHotelAvailNotifRQ> OTA_FREE_ROOMS_PUSH_REQUEST
            = Key.key("freerooms push request", OTAHotelAvailNotifRQ.class);
    private static final Key<OTAHotelAvailNotifRS> OTA_FREE_ROOMS_PUSH_RESPONSE
            = Key.key("freerooms push response", OTAHotelAvailNotifRS.class);

    private HotelAvailNotifPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsPushMiddleware(String alpineBitsVersion) {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                new ActionExceptionHandler<>(alpineBitsVersion, OTA_FREE_ROOMS_PUSH_RESPONSE, ResponseOutcomeBuilder::forOTAHotelAvailNotifRS),
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_FREE_ROOMS_PUSH_REQUEST, alpineBitsVersion),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_FREE_ROOMS_PUSH_RESPONSE, alpineBitsVersion),
                new HotelCodeMissingChecker<>(
                        OTA_FREE_ROOMS_PUSH_REQUEST,
                        OTA_FREE_ROOMS_PUSH_RESPONSE,
                        HotelCodeExtractor::hasHotelCode,
                        ResponseOutcomeBuilder::forOTAHotelAvailNotifRS
                ),
                buildValidationMiddleware(),
                new HotelAvailNotifPushMiddleware(
                        OTA_FREE_ROOMS_PUSH_REQUEST,
                        OTA_FREE_ROOMS_PUSH_RESPONSE
                )
        ));
    }

    private static Middleware buildValidationMiddleware() {
        Validator<OTAHotelAvailNotifRQ, HotelAvailNotifContext> validator = new OTAHotelAvailNotifRQValidator();
        ValidationContextProvider<HotelAvailNotifContext> validationContextProvider = new HotelAvailNotifContextProvider();
        return new ValidationMiddleware<>(OTA_FREE_ROOMS_PUSH_REQUEST, validator, validationContextProvider);
    }

}
