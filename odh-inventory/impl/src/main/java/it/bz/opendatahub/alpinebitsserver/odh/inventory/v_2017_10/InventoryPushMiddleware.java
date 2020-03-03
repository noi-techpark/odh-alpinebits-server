/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2017_10;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests.
 */
public class InventoryPushMiddleware implements Middleware {

    private final Key<OTAHotelDescriptiveContentNotifRQ> requestKey;
    private final Key<GenericResponse> responseKey;

    public InventoryPushMiddleware(
            Key<OTAHotelDescriptiveContentNotifRQ> requestKey,
            Key<GenericResponse> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        GenericResponse response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private GenericResponse invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE_2017_10);
        String alpineBitsVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        OTAHotelDescriptiveContentNotifRQ inventoryPushRequest = ctx.getOrThrow(this.requestKey);

        String accomodationId = this.getAccomodationId(inventoryPushRequest);

        PushWrapper pushWrapper = new PushWrapper();
        pushWrapper.setAlpineBitsVersion(alpineBitsVersion);
        pushWrapper.setAccommodationId(accomodationId);
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

    private String getAccomodationId(OTAHotelDescriptiveContentNotifRQ inventoryRequest) {
        if (inventoryRequest == null
                || inventoryRequest.getHotelDescriptiveContents() == null
                || inventoryRequest.getHotelDescriptiveContents().getHotelDescriptiveContent() == null
        ) {
            return "";
        }

        return inventoryRequest.getHotelDescriptiveContents().getHotelDescriptiveContent().getHotelCode();
    }
}
