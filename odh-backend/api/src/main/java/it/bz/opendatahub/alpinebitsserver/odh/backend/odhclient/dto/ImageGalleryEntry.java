// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * DTO for Open Data Hub "AccommodationRoom" ImageGallery element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageGalleryEntry {

    @JsonProperty("ImageUrl")
    private String imageUrl;

    @JsonProperty("CopyRight")
    private String copyRight;

    @JsonProperty("License")
    private String license;

    @JsonProperty("ImageDesc")
    private Map<String, String> imageDescriptions;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Map<String, String> getImageDescriptions() {
        return imageDescriptions;
    }

    public void setImageDescriptions(Map<String, String> imageDescriptions) {
        this.imageDescriptions = imageDescriptions;
    }

    @Override
    public String toString() {
        return "ImageGalleryEntry{" +
                "imageUrl='" + imageUrl + '\'' +
                ", copyRight='" + copyRight + '\'' +
                ", license='" + license + '\'' +
                ", imageDescriptions=" + imageDescriptions +
                '}';
    }
}
