// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.PetsPolicies.PetsPolicy;

/**
 * This adapter is used to remove elements from {@link PetsPolicy} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class PetsPolicyAdapter {

    private PetsPolicyAdapter() {
        // Empty
    }

    public static void removeUnsupported(PetsPolicy petsPolicy) {
        if (petsPolicy == null) {
            return;
        }

        petsPolicy.setMinUnitOfMeasureQuantity(null);
        petsPolicy.setPetsPolicyCode(null);
        petsPolicy.setRefundableDeposit(null);
        petsPolicy.setRestrictionInd(null);
        petsPolicy.setUnitOfMeasure(null);
        petsPolicy.setUnitOfMeasureCode(null);
        petsPolicy.setUnitOfMeasureQuantity(null);

        if (petsPolicy.getDescriptions() != null) {
            petsPolicy.getDescriptions().forEach(ParagraphTypeAdapter::removeUnsupported);
        }
    }

}
