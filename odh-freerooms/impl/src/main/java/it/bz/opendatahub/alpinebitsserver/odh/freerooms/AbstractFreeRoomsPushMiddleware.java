/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendContextKey;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * An abstract {@link Middleware} that implements common functionality
 * to handle FreeRooms push requests.
 *
 * This base implementation can be used for AlpineBits FreeRooms requests
 * prior up to 2018-10 (OTAHotelAvailNotifRQ and OTAHotelAvailNotifRS) and
 * FreeRooms requests from 2020-10 going on (OTAHotelInvCountNotifRQ and
 * OTAHotelInvCountNotifRS).
 *
 * @param <T> The type of FreeRooms request data (OTAHotelAvailNotifRQ or OTAHotelInvCountNotifRQ).
 * @param <S> The type of FreeRooms response data (OTAHotelAvailNotifRS or OTAHotelInvCountNotifRS).
 */
public abstract class AbstractFreeRoomsPushMiddleware<T, S> implements Middleware {

    private final Key<T> requestKey;
    private final Key<S> responseKey;

    public AbstractFreeRoomsPushMiddleware(
            Key<T> requestKey,
            Key<S> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        S response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    protected abstract FreeRoomsPushService<S> getFreeRoomsPushService(OdhBackendService odhBackendService);

    protected abstract String getHotelCodeOrThrowOnEmpty(T request);

    private S invokeService(Context ctx) {
        // Get necessary objects from middleware context
        OdhBackendService odhBackendService = ctx.getOrThrow(OdhBackendContextKey.ODH_BACKEND_SERVICE);

        String alpineBitsVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        T freeRoomsRequest = ctx.getOrThrow(this.requestKey);
        String accommodationId = this.getHotelCodeOrThrowOnEmpty(freeRoomsRequest);

        PushWrapper pushWrapper = new PushWrapper();
        pushWrapper.setAlpineBitsVersion(alpineBitsVersion);
        pushWrapper.setAccommodationId(accommodationId);
        pushWrapper.setRequestId(requestId);
        pushWrapper.setMessage(freeRoomsRequest);

        FreeRoomsPushService<S> service = getFreeRoomsPushService(odhBackendService);
        return service.write(pushWrapper);
    }

}
