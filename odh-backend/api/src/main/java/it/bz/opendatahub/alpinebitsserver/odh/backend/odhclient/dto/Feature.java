// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DTO for Open Data Hub "AccommodationRoom" Feature element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("HgvId")
    private String hgvId;

    @JsonProperty("OtaCodes")
    private String otaCodes;

    @JsonProperty("RoomAmenityCodes")
    private List<Integer> roomAmenityCodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHgvId() {
        return hgvId;
    }

    public void setHgvId(String hgvId) {
        this.hgvId = hgvId;
    }

    public String getOtaCodes() {
        return otaCodes;
    }

    public void setOtaCodes(String otaCodes) {
        this.otaCodes = otaCodes;
    }

    public List<Integer> getRoomAmenityCodes() {
        return roomAmenityCodes;
    }

    public void setRoomAmenityCodes(List<Integer> roomAmenityCodes) {
        this.roomAmenityCodes = roomAmenityCodes;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hgvId='" + hgvId + '\'' +
                ", otaCodes='" + otaCodes + '\'' +
                ", roomAmenityCodes=" + roomAmenityCodes +
                '}';
    }
}
