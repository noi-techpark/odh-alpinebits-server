// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos;

import it.bz.opendatahub.alpinebits.xml.schema.ota.PhonesType.Phone;

/**
 * This adapter is used to remove elements from {@link Phone} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class PhoneAdapter {

    private PhoneAdapter() {
        // Empty
    }

    public static void removeUnsupported(Phone phone) {
        if (phone == null) {
            return;
        }

        phone.setAreaCityCode(null);
        phone.setCountryAccessCode(null);
        phone.setDefaultInd(null);
        phone.setExtension(null);
        phone.setFormattedInd(null);
        phone.setID(null);
        phone.setPhoneLocationType(null);
        phone.setPhoneUseType(null);
        phone.setPIN(null);
        phone.setRemark(null);
        phone.setRPH(null);
        phone.setShareMarketInd(null);
        phone.setShareSynchInd(null);
    }

}
