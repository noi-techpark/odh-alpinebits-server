/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * DTO for Open Data Hub "AccommodationRoom" element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccommodationRoom {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Roommax")
    private Integer roommax;

    @JsonProperty("Roommin")
    private Integer roommin;

    @JsonProperty("Roomstd")
    private Integer roomstd;

    @JsonProperty("RoomCode")
    private String roomcode;

    @JsonProperty("Roomtype")
    private String roomtype;

    @JsonProperty("RoomQuantity")
    private Integer roomQuantity;

    @JsonProperty("RoomClassificationCodes")
    private Integer roomClassificationCode;

    @JsonProperty("RoomNumbers")
    private List<String> roomNumbers;

    @JsonProperty("Features")
    private List<Feature> features;

    @JsonProperty("AccoRoomDetail")
    private Map<String, AccoRoomDetail> accoRoomDetailMap;

    @JsonProperty("ImageGallery")
    private List<ImageGalleryEntry> imageGalleryEntries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getRoommax() {
        return roommax;
    }

    public void setRoommax(Integer roommax) {
        this.roommax = roommax;
    }

    public Integer getRoommin() {
        return roommin;
    }

    public void setRoommin(Integer roommin) {
        this.roommin = roommin;
    }

    public Integer getRoomstd() {
        return roomstd;
    }

    public void setRoomstd(Integer roomstd) {
        this.roomstd = roomstd;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public Integer getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(Integer roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public Integer getRoomClassificationCode() {
        return roomClassificationCode;
    }

    public void setRoomClassificationCode(Integer roomClassificationCode) {
        this.roomClassificationCode = roomClassificationCode;
    }

    public List<String> getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(List<String> roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Map<String, AccoRoomDetail> getAccoRoomDetailMap() {
        return accoRoomDetailMap;
    }

    public void setAccoRoomDetailMap(Map<String, AccoRoomDetail> accoRoomDetailMap) {
        this.accoRoomDetailMap = accoRoomDetailMap;
    }

    public List<ImageGalleryEntry> getImageGalleryEntries() {
        return imageGalleryEntries;
    }

    public void setImageGalleryEntries(List<ImageGalleryEntry> imageGalleryEntries) {
        this.imageGalleryEntries = imageGalleryEntries;
    }

    @Override
    public String toString() {
        return "AccommodationRoom{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", roommax=" + roommax +
                ", roommin=" + roommin +
                ", roomstd=" + roomstd +
                ", roomcode='" + roomcode + '\'' +
                ", roomtype='" + roomtype + '\'' +
                ", roomQuantity=" + roomQuantity +
                ", roomClassificationCode=" + roomClassificationCode +
                ", roomNumbers=" + roomNumbers +
                ", features=" + features +
                ", accoRoomDetailMap=" + accoRoomDetailMap +
                ", imageGalleryEntries=" + imageGalleryEntries +
                '}';
    }
}
