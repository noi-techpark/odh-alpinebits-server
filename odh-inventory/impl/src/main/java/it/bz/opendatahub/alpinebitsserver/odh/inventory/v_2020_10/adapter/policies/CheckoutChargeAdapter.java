// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.CheckoutCharges.CheckoutCharge;

/**
 * This adapter is used to remove elements from {@link CheckoutCharge} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class CheckoutChargeAdapter {

    private CheckoutChargeAdapter() {
        // Empty
    }

    public static void removeUnsupported(CheckoutCharge checkoutCharge) {
        if (checkoutCharge == null) {
            return;
        }

        checkoutCharge.setBalanceOfStayInd(null);
        checkoutCharge.setCodeDetail(null);
        checkoutCharge.setExistsCode(null);
        checkoutCharge.setNmbrOfNights(null);
        checkoutCharge.setPercent(null);
        checkoutCharge.setRemoval(null);
        checkoutCharge.setType(null);

        if (checkoutCharge.getDescriptions() != null) {
            checkoutCharge.getDescriptions().forEach(ParagraphTypeAdapter::removeUnsupported);
        }
    }

}
