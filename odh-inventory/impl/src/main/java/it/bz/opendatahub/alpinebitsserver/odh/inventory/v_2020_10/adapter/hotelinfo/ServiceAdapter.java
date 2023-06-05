// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo;

import it.bz.opendatahub.alpinebits.xml.schema.ota.FeaturesType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelInfoType.Services.Service;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link Service} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class ServiceAdapter {

    private ServiceAdapter() {
        // Empty
    }

    public static void removeUnsupported(Service service, boolean withExtendedHotelInfoServiceCodes) {
        if (service == null) {
            return;
        }

        // Don't remove CodeDetail and ExistsCode elements if the extended HotelInfo Service Codes are included
        if (!withExtendedHotelInfoServiceCodes) {
            service.setCodeDetail(null);
            service.setExistsCode(null);
        }

        service.setAvailableToAnyGuest(null);
        service.setBusinessServiceCode(null);
        service.setContact(null);
        service.setDescriptiveText(null);
        service.setFeaturedInd(null);
        service.setID(null);
        service.setInvCode(null);
        service.setMealPlanCode(null);
        service.setMeetingRoomCode(null);
        service.setMultimediaDescriptions(null);
        service.setOperationSchedules(null);
        service.setQuantity(null);
        service.setRelativePosition(null);
        service.setRemoval(null);
        service.setSort(null);

        extractFeatures(service).forEach(FeatureAdapter::removeUnsupported);
    }

    private static List<FeaturesType.Feature> extractFeatures(Service service) {
        return service != null && service.getFeatures() != null
                ? service.getFeatures().getFeatures()
                : Collections.emptyList();
    }
}
