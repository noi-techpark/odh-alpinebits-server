// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.HotelCodeExtractor;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * A simple {@link Middleware} to handle FreeRooms push requests for
 * AlpineBits versions from 2020-10 going on.
 */
public class HotelInvCountNotifPushMiddleware extends AbstractFreeRoomsPushMiddleware<OTAHotelInvCountNotifRQ, OTAHotelInvCountNotifRS> {

    public HotelInvCountNotifPushMiddleware(
            Key<OTAHotelInvCountNotifRQ> requestKey,
            Key<OTAHotelInvCountNotifRS> responseKey
    ) {
        super(requestKey, responseKey);
    }

    @Override
    protected FreeRoomsPushService<OTAHotelInvCountNotifRS> getFreeRoomsPushService(OdhBackendService odhBackendService) {
        return new HotelInvCountNotifPushServiceImpl(odhBackendService);
    }

    protected String getHotelCodeOrThrowOnEmpty(OTAHotelInvCountNotifRQ otaHotelInvCountNotifRQ) {
        return HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelInvCountNotifRQ);
    }
}
