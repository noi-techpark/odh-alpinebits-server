/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Open Data Hub "AccommodationRoom" ImageGallery element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageGalleryEntry {

    @JsonProperty("ImageUrl")
    private String imageUrl;

    @JsonProperty("CopyRight")
    private String copyRight;

    @JsonProperty("Descriptions")
    private Object descriptions;

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

    public Object getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Object descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "ImageGalleryEntry{" +
                "imageUrl='" + imageUrl + '\'' +
                ", copyRight='" + copyRight + '\'' +
                ", descriptions=" + descriptions +
                '}';
    }
}
