// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.TextDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link TextItem} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class TextItemAdapter {

    private TextItemAdapter() {
        // Empty
    }

    public static void removeUnsupported(TextItem textItem) {
        if (textItem == null) {
            return;
        }

        textItem.setApplicableEnd(null);
        textItem.setApplicableStart(null);
        textItem.setAuthor(null);
        textItem.setCategory(null);
        textItem.setContentID(null);
        textItem.setCopyrightEnd(null);
        textItem.setCopyrightOwner(null);
        textItem.setCopyrightStart(null);
        textItem.setCreateDateTime(null);
        textItem.setCreatorID(null);
        textItem.setEffectiveEnd(null);
        textItem.setEffectiveStart(null);
        textItem.setLanguage(null);
        textItem.setLastModifierID(null);
        textItem.setLastModifyDateTime(null);
        textItem.setPurgeDate(null);
        textItem.setRecordID(null);
        textItem.setRemoval(null);
        textItem.setTitle(null);
        textItem.setURL(null);
        textItem.setVersion(null);

        extractTextDescriptions(textItem).forEach(description -> {
            description.setFormatted(null);
            description.setListItem(null);
        });
    }

    private static List<TextDescriptionType.Description> extractTextDescriptions(TextItem textItem) {
        return textItem != null ? textItem.getDescriptions() : Collections.emptyList();
    }
}
