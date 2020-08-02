/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos;

import it.bz.opendatahub.alpinebits.xml.schema.ota.URLsType.URL;

/**
 * This adapter is used to remove elements from {@link URL} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class URLAdapter {

    private URLAdapter() {
        // Empty
    }

    public static void removeUnsupported(URL url) {
        if (url == null) {
            return;
        }

        url.setDefaultInd(null);
        url.setType(null);
    }

}
