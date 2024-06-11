// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception;

/**
 * General backend exception.
 */
public class OdhBackendException extends RuntimeException {

    public OdhBackendException(String message) {
        super(message);
    }

    public OdhBackendException(String message, Throwable cause) {
        super(message, cause);
    }

}
