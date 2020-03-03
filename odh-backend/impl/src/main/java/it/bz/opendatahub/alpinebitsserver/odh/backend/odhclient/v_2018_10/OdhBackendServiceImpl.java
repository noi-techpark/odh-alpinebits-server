/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10;

import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.AuthenticatedOdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PullWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements {@link OdhBackendService}.
 */
public class OdhBackendServiceImpl implements OdhBackendService {

    private static final String ODH_ERROR_MESSAGE = "ODH client encountered a problem";

    private final AuthenticatedOdhClient odhClient;

    public OdhBackendServiceImpl(AuthenticatedOdhClient odhClient) {
        this.odhClient = odhClient;
    }

    @Override
    public boolean isAuthenticated() {
        return this.odhClient.isAuthenticated();
    }

    @Override
    public List<OTAHotelDescriptiveInfoRS> fetchInventoryBasic(String hotelCode) throws OdhBackendException {
        String path = "api/AlpineBits/InventoryBasic";

        return this.fetchOTAHotelDescriptiveInfoRS(path, hotelCode);
    }

    @Override
    public List<OTAHotelDescriptiveInfoRS> fetchInventoryHotelInfo(String hotelCode) throws OdhBackendException {
        String path = "api/AlpineBits/InventoryHotelInfo";

        return this.fetchOTAHotelDescriptiveInfoRS(path, hotelCode);
    }

    @Override
    public List<AccomodationRoom> fetchAccomodationRooms(String accoId) throws OdhBackendException {
        String path = "api/AccommodationRoom";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("accoid", accoId);
        try {
            return odhClient.fetch(path, HttpMethod.GET, queryParams, null, new GenericType<List<AccomodationRoom>>() {
            });
        } catch (ProcessingException | WebApplicationException e) {
            throw new OdhBackendException(ODH_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Accomodation fetchAccomodation(String accoId) throws OdhBackendException {
        String path = "api/Accommodation/" + accoId;

        try {
            return odhClient.fetch(path, HttpMethod.GET, null, null, Accomodation.class);
        } catch (ProcessingException | WebApplicationException e) {
            throw new OdhBackendException(ODH_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void pushFreeRooms(PushWrapper pushWrapper) throws OdhBackendException {
        this.push("api/AlpineBits/FreeRooms", pushWrapper);
    }

    @Override
    public void pushInventoryBasic(PushWrapper pushWrapper) throws OdhBackendException {
        this.push("api/AlpineBits/InventoryBasic", pushWrapper);
    }

    @Override
    public void pushInventoryHotelInfo(PushWrapper pushWrapper) throws OdhBackendException {
        this.push("api/AlpineBits/InventoryHotelInfo", pushWrapper);
    }

    private void push(String path, PushWrapper pushWrapper) throws OdhBackendException {
        try {
            odhClient.fetch(path, HttpMethod.POST, null, Entity.json(pushWrapper), String.class);
        } catch (ProcessingException | WebApplicationException e) {
            throw new OdhBackendException(ODH_ERROR_MESSAGE, e);
        }
    }

    private List<OTAHotelDescriptiveInfoRS> fetchOTAHotelDescriptiveInfoRS(String path, String hotelCode) throws OdhBackendException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("accoid", hotelCode);
        try {
            List<PullWrapper<OTAHotelDescriptiveInfoRS>> results = odhClient.fetch(
                    path,
                    HttpMethod.GET,
                    queryParams,
                    null,
                    new GenericType<List<PullWrapper<OTAHotelDescriptiveInfoRS>>>() {
                    }
            );

            if (results.isEmpty()) {
                return Collections.emptyList();
            }

            return results.stream()
                    .map(PullWrapper::getMessage)
                    .collect(Collectors.toList());
        } catch (ProcessingException | WebApplicationException e) {
            throw new OdhBackendException(ODH_ERROR_MESSAGE, e);
        }
    }
}
