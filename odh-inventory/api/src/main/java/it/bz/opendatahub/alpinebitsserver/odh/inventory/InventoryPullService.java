// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;

/**
 * A service to handle an AlpineBits Inventory/Basic pull requests.
 */
public interface InventoryPullService {

    /**
     * Read and return Inventory Basic information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link OTAHotelDescriptiveInfoRS} with Inventory Basic information
     */
    OTAHotelDescriptiveInfoRS readBasic(OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRequest);

    /**
     * Read and return Inventory HotelInfo information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link OTAHotelDescriptiveInfoRS} with Inventory HotelInfo information
     */
    OTAHotelDescriptiveInfoRS readHotelInfo(OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRequest);

}
