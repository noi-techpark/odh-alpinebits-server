// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageDescriptionType.ImageFormat;

/**
 * This adapter is used to remove elements from {@link ImageFormat} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class ImageFormatAdapter {

    private ImageFormatAdapter() {
        // Empty
    }

    public static void removeUnsupported(ImageFormat imageFormat) {
        if (imageFormat == null) {
            return;
        }

        imageFormat.setAuthor(null);
        imageFormat.setAuthorContact(null);
        imageFormat.setColorSpace(null);
        imageFormat.setContentID(null);
        imageFormat.setCopyrightEnd(null);
        imageFormat.setCopyrightOwner(null);
        imageFormat.setCopyrightStart(null);
        imageFormat.setDimensionCategory(null);
        imageFormat.setEffectiveEnd(null);
        imageFormat.setEffectiveStart(null);
        imageFormat.setFileName(null);
        imageFormat.setFileSize(null);
        imageFormat.setFormat(null);
        imageFormat.setHeight(null);
        imageFormat.setIsOriginalIndicator(null);
        imageFormat.setLanguage(null);
        imageFormat.setLatitude(null);
        imageFormat.setLongitude(null);
        imageFormat.setOriginalFileName(null);
        imageFormat.setRecordID(null);
        imageFormat.setResolution(null);
        imageFormat.setSort(null);
        imageFormat.setUnitOfMeasureCode(null);
        imageFormat.setWidth(null);
    }

}
