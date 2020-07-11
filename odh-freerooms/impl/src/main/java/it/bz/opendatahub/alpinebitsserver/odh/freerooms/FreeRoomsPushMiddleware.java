/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests.
 */
public class FreeRoomsPushMiddleware implements Middleware {

    private final Key<OTAHotelAvailNotifRQ> requestKey;
    private final Key<OTAHotelAvailNotifRS> responseKey;

    public FreeRoomsPushMiddleware(
            Key<OTAHotelAvailNotifRQ> requestKey,
            Key<OTAHotelAvailNotifRS> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        OTAHotelAvailNotifRS response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private OTAHotelAvailNotifRS invokeService(Context ctx) {
        // Get necessary objects from middleware context
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);

        String alpineBitsVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        OTAHotelAvailNotifRQ freeRoomsRequest = ctx.getOrThrow(this.requestKey);
        String accomodationId = this.getHotelCodeOrThrowOnEmpty(freeRoomsRequest);

        PushWrapper pushWrapper = new PushWrapper();
        pushWrapper.setAlpineBitsVersion(alpineBitsVersion);
        pushWrapper.setAccommodationId(accomodationId);
        pushWrapper.setRequestId(requestId);
        pushWrapper.setMessage(freeRoomsRequest);

        FreeRoomsPushService service = new FreeRoomsPushServiceImpl(odhBackendService);
        return service.write(pushWrapper);
    }

    private String getHotelCodeOrThrowOnEmpty(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        if (otaHotelAvailNotifRQ == null) {
            throw new AlpineBitsException("Element OTAHotelAvailNotifRQ is required", 400);
        }
        if (otaHotelAvailNotifRQ.getAvailStatusMessages() == null) {
            throw new AlpineBitsException("Element AvailStatusMessages is required", 400);
        }
        if (otaHotelAvailNotifRQ.getAvailStatusMessages().getHotelCode() == null) {
            throw new AlpineBitsException("Attribute HotelCode is required", 400);
        }

        return otaHotelAvailNotifRQ.getAvailStatusMessages().getHotelCode();
    }
}
