// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeExtractor;

/**
 * A {@link Middleware} to handle Inventory pull requests.
 * <p>
 * This middleware is invoked, if the credentials provided by
 * the AlpineBits request are valid.
 */
public class AuthenticatedInventoryPullMiddleware implements Middleware {

    private final Key<OTAHotelDescriptiveInfoRQ> requestKey;
    private final Key<OTAHotelDescriptiveInfoRS> responseKey;

    public AuthenticatedInventoryPullMiddleware(
            Key<OTAHotelDescriptiveInfoRQ> requestKey,
            Key<OTAHotelDescriptiveInfoRS> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        OTAHotelDescriptiveInfoRS response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private OTAHotelDescriptiveInfoRS invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);

        OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ = ctx.getOrThrow(requestKey);

        String hotelCode = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelDescriptiveInfoRQ);

        AuthenticatedInventoryPullServiceImpl service = new AuthenticatedInventoryPullServiceImpl(odhBackendService);

        // Call service for persistence
        if (AlpineBitsAction.INVENTORY_BASIC_PULL.equals(action)) {
            return service.readBasic(hotelCode);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action)) {
            return service.readHotelInfo(hotelCode);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }

}
