// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class wraps a message, that is read from ODH.
 *
 * @param <T> Type of the resulting message.
 */
public class PullWrapper<T> {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("AlpineBitsVersion")
    private String alpineBitsVersion;

    @JsonProperty("AccommodationId")
    private String accommodationId;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("Message")
    private T message;

    @JsonProperty("MessageType")
    private String messageType;

    @JsonProperty("RequestDate")
    private String requestDate;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAlpineBitsVersion() {
        return alpineBitsVersion;
    }

    public void setAlpineBitsVersion(String alpineBitsVersion) {
        this.alpineBitsVersion = alpineBitsVersion;
    }

    public String getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "PullWrapper{" +
                "source='" + source + '\'' +
                ", alpineBitsVersion='" + alpineBitsVersion + '\'' +
                ", accommodationId='" + accommodationId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", message=" + message +
                ", messageType='" + messageType + '\'' +
                ", requestDate='" + requestDate + '\'' +
                '}';
    }
}
