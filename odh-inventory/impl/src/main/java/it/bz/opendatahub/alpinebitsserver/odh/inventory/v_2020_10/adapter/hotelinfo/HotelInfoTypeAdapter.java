// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo;

import it.bz.opendatahub.alpinebits.xml.schema.ota.CategoryCodesType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.CategoryCodesType.HotelCategory;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelInfoType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelInfoType.Descriptions;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelInfoType.Services.Service;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription.MultimediaDescriptionTypeAdapter;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link HotelInfoType} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class HotelInfoTypeAdapter {

    private HotelInfoTypeAdapter() {
        // Empty
    }

    /**
     * Remove unsupported (sub) elements and attributes from the {@link HotelInfoType} element.
     *
     * @param hotelInfoType                     This element needs to be cleaned up in order to be AlpineBits 2020-10 compatible.
     * @param withExtendedHotelInfoServiceCodes If this parameter is set to <code>true</code>, then extended service codes are
     *                                          included and <code>CodeDetail</code> and <code>ExistsCode</code> elements should
     *                                          not be removed.
     */
    public static void removeUnsupported(HotelInfoType hotelInfoType, boolean withExtendedHotelInfoServiceCodes) {
        if (hotelInfoType == null) {
            return;
        }

        hotelInfoType.setAreaWeather(null);
        hotelInfoType.setBlackoutDates(null);
        hotelInfoType.setClosedSeasons(null);
        hotelInfoType.setDaylightSavingIndicator(null);
        hotelInfoType.setDuration(null);
        hotelInfoType.setEnd(null);
        hotelInfoType.setHotelInfoCodes(null);
        hotelInfoType.setHotelName(null);
        hotelInfoType.setHotelStatus(null);
        hotelInfoType.setHotelStatusCode(null);
        hotelInfoType.setInterfaceCompliance(null);
        hotelInfoType.setISO9000CertifiedInd(null);
        hotelInfoType.setLanguages(null);
        hotelInfoType.setLastUpdated(null);
        hotelInfoType.setOwnershipManagementInfos(null);
        hotelInfoType.setPMSSystem(null);
        hotelInfoType.setRelativePositions(null);
        hotelInfoType.setStart(null);
        hotelInfoType.setTaxID(null);
        hotelInfoType.setWeatherInfos(null);
        hotelInfoType.setWhenBuilt(null);

        // Cleanup CategoryCodes
        if (hotelInfoType.getCategoryCodes() != null) {
            CategoryCodesType categoryCodesType = hotelInfoType.getCategoryCodes();

            categoryCodesType.getArchitecturalStyles().clear();
            categoryCodesType.getGuestRoomInfos().clear();
            categoryCodesType.getLocationCategories().clear();
            categoryCodesType.getSegmentCategories().clear();

            extractHotelCategories(categoryCodesType).forEach(hotelCategory -> {
                hotelCategory.setCode(null);
                hotelCategory.setExistsCode(null);
                hotelCategory.setRemoval(null);
            });
        }

        // Cleanup Descriptions
        if (hotelInfoType.getDescriptions() != null) {
            Descriptions descriptions = hotelInfoType.getDescriptions();

            descriptions.getRenovations().clear();
            descriptions.setDescriptiveText(null);

            extractMultimediaDescriptions(descriptions).forEach(MultimediaDescriptionTypeAdapter::removeUnsupported);
        }

        // Cleanup Position
        if (hotelInfoType.getPosition() != null) {
            hotelInfoType.getPosition().setPositionAccuracyCode(null);
        }

        // Cleanup Services
        extractServices(hotelInfoType).forEach(service -> ServiceAdapter.removeUnsupported(service, withExtendedHotelInfoServiceCodes));
    }

    private static List<HotelCategory> extractHotelCategories(CategoryCodesType categoryCodesType) {
        return categoryCodesType != null ? categoryCodesType.getHotelCategories() : Collections.emptyList();
    }

    private static List<MultimediaDescriptionType> extractMultimediaDescriptions(Descriptions descriptions) {
        return descriptions != null && descriptions.getMultimediaDescriptions() != null
                ? descriptions.getMultimediaDescriptions().getMultimediaDescriptions()
                : Collections.emptyList();
    }

    private static List<Service> extractServices(HotelInfoType hotelInfoType) {
        return hotelInfoType != null && hotelInfoType.getServices() != null
                ? hotelInfoType.getServices().getServices()
                : Collections.emptyList();
    }

}
