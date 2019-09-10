/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.freerooms.impl;

import it.bz.opendatahub.alpinebits.mapping.entity.Error;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.freerooms.FreeRoomsPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service uses the ODH tourism data to store AlpineBits FreeRooms data.
 */
public class FreeRoomsPushServiceImpl implements FreeRoomsPushService {

    private static final Logger LOG = LoggerFactory.getLogger(FreeRoomsPushServiceImpl.class);

    private final OdhBackendService service;

    public FreeRoomsPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public GenericResponse write(PushWrapper pushWrapper) {
        try {
            service.pushFreeRooms(pushWrapper);
            return GenericResponse.success();
        } catch (OdhBackendException e) {
            LOG.error("ODH backend client error", e);
            String errorMessage = this.buildErrorMessage(e.getMessage(), pushWrapper.getRequestId());
            return GenericResponse.error(
                    Error.withDefaultType(Error.CODE_UNABLE_TO_PROCESS, errorMessage)
            );
        }
    }

    private String buildErrorMessage(String message, String requestId) {
        return message + " (rid = " + requestId + ")";
    }

}
