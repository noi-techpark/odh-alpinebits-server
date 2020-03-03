/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient;

import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.OdhBackendService;

/**
 * This class contains key definitions for ODH.
 */
public final class OdhBackendContextKey {

    /**
     * Context key for ODH backend service for AlpineBits 2017-10.
     */
    public static final Key<OdhBackendService> ODH_BACKEND_SERVICE_2017_10 = Key.key(
            "odhBackendService_2017_10", OdhBackendService.class
    );

    /**
     * Context key for ODH backend service for AlpineBits 2018-10.
     */
    public static final Key<it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService>
            ODH_BACKEND_SERVICE_2018_10 = Key.key(
            "odhBackendService_2018_10",
            it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService.class
    );

    private OdhBackendContextKey() {
        // Empty
    }

}
