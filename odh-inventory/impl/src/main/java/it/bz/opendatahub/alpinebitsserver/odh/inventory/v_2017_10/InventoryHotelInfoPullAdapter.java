// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2017_10;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;

/**
 * This middleware removes elements and attributes that are not allowed in AlpineBits 2017-10
 * Inventory/HotelInfo action.
 */
public final class InventoryHotelInfoPullAdapter implements Middleware {

    private static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // This middleware is invoked during the response-phase only
        // Therefor the request-phase chain (request phase) is invoked first
        chain.next();

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = ctx.getOrThrow(OTA_INVENTORY_PULL_RESPONSE);

        // Set AreaID to null (AreaID is allowed in AlpineBits 2020-10)
        if (otaHotelDescriptiveInfoRS.getHotelDescriptiveContents() != null
                && !otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().isEmpty()) {
            otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).setAreaID(null);
        }
    }

}
