/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.auth;

/**
 * An instance of this class provides methods for authentication.
 *
 * @param <T> The type of the authentication result.
 */
public interface AuthProvider<T> {

    /**
     * Perform authentication and return the authentication result, e.g. an access token.
     *
     * @return The authentication result on successful authentication. An {@link AuthenticationException}
     * should be thrown otherwise.
     * @throws AuthenticationException When there was an error during authentication.
     */
    T authenticate();

}
