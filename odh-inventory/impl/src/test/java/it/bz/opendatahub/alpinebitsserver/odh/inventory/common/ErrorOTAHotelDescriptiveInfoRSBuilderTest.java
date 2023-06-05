// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link ErrorOTAHotelDescriptiveInfoRSBuilder}.
 */
public class ErrorOTAHotelDescriptiveInfoRSBuilderTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void testForHotelCode_ShouldThrow_WhenHotelCodeIsNull() {
        ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(null);
    }

    @Test
    public void testForHotelCode_ShouldReturnErrorResultWithHotelCode() {
        String hotelCode = "123";
        OTAHotelDescriptiveInfoRS ota = ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(hotelCode);
        assertNull(ota.getSuccess());
        assertNotNull(ota.getVersion());
        assertNotNull(ota.getErrors());
    }

}