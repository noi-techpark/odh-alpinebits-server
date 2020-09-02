/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service uses the ODH tourism data to store AlpineBits FreeRooms data
 * for AlpineBits versions up to 2018-10.
 */
public class HotelAvailNotifPushServiceImpl implements FreeRoomsPushService<OTAHotelAvailNotifRS> {

    private static final Logger LOG = LoggerFactory.getLogger(HotelAvailNotifPushServiceImpl.class);

    private final OdhBackendService service;

    public HotelAvailNotifPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public OTAHotelAvailNotifRS write(PushWrapper pushWrapper) {
        try {
            service.pushFreeRooms(pushWrapper);

            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forSuccess();
            return new OTAHotelAvailNotifRS(mat);
        } catch (OdhBackendException e) {
            LOG.error("ODH backend client error", e);
            String message = this.buildErrorMessage(e.getMessage(), pushWrapper.getRequestId());
            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forError(message);
            return new OTAHotelAvailNotifRS(mat);
        }
    }

    private String buildErrorMessage(String message, String requestId) {
        return message + " (rid = " + requestId + ")";
    }

}
