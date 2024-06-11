// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageDescriptionType.ImageFormat;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link ImageItem} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class ImageItemAdapter {

    private ImageItemAdapter() {
        // Empty
    }

    public static void removeUnsupported(ImageItem imageItem) {
        if (imageItem == null) {
            return;
        }

        imageItem.setCreateDateTime(null);
        imageItem.setCreatorID(null);
        imageItem.setID(null);
        imageItem.setLastModifierID(null);
        imageItem.setLastModifyDateTime(null);
        imageItem.setPurgeDate(null);
        imageItem.setRemoval(null);
        imageItem.setTPAExtensions(null);
        imageItem.setVersion(null);

        extractImageFormats(imageItem).forEach(ImageFormatAdapter::removeUnsupported);

        extractImageDescriptions(imageItem).forEach(description -> {
            description.setCaption(null);
            description.setFormatted(null);
        });
    }

    private static List<ImageFormat> extractImageFormats(ImageItemsType.ImageItem imageItem) {
        return imageItem != null && imageItem.getImageFormats() != null
                ? imageItem.getImageFormats()
                : Collections.emptyList();
    }

    private static List<ImageDescriptionType.Description> extractImageDescriptions(ImageItemsType.ImageItem imageItem) {
        return imageItem != null ? imageItem.getDescriptions() : Collections.emptyList();
    }
}
