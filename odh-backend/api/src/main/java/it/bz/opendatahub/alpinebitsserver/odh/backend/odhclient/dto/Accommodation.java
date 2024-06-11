// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * DTO for Open Data Hub "Accommodation" element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Accommodation {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Latitude")
    private Double latitude;

    @JsonProperty("Longitude")
    private Double longitude;

    @JsonProperty("Altitude")
    private Double altitude;

    @JsonProperty("Shortname")
    private String shortname;

    @JsonProperty("TrustYouID")
    private String trustYouId;

    @JsonProperty("TrustYouActive")
    private Boolean trustYouActive;

    @JsonProperty("TrustYouState")
    private Integer trustYouState;

    @JsonProperty("TrustYouScore")
    private Float trustYouScore;

    @JsonProperty("AccoTypeId")
    private String accoTypeId;

    @JsonProperty("AccoCategoryId")
    private String accoCategoryId;

    @JsonProperty("Features")
    private List<Feature> features;

    @JsonProperty("AccoDetail")
    private Map<String, AccoDetail> accoDetailMap;

    @JsonProperty("ImageGallery")
    private List<ImageGalleryEntry> imageGalleries;

    @JsonIgnore
    private List<AccommodationRoom> accommodationRooms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getTrustYouId() {
        return trustYouId;
    }

    public void setTrustYouId(String trustYouId) {
        this.trustYouId = trustYouId;
    }

    public Boolean getTrustYouActive() {
        return trustYouActive;
    }

    public void setTrustYouActive(Boolean trustYouActive) {
        this.trustYouActive = trustYouActive;
    }

    public Integer getTrustYouState() {
        return trustYouState;
    }

    public void setTrustYouState(Integer trustYouState) {
        this.trustYouState = trustYouState;
    }

    public Float getTrustYouScore() {
        return trustYouScore;
    }

    public void setTrustYouScore(Float trustYouScore) {
        this.trustYouScore = trustYouScore;
    }

    public String getAccoTypeId() {
        return accoTypeId;
    }

    public void setAccoTypeId(String accoTypeId) {
        this.accoTypeId = accoTypeId;
    }

    public String getAccoCategoryId() {
        return accoCategoryId;
    }

    public void setAccoCategoryId(String accoCategoryId) {
        this.accoCategoryId = accoCategoryId;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Map<String, AccoDetail> getAccoDetailMap() {
        return accoDetailMap;
    }

    public void setAccoDetailMap(Map<String, AccoDetail> accoDetailMap) {
        this.accoDetailMap = accoDetailMap;
    }

    public List<ImageGalleryEntry> getImageGalleries() {
        return imageGalleries;
    }

    public void setImageGalleries(List<ImageGalleryEntry> imageGalleries) {
        this.imageGalleries = imageGalleries;
    }

    public List<AccommodationRoom> getAccommodationRooms() {
        return accommodationRooms;
    }

    public void setAccommodationRooms(List<AccommodationRoom> accommodationRooms) {
        this.accommodationRooms = accommodationRooms;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", shortname='" + shortname + '\'' +
                ", trustYouId='" + trustYouId + '\'' +
                ", trustYouActive=" + trustYouActive +
                ", trustYouState=" + trustYouState +
                ", trustYouScore=" + trustYouScore +
                ", accoTypeId=" + accoTypeId +
                ", accoCategoryId=" + accoCategoryId +
                ", features=" + features +
                ", accoDetailMap=" + accoDetailMap +
                ", imageGalleries=" + imageGalleries +
                ", accommodationRooms=" + accommodationRooms +
                '}';
    }
}
