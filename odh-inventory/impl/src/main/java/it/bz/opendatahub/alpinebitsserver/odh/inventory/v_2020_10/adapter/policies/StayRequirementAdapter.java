/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.StayRequirements.StayRequirement;

/**
 * This adapter is used to remove elements from {@link StayRequirement} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class StayRequirementAdapter {

    private StayRequirementAdapter() {
        // Empty
    }

    public static void removeUnsupported(StayRequirement stayRequirement) {
        if (stayRequirement == null) {
            return;
        }

        stayRequirement.setDescription(null);
        stayRequirement.setDuration(null);
        stayRequirement.setFri(null);
        stayRequirement.setMaxLOS(null);
        stayRequirement.setMinLOS(null);
        stayRequirement.setMon(null);
        stayRequirement.setSat(null);
        stayRequirement.setSun(null);
        stayRequirement.setThur(null);
        stayRequirement.setTue(null);
        stayRequirement.setWeds(null);
    }

}
