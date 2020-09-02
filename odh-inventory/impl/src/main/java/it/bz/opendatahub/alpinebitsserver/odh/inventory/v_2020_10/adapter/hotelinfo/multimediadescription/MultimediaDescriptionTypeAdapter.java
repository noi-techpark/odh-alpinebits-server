/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;
import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoItemsType;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link MultimediaDescriptionType} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class MultimediaDescriptionTypeAdapter {

    private MultimediaDescriptionTypeAdapter() {
        // Empty
    }

    public static void removeUnsupported(MultimediaDescriptionType multimediaDescriptionType) {
        if (multimediaDescriptionType == null) {
            return;
        }

        multimediaDescriptionType.setAdditionalDetailCode(null);
        multimediaDescriptionType.setID(null);
        multimediaDescriptionType.setLastUpdated(null);
        multimediaDescriptionType.setVersion(null);

        extractImageItems(multimediaDescriptionType).forEach(ImageItemAdapter::removeUnsupported);

        extractTextItems(multimediaDescriptionType).forEach(TextItemAdapter::removeUnsupported);

        extractVideoItems(multimediaDescriptionType).forEach(VideoItemAdapter::removeUnsupported);
    }

    private static List<ImageItem> extractImageItems(MultimediaDescriptionType multimediaDescriptionType) {
        return multimediaDescriptionType != null && multimediaDescriptionType.getImageItems() != null
                ? multimediaDescriptionType.getImageItems().getImageItems()
                : Collections.emptyList();
    }

    private static List<TextItem> extractTextItems(MultimediaDescriptionType multimediaDescriptionType) {
        return multimediaDescriptionType != null && multimediaDescriptionType.getTextItems() != null
                ? multimediaDescriptionType.getTextItems().getTextItems()
                : Collections.emptyList();
    }

    private static List<VideoItemsType.VideoItem> extractVideoItems(MultimediaDescriptionType multimediaDescriptionType) {
        return multimediaDescriptionType != null && multimediaDescriptionType.getVideoItems() != null
                ? multimediaDescriptionType.getVideoItems().getVideoItems()
                : Collections.emptyList();
    }
}
