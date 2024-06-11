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
 * This class wraps a message, that should be written to ODH,
 * and adds useful information.
 */
public class PushWrapper {

    @JsonProperty("AlpineBitsVersion")
    private String alpineBitsVersion;

    @JsonProperty("AccommodationId")
    private String accommodationId;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("Message")
    private Object message;

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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PushWrapper{" +
                "alpineBitsVersion='" + alpineBitsVersion + '\'' +
                ", accommodationId='" + accommodationId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", message=" + message +
                '}';
    }
}
