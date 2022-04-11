/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeExtractor;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests for
 * AlpineBits versions up to 2018-10.
 */
public class HotelAvailNotifPushMiddleware extends AbstractFreeRoomsPushMiddleware<OTAHotelAvailNotifRQ, OTAHotelAvailNotifRS> {

    public HotelAvailNotifPushMiddleware(
            Key<OTAHotelAvailNotifRQ> requestKey,
            Key<OTAHotelAvailNotifRS> responseKey
    ) {
        super(requestKey, responseKey);
    }

    @Override
    protected FreeRoomsPushService<OTAHotelAvailNotifRS> getFreeRoomsPushService(OdhBackendService odhBackendService) {
        return new HotelAvailNotifPushServiceImpl(odhBackendService);
    }

    protected String getHotelCodeOrThrowOnEmpty(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        return HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelAvailNotifRQ);
    }
}
