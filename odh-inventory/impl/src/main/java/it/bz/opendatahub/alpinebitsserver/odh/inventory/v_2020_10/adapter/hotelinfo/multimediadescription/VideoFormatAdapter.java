// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo.multimediadescription;

import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoDescriptionType.VideoFormat;

/**
 * This adapter is used to remove elements from {@link VideoFormat} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class VideoFormatAdapter {

    private VideoFormatAdapter() {
        // Empty
    }

    public static void removeUnsupported(VideoFormat videoFormat) {
        if (videoFormat == null) {
            return;
        }

        videoFormat.setAuthor(null);
        videoFormat.setBitRate(null);
        videoFormat.setContentID(null);
        videoFormat.setCopyrightEnd(null);
        videoFormat.setCopyrightOwner(null);
        videoFormat.setCopyrightStart(null);
        videoFormat.setEffectiveEnd(null);
        videoFormat.setEffectiveStart(null);
        videoFormat.setFileName(null);
        videoFormat.setFileSize(null);
        videoFormat.setFormat(null);
        videoFormat.setHeight(null);
        videoFormat.setID(null);
        videoFormat.setLanguage(null);
        videoFormat.setLength(null);
        videoFormat.setRecordID(null);
        videoFormat.setStreamingSource(null);
        videoFormat.setTitle(null);
        videoFormat.setUnitOfMeasureCode(null);
        videoFormat.setWidth(null);
    }

}
