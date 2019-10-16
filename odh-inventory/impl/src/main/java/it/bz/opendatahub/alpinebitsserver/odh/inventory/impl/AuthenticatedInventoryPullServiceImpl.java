/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.impl;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This service uses the ODH tourism data to provide a response to
 * an AlpineBits Inventory/Basic pull request with valid authentication.
 */
public class AuthenticatedInventoryPullServiceImpl {

    private final OdhBackendService service;

    public AuthenticatedInventoryPullServiceImpl(OdhBackendService service) {
        this.service = service;
    }

    public OTAHotelDescriptiveInfoRS readBasic(String hotelCode) {
        try {
            List<OTAHotelDescriptiveInfoRS> result = this.service.fetchInventoryBasic(hotelCode);

            OTAHotelDescriptiveInfoRS response = result.isEmpty()
                    ? this.getMinimumOTAHotelDescriptiveInfoRS(hotelCode)
                    : result.get(0);

            response.setSuccess("");
            return response;
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }
    }

    public OTAHotelDescriptiveInfoRS readHotelInfo(String hotelCode) {
        try {
            List<OTAHotelDescriptiveInfoRS> result = this.service.fetchInventoryHotelInfo(hotelCode);

            OTAHotelDescriptiveInfoRS response = result.isEmpty()
                    ? this.getMinimumOTAHotelDescriptiveInfoRS(hotelCode)
                    : result.get(0);

            response.setSuccess("");
            return response;
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }
    }

    private OTAHotelDescriptiveInfoRS getMinimumOTAHotelDescriptiveInfoRS(String hotelCode) {
        OTAHotelDescriptiveInfoRS ota = new OTAHotelDescriptiveInfoRS();
        ota.setVersion("8.000");

        HotelDescriptiveContents hotelDescriptiveContents = new HotelDescriptiveContents();
        ota.setHotelDescriptiveContents(hotelDescriptiveContents);

        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setHotelCode(hotelCode);
        hotelDescriptiveContents.setHotelDescriptiveContent(hotelDescriptiveContent);

        return ota;
    }
}
