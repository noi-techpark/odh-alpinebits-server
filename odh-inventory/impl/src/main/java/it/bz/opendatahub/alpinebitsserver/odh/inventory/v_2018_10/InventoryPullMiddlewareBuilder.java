/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2018_10;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.application.common.middleware.config.XmlMiddlewareBuilder;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link InventoryPullMiddleware}.
 */
public final class InventoryPullMiddlewareBuilder {

    private static final Key<OTAHotelDescriptiveInfoRQ> OTA_INVENTORY_PULL_REQUEST
            = Key.key("inventory pull request", OTAHotelDescriptiveInfoRQ.class);
    private static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);

    private static final Key<HotelDescriptiveInfoRequest> HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY =
            Key.key("mapped inventory pull request", HotelDescriptiveInfoRequest.class);
    private static final Key<HotelDescriptiveInfoResponse> HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY =
            Key.key("mapped inventory pull response", HotelDescriptiveInfoResponse.class);

    private InventoryPullMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildInventoryPullMiddleware() throws JAXBException {
        // Return a middleware, that invokes a different middleware, based
        // on the validity of the credentials provided with the AlpineBits request.
        return new AuthenticationBasedRoutingMiddleware(
                buildInventoryPullMiddlewareWithAuthentication(),
                buildInventoryPullMiddlewareWithNoAuthentication()
        );
    }

    private static Middleware buildInventoryPullMiddlewareWithAuthentication() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_INVENTORY_PULL_REQUEST, AlpineBitsVersion.V_2018_10),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_INVENTORY_PULL_RESPONSE, AlpineBitsVersion.V_2018_10),
                new AuthenticatedInventoryPullMiddleware(
                        OTA_INVENTORY_PULL_REQUEST,
                        OTA_INVENTORY_PULL_RESPONSE
                )
        ));
    }

    private static Middleware buildInventoryPullMiddlewareWithNoAuthentication() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_INVENTORY_PULL_REQUEST, AlpineBitsVersion.V_2018_10),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_INVENTORY_PULL_RESPONSE, AlpineBitsVersion.V_2018_10),
                InventoryPullMiddlewareBuilder.buildInventoryPullRequestMappingMiddleware(),
                InventoryPullMiddlewareBuilder.buildInventoryPullResponseMappingMiddleware(),
                new InventoryPullMiddleware(
                        HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY,
                        HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildInventoryPullRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_INVENTORY_PULL_REQUEST,
                HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER::toHotelDescriptiveInfoRequest
        );
    }

    private static Middleware buildInventoryPullResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY,
                OTA_INVENTORY_PULL_RESPONSE,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER::toOTAHotelDescriptiveInfoRS
        );
    }
}
