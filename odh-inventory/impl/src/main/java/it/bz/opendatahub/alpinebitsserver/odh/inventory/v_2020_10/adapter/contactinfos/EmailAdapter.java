// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos;

import it.bz.opendatahub.alpinebits.xml.schema.ota.EmailsType.Email;

/**
 * This adapter is used to remove elements from {@link Email} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class EmailAdapter {

    private EmailAdapter() {
        // Empty
    }

    public static void removeUnsupported(Email email) {
        if (email == null) {
            return;
        }

        email.setDefaultInd(null);
        email.setID(null);
        email.setRemark(null);
        email.setRemoval(null);
        email.setRPH(null);
        email.setShareMarketInd(null);
        email.setShareSynchInd(null);
        email.setTextFormat(null);
        email.setValidInd(null);
    }

}
