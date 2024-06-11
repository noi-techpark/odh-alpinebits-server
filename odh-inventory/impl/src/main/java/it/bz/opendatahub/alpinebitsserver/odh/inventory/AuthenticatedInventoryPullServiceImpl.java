// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.ErrorOTAHotelDescriptiveInfoRSBuilder;

import java.math.BigDecimal;

/**
 * This service uses the ODH tourism data to provide a response to
 * an AlpineBits Inventory/Basic pull request with valid authentication.
 */
public class AuthenticatedInventoryPullServiceImpl {

    private final OdhBackendService service;

    public AuthenticatedInventoryPullServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    public OTAHotelDescriptiveInfoRS readBasic(String hotelCode) {
        return this.service.fetchInventoryBasic(hotelCode)
                .map(this::addSuccess)
                .orElse(ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(hotelCode));
    }

    public OTAHotelDescriptiveInfoRS readHotelInfo(String hotelCode) {
        return this.service.fetchInventoryHotelInfo(hotelCode)
                .map(this::addSuccess)
                .orElse(ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(hotelCode));
    }

    private OTAHotelDescriptiveInfoRS addSuccess(OTAHotelDescriptiveInfoRS ota) {
        ota.setSuccess(new SuccessType());
        ota.setVersion(BigDecimal.valueOf(8.000));
        return ota;
    }

}
