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

/**
 * A simple {@link Middleware} to handle Inventory pull
 * requests, returning data from DB.
 */
public class InventoryPullMiddleware implements Middleware {

    private final Key<OTAHotelDescriptiveInfoRQ> requestKey;
    private final Key<OTAHotelDescriptiveInfoRS> responseKey;
    private final Key<String> withExtendedHotelInfoServiceCodesKey;

    public InventoryPullMiddleware(
            Key<OTAHotelDescriptiveInfoRQ> requestKey,
            Key<OTAHotelDescriptiveInfoRS> responseKey,
            Key<String> withExtendedHotelInfoServiceCodesKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
        this.withExtendedHotelInfoServiceCodesKey = withExtendedHotelInfoServiceCodesKey;
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
        OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ = ctx.getOrThrow(this.requestKey);
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);

        boolean withExtendedHotelInfoServiceCodesHeaderFound = this.withExtendedHotelInfoServiceCodesKey != null
                && ctx.contains(this.withExtendedHotelInfoServiceCodesKey);

        InventoryPullServiceImpl service = new InventoryPullServiceImpl(odhBackendService, withExtendedHotelInfoServiceCodesHeaderFound);

        // Call service for persistence
        if (AlpineBitsAction.INVENTORY_BASIC_PULL.equals(action)) {
            return service.readBasic(otaHotelDescriptiveInfoRQ);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action)) {
            return service.readHotelInfo(otaHotelDescriptiveInfoRQ);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }
}
