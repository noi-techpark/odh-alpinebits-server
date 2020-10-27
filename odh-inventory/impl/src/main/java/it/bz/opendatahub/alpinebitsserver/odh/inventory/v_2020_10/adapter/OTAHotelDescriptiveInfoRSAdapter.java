/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.affiliationinfo.AffiliationInfoTypeAdapter;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.common.HotelDescriptiveContentExtractor;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos.ContactInfosTypeAdapter;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.HotelInfoTypeAdapter;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies.PoliciesAdapter;

/**
 * This adapter is used to remove elements from {@link OTAHotelDescriptiveInfoRS} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class OTAHotelDescriptiveInfoRSAdapter {

    private OTAHotelDescriptiveInfoRSAdapter() {
        // Empty
    }

    public static void removeUnsupported(OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS, boolean withExtendedHotelInfoServiceCodes) {
        if (otaHotelDescriptiveInfoRS == null) {
            throw new IllegalArgumentException("OTAHotelDescriptiveInfoRS must not be null");
        }

        HotelDescriptiveContentExtractor
                .extractHotelDescriptiveContent(otaHotelDescriptiveInfoRS)
                .forEach(hotelDescriptiveContent -> {
                    // Remove unsupported elements and attributes from HotelInfo element
                    HotelInfoTypeAdapter.removeUnsupported(hotelDescriptiveContent.getHotelInfo(), withExtendedHotelInfoServiceCodes);

                    // Remove unsupported elements and attributes from Policies element
                    PoliciesAdapter.removeUnsupported(hotelDescriptiveContent.getPolicies());

                    // Remove unsupported elements and attributes from AffiliationInfo element
                    AffiliationInfoTypeAdapter.removeUnsupported(hotelDescriptiveContent.getAffiliationInfo());

                    // Remove unsupported elements and attributes from ContactInfo element
                    ContactInfosTypeAdapter.removeUnsupported(hotelDescriptiveContent.getContactInfos());
                });
    }

}
