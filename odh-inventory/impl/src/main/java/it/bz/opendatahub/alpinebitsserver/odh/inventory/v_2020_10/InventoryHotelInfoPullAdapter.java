// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.OTAHotelDescriptiveInfoRSAdapter;

/**
 * This middleware removes elements and attributes that are not allowed in AlpineBits 2020-10
 * Inventory/HotelInfo action.
 * <p>
 * ODH as persisting backend saves AlpineBits inventory data for different AlpineBits versions.
 * Those versions may allow elements and attributes that are not allowed in AlpineBits 2020-10.
 * Therefor it is necessary to adapt the response data for AlpineBits 2020-10 before it is returned.
 * Otherwise, the XML validation may fail.
 * <p>
 * An example for the problem described is the definition of the <code>ContactInfos</code> element,
 * that changed between AlpineBits 2017-10 and 2020-10 versions: in 2017-10, the <code>ContactInfos</code>
 * element was allowed to contain all elements and attributes defined in OTA-2015A. In AlpineBits 2020-10
 * the usage was restricted to a subset of the allowed elements.
 * <p>
 * Please note, that this middleware only removes data to establish compatibility with AlpineBits 2020-10.
 * It does not provide missing data, because that is not possible most of the time. For example, if some
 * required element or attribute is missing (because that is allowed in a different AlpineBits version),
 * there is no way to build the missing data.
 */
public final class InventoryHotelInfoPullAdapter implements Middleware {

    private static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);
    private final Key<String> withExtendedHotelInfoServiceCodesKey;

    public InventoryHotelInfoPullAdapter(Key<String> withExtendedHotelInfoServiceCodesKey) {
        this.withExtendedHotelInfoServiceCodesKey = withExtendedHotelInfoServiceCodesKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // This middleware is invoked during the response-phase only
        // Therefor the request-phase chain (request phase) is invoked first
        chain.next();

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = ctx.getOrThrow(OTA_INVENTORY_PULL_RESPONSE);

        String alpineBitsAction = ctx.get(RequestContextKey.REQUEST_ACTION).orElse(null);

        // Remove FacilityInfo for Inventory/HotelInfo all together, because is not supported in
        // AlpineBits 2020-10 Inventory/HotelInfo anymore
        if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(alpineBitsAction)
                && hasFacilityInfoType(otaHotelDescriptiveInfoRS)) {
            otaHotelDescriptiveInfoRS
                    .getHotelDescriptiveContents()
                    .getHotelDescriptiveContents()
                    .get(0)
                    .setFacilityInfo(null);
        }

        boolean withExtendedHotelInfoServiceCodes = this.withExtendedHotelInfoServiceCodesKey != null
                && ctx.contains(this.withExtendedHotelInfoServiceCodesKey);

        OTAHotelDescriptiveInfoRSAdapter.removeUnsupported(otaHotelDescriptiveInfoRS, withExtendedHotelInfoServiceCodes);
    }

    private static boolean hasFacilityInfoType(OTAHotelDescriptiveInfoRS ota) {
        return ota.getHotelDescriptiveContents() != null
                && !ota.getHotelDescriptiveContents().getHotelDescriptiveContents().isEmpty()
                && ota.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).getFacilityInfo() != null;
    }

}
