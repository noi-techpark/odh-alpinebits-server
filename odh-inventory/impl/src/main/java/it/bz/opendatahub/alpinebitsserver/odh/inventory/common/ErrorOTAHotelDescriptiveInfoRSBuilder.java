// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;
import it.bz.opendatahub.alpinebits.common.utils.response.ErrorEntry;
import it.bz.opendatahub.alpinebits.common.utils.response.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebits.common.utils.response.ResponseOutcomeBuilder;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;

import java.util.Objects;

/**
 * Helper class to build error responses for {@link OTAHotelDescriptiveInfoRS} documents.
 */
public final class ErrorOTAHotelDescriptiveInfoRSBuilder {

    private ErrorOTAHotelDescriptiveInfoRSBuilder() {
        // Empty
    }

    public static OTAHotelDescriptiveInfoRS noDataFound(String hotelCode) {
        Objects.requireNonNull(hotelCode, "HotelCode must not be null");

        String message = String.format("No data for HotelCode %s found", hotelCode);
        ErrorEntry entry = new ErrorEntry(message, OTACodeErrorCodes.NO_MATCH_FOUND);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.error(entry);
        return ResponseOutcomeBuilder.forOTAHotelDescriptiveInfoRS(mat);
    }

}
