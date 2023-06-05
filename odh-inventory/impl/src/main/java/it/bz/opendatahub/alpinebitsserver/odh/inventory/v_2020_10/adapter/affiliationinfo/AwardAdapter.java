// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.affiliationinfo;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType.Awards.Award;

/**
 * This adapter is used to remove elements from {@link Award} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class AwardAdapter {

    private AwardAdapter() {
        // Empty
    }

    public static void removeUnsupported(Award award) {
        if (award == null) {
            return;
        }

        award.setDate(null);
        award.setOfficialAppointmentInd(null);
        award.setRatingSymbol(null);
        award.setRPH(null);
        award.setRemoval(null);
    }

}
