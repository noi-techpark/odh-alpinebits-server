// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;

/**
 * A service to handle an AlpineBits Inventory/Basic push requests.
 */
public interface InventoryPushService {

    /**
     * Write a Inventory/Basic information.
     *
     * @param pushWrapper this element contains the Inventory information
     *                    that should be written to ODH
     * @return a {@link OTAHotelDescriptiveContentNotifRS} containing the response
     */
    OTAHotelDescriptiveContentNotifRS writeBasic(PushWrapper pushWrapper);

    /**
     * Write a Inventory/Basic information.
     *
     * @param pushWrapper this element contains the Inventory information
     *                    that should be written to ODH
     * @return a {@link OTAHotelDescriptiveContentNotifRS} containing the response
     */
    OTAHotelDescriptiveContentNotifRS writeHotelInfo(PushWrapper pushWrapper);

}
