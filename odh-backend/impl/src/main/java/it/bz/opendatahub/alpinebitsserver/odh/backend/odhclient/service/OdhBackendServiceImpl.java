package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service;

import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.OdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements {@link OdhBackendService}.
 */
public class OdhBackendServiceImpl implements OdhBackendService {

    public static final String DEFAULT_ODH_BASE_URL = "https://tourism.opendatahub.bz.it";

    private final OdhClient odhClient;

    public OdhBackendServiceImpl(OdhClient odhClient) {
        this.odhClient = odhClient;
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
            throw new OdhBackendException("ODH client encountered a problem", e);
        }
    }

    @Override
    public Accomodation fetchAccomodation(String accoId) throws OdhBackendException {
        String path = "api/Accommodation/" + accoId;

        try {
            return odhClient.fetch(path, HttpMethod.GET, null, null, Accomodation.class);
        } catch (ProcessingException | WebApplicationException e) {
            throw new OdhBackendException("ODH client encountered a problem", e);
        }
    }
}
