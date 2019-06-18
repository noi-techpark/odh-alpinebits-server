/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;

/**
 * A service to handle an AlpineBits Inventory/Basic pull requests.
 */
public interface InventoryPullService {

    /**
     * Read and return Inventory Basic information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link HotelDescriptiveInfoResponse} with Inventory Basic information
     */
    HotelDescriptiveInfoResponse readBasic(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest);

    /**
     * Read and return Inventory HotelInfo information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link HotelDescriptiveInfoResponse} with Inventory HotelInfo information
     */
    HotelDescriptiveInfoResponse readHotelInfo(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest);

}
