// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.affiliationinfo;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType.Awards.Award;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link AffiliationInfoType} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class AffiliationInfoTypeAdapter {

    private AffiliationInfoTypeAdapter() {
        // Empty
    }

    /**
     * Remove unsupported (sub) elements and attributes from the {@link AffiliationInfoType} element.
     *
     * @param affiliationInfoType This element needs to be cleaned up in order to be AlpineBits 2020-10 compatible.
     */
    public static void removeUnsupported(AffiliationInfoType affiliationInfoType) {
        if (affiliationInfoType == null) {
            return;
        }

        affiliationInfoType.setBrands(null);
        affiliationInfoType.setDescriptions(null);
        affiliationInfoType.setDistribSystems(null);
        affiliationInfoType.setLastUpdated(null);
        affiliationInfoType.setLoyalPrograms(null);
        affiliationInfoType.setPartnerInfos(null);

        extractAwards(affiliationInfoType).forEach(AwardAdapter::removeUnsupported);
    }

    private static List<Award> extractAwards(AffiliationInfoType affiliationInfoType) {
        return affiliationInfoType.getAwards() != null
                ? affiliationInfoType.getAwards().getAwards()
                : Collections.emptyList();
    }
}
