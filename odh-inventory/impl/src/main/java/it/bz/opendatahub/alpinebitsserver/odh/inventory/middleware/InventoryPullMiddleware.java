/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.middleware;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.impl.OdhInventoryPullService;

/**
 * A simple {@link Middleware} to handle Inventory pull
 * requests, returning data from DB.
 */
public class InventoryPullMiddleware implements Middleware {

    private final Key<HotelDescriptiveInfoRequest> requestKey;
    private final Key<HotelDescriptiveInfoResponse> responseKey;

    public InventoryPullMiddleware(
            Key<HotelDescriptiveInfoRequest> requestKey,
            Key<HotelDescriptiveInfoResponse> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        HotelDescriptiveInfoResponse response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private HotelDescriptiveInfoResponse invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest = ctx.getOrThrow(this.requestKey);
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);

        OdhInventoryPullService service = new OdhInventoryPullService(odhBackendService);

        // Call service for persistence
        if (AlpineBitsAction.INVENTORY_BASIC_PULL.equals(action)) {
            return service.readBasic(hotelDescriptiveInfoRequest);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action)) {
            return service.readHotelInfo(hotelDescriptiveInfoRequest);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }
}
