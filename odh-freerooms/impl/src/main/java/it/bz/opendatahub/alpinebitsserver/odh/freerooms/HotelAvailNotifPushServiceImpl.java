// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.common.utils.response.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebits.common.utils.response.ResponseOutcomeBuilder;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * This service uses the ODH tourism data to store AlpineBits FreeRooms data
 * for AlpineBits versions up to 2018-10.
 */
public class HotelAvailNotifPushServiceImpl implements FreeRoomsPushService<OTAHotelAvailNotifRS> {

    private final OdhBackendService service;

    public HotelAvailNotifPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public OTAHotelAvailNotifRS write(PushWrapper pushWrapper) {
        service.pushFreeRooms(pushWrapper);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.success();
        return ResponseOutcomeBuilder.forOTAHotelAvailNotifRS(mat);
    }

}
