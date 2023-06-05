// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.common;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;

import java.util.Collections;
import java.util.List;

/**
 * This class is used to extract {@link HotelDescriptiveContent} elements.
 */
public final class HotelDescriptiveContentExtractor {

    private HotelDescriptiveContentExtractor() {
        // Empty
    }

    /**
     * Extract the list of {@link HotelDescriptiveContent} elements from the given {@link OTAHotelDescriptiveInfoRS},
     * if such elements exists.
     *
     * @param otaHotelDescriptiveInfoRS The base element, from where the list of
     *                                  {@link HotelDescriptiveContent} elements is taken.
     * @return The list of {@link HotelDescriptiveContent} elements, if such a list exists. An empty list otherwise.
     */
    public static List<HotelDescriptiveContent> extractHotelDescriptiveContent(OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS) {
        return otaHotelDescriptiveInfoRS != null && otaHotelDescriptiveInfoRS.getHotelDescriptiveContents() != null
                ? otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents()
                : Collections.emptyList();
    }
}
