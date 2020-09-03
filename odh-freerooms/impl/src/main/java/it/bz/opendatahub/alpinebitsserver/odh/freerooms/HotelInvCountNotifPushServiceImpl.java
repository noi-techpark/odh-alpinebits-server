/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRS;
import it.bz.opendatahub.alpinebitsserver.application.common.utils.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service uses the ODH tourism data to store AlpineBits FreeRooms data
 * for AlpineBits versions 2020-10 and going on.
 */
public class HotelInvCountNotifPushServiceImpl implements FreeRoomsPushService<OTAHotelInvCountNotifRS> {

    private static final Logger LOG = LoggerFactory.getLogger(HotelInvCountNotifPushServiceImpl.class);

    private final OdhBackendService service;

    public HotelInvCountNotifPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public OTAHotelInvCountNotifRS write(PushWrapper pushWrapper) {
        try {
            service.pushFreeRooms(pushWrapper);

            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forSuccess();
            return toOTAHotelInvCountNotifRS(mat);
        } catch (OdhBackendException e) {
            LOG.error("ODH backend client error", e);
            String message = this.buildErrorMessage(e.getMessage(), pushWrapper.getRequestId());
            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forError(message);
            return toOTAHotelInvCountNotifRS(mat);
        }
    }

    private String buildErrorMessage(String message, String requestId) {
        return message + " (rid = " + requestId + ")";
    }

    private OTAHotelInvCountNotifRS toOTAHotelInvCountNotifRS(MessageAcknowledgementType mat) {
        OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = new OTAHotelInvCountNotifRS();
        otaHotelInvCountNotifRS.setErrors(mat.getErrors());
        otaHotelInvCountNotifRS.setSuccess(mat.getSuccess());
        otaHotelInvCountNotifRS.setVersion(mat.getVersion());
        return otaHotelInvCountNotifRS;
    }
}
