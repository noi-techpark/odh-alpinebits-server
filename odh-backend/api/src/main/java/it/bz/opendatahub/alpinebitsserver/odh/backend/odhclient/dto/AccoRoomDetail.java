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

/**
 * DTO for Open Data Hub "AccommodationRoom" AccoRoomDetail element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccoRoomDetail {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Longdesc")
    private String longdesc;

    @JsonProperty("Shortdesc")
    private String shortdesc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    @Override
    public String toString() {
        return "AccoRoomDetail{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", longdesc='" + longdesc + '\'' +
                ", shortdesc='" + shortdesc + '\'' +
                '}';
    }
}
