/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client;

/**
 * The AuthenticatedOdhClient adds to the methods provided by {@link OdhClient} methods for
 * authentication.
 */
public interface AuthenticatedOdhClient extends OdhClient {

    /**
     * Return if the client is authenticated.
     *
     * @return <code>true</code> if the client is authenticated, <code>false</code> otherwise.
     */
    boolean isAuthenticated();

}
