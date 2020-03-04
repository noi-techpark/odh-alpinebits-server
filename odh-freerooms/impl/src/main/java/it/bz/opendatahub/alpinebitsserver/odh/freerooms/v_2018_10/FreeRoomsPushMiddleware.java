/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms.v_2018_10;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.freerooms.FreeRoomsPushService;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests.
 */
public class FreeRoomsPushMiddleware implements Middleware {

    private final Key<OTAHotelAvailNotifRQ> requestKey;
    private final Key<GenericResponse> responseKey;

    public FreeRoomsPushMiddleware(
            Key<OTAHotelAvailNotifRQ> requestKey,
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
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE_2018_10);

        String alpineBitsVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        OTAHotelAvailNotifRQ freeRoomsRequest = ctx.getOrThrow(this.requestKey);
        String accomodationId = this.getAccomodationId(freeRoomsRequest);

        PushWrapper pushWrapper = new PushWrapper();
        pushWrapper.setAlpineBitsVersion(alpineBitsVersion);
        pushWrapper.setAccommodationId(accomodationId);
        pushWrapper.setRequestId(requestId);
        pushWrapper.setMessage(freeRoomsRequest);

        FreeRoomsPushService service = new FreeRoomsPushServiceImpl(odhBackendService);
        return service.write(pushWrapper);
    }

    private String getAccomodationId(OTAHotelAvailNotifRQ freeRoomsRequest) {
        if (freeRoomsRequest == null || freeRoomsRequest.getAvailStatusMessages() == null) {
            return "";
        }

        return freeRoomsRequest.getAvailStatusMessages().getHotelCode();
    }
}
