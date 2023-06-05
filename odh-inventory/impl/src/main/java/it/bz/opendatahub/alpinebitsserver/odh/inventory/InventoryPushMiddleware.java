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
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeExtractor;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests.
 */
public class InventoryPushMiddleware implements Middleware {

    private final Key<OTAHotelDescriptiveContentNotifRQ> requestKey;
    private final Key<OTAHotelDescriptiveContentNotifRS> responseKey;

    public InventoryPushMiddleware(
            Key<OTAHotelDescriptiveContentNotifRQ> requestKey,
            Key<OTAHotelDescriptiveContentNotifRS> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        OTAHotelDescriptiveContentNotifRS response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private OTAHotelDescriptiveContentNotifRS invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);
        String alpineBitsVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        OTAHotelDescriptiveContentNotifRQ inventoryPushRequest = ctx.getOrThrow(this.requestKey);

        String accommodationId = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(inventoryPushRequest);

        PushWrapper pushWrapper = new PushWrapper();
        pushWrapper.setAlpineBitsVersion(alpineBitsVersion);
        pushWrapper.setAccommodationId(accommodationId);
        pushWrapper.setRequestId(requestId);
        pushWrapper.setMessage(inventoryPushRequest);

        // Call service for persistence
        InventoryPushServiceImpl service = new InventoryPushServiceImpl(odhBackendService);

        if (AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(action)) {
            return service.writeBasic(pushWrapper);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH.equals(action)) {
            return service.writeHotelInfo(pushWrapper);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }

}
