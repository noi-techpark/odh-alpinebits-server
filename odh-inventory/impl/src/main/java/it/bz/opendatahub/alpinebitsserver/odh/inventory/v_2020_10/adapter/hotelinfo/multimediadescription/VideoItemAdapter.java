// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoDescriptionType.VideoFormat;
import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoItemsType.VideoItem;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link VideoItem} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class VideoItemAdapter {

    private VideoItemAdapter() {
        // Empty
    }

    public static void removeUnsupported(VideoItem videoItem) {
        if (videoItem == null) {
            return;
        }

        videoItem.setCaption(null);
        videoItem.setCreateDateTime(null);
        videoItem.setCreatorID(null);
        videoItem.setLanguage(null);
        videoItem.setLastModifierID(null);
        videoItem.setLastModifyDateTime(null);
        videoItem.setPurgeDate(null);
        videoItem.setRemoval(null);
        videoItem.setVersion(null);

        extractVideoFormats(videoItem).forEach(VideoFormatAdapter::removeUnsupported);

        extractVideoDescriptions(videoItem).forEach(description -> {
            description.setCaption(null);
            description.setFormatted(null);
        });
    }

    private static List<VideoFormat> extractVideoFormats(VideoItem videoItem) {
        return videoItem != null && videoItem.getVideoFormats() != null
                ? videoItem.getVideoFormats()
                : Collections.emptyList();
    }

    private static List<VideoDescriptionType.Description> extractVideoDescriptions(VideoItem videoItem) {
        return videoItem != null ? videoItem.getDescriptions() : Collections.emptyList();
    }
}
