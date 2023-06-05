// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.TaxPolicies.TaxPolicy;

/**
 * This adapter is used to remove elements from {@link TaxPolicy} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class TaxPolicyAdapter {

    private TaxPolicyAdapter() {
        // Empty
    }

    public static void removeUnsupported(TaxPolicy taxPolicy) {
        if (taxPolicy == null) {
            return;
        }

        taxPolicy.setChargeFrequencyExempt(null);
        taxPolicy.setChargeUnitExempt(null);
        taxPolicy.setDuration(null);
        taxPolicy.setEffectiveDate(null);
        taxPolicy.setExpireDate(null);
        taxPolicy.setExpireDateExclusiveInd(null);
        taxPolicy.setMaxChargeFrequencyApplies(null);
        taxPolicy.setMaxChargeUnitApplies(null);
        taxPolicy.setNightsForTaxExemptionQuantity(null);
        taxPolicy.setPercent(null);
        taxPolicy.setSequenceNbr(null);
        taxPolicy.setTaxableNightsQuantity(null);
        taxPolicy.setTPAExtensions(null);
        taxPolicy.setType(null);

        if (taxPolicy.getTaxDescriptions() != null) {
            taxPolicy.getTaxDescriptions().forEach(ParagraphTypeAdapter::removeUnsupported);
        }
    }

}
