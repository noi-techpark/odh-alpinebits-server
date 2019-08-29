/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;

/**
 * A service to handle an AlpineBits Inventory/Basic push requests.
 */
public interface InventoryPushService {

    /**
     * Write a Inventory/Basic information.
     *
     * @param pushWrapper this element contains the FreeRooms information
     *                    that should be written to ODH
     * @return a {@link GenericResponse} containing the response
     */
    GenericResponse writeBasic(PushWrapper pushWrapper);

    /**
     * Write a Inventory/Basic information.
     *
     * @param pushWrapper this element contains the FreeRooms information
     *                    that should be written to ODH
     * @return a {@link GenericResponse} containing the response
     */
    GenericResponse writeHotelInfo(PushWrapper pushWrapper);

}
