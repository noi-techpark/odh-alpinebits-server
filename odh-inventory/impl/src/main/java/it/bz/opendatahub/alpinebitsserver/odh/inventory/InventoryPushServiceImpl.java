/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.common.utils.response.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebits.common.utils.response.ResponseOutcomeBuilder;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;

/**
 * This service uses the ODH tourism data to store AlpineBits Inventory data.
 */
public class InventoryPushServiceImpl implements InventoryPushService {

    private final OdhBackendService service;

    public InventoryPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public OTAHotelDescriptiveContentNotifRS writeBasic(PushWrapper pushWrapper) {
        service.pushInventoryBasic(pushWrapper);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.success();
        return ResponseOutcomeBuilder.forOTAHotelDescriptiveContentNotifRS(mat);
    }

    @Override
    public OTAHotelDescriptiveContentNotifRS writeHotelInfo(PushWrapper pushWrapper) {
        service.pushInventoryHotelInfo(pushWrapper);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.success();
        return ResponseOutcomeBuilder.forOTAHotelDescriptiveContentNotifRS(mat);
    }

}
