/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.impl;

import it.bz.opendatahub.alpinebits.mapping.entity.Error;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.InventoryPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service uses the ODH tourism data to store AlpineBits Inventory data.
 */
public class InventoryPushServiceImpl implements InventoryPushService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryPushService.class);

    private final OdhBackendService service;

    public InventoryPushServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    @Override
    public GenericResponse writeBasic(PushWrapper pushWrapper) {
        try {
            service.pushInventoryBasic(pushWrapper);
            return GenericResponse.success();
        } catch (OdhBackendException e) {
            LOG.error("ODH backend client error", e);
            String errorMessage = this.buildErrorMessage(e.getMessage(), pushWrapper.getRequestId());
            return GenericResponse.error(
                    Error.withDefaultType(Error.CODE_UNABLE_TO_PROCESS, errorMessage)
            );
        }
    }

    @Override
    public GenericResponse writeHotelInfo(PushWrapper pushWrapper) {
        try {
            service.pushInventoryHotelInfo(pushWrapper);
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
