/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;

/**
 * A service to handle an AlpineBits FreeRooms requests.
 * <p>
 * Note that AlpineBits FreeRooms supports push requests only.
 */
public interface FreeRoomsPushService {

    /**
     * Write a FreeRooms information.
     *
     * @param pushWrapper this element contains the FreeRooms information
     *                    that should be written to ODH
     * @return a {@link GenericResponse} containing the response
     */
    GenericResponse write(PushWrapper pushWrapper);

}
