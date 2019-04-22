/*
 * This source Code Form is subject to the terms of the Mozilla Public
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
public class Accomodation {

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

    @JsonProperty("Features")
    private List<Feature> features;

    @JsonProperty("AccoDetail")
    private Map<String, AccoDetail> accoDetailMap;

    @JsonProperty("ImageGallery")
    private List<ImageGalleryEntry> imageGalleries;

    @JsonIgnore
    private List<AccomodationRoom> accomodationRooms;

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

    public List<AccomodationRoom> getAccomodationRooms() {
        return accomodationRooms;
    }

    public void setAccomodationRooms(List<AccomodationRoom> accomodationRooms) {
        this.accomodationRooms = accomodationRooms;
    }

    @Override
    public String toString() {
        return "Accomodation{" +
                "id='" + id + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", features=" + features +
                ", accoDetailMap=" + accoDetailMap +
                ", imageGalleries=" + imageGalleries +
                ", accomodationRooms=" + accomodationRooms +
                '}';
    }
}
