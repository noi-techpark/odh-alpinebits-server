// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AddressesType.Address;

/**
 * This adapter is used to remove elements from {@link Address} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class AddressAdapter {

    private AddressAdapter() {
        // Empty
    }

    public static void removeUnsupported(Address address) {
        if (address == null) {
            return;
        }

        address.setCounty(null);
        address.setDefaultInd(null);
        address.setFormattedInd(null);
        address.setID(null);
        address.setRemark(null);
        address.setRPH(null);
        address.setRemoval(null);
        address.setShareMarketInd(null);
        address.setShareSynchInd(null);
        address.setStreetNmbr(null);
        address.setTPAExtensions(null);
        address.setType(null);
        address.setUseType(null);
        address.setValidInd(null);

        // Remove unsupported elements and attributes from CountryName
        if (address.getCountryName() != null) {
            address.getCountryName().setValue(null);
        }

        // Remove unsupported elements and attributes from StateProv
        if (address.getStateProv() != null) {
            address.getStateProv().setValue(null);
        }

        // Remove unsupported elements and attributes from StateProv
        if (address.getCountryName() != null) {
            address.getCountryName().setValue(null);
        }
    }

}
