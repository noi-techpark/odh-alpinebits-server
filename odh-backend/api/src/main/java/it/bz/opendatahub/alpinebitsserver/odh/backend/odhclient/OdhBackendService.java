package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient;

import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import java.util.List;

/**
 * This service provides methods to access the ODH backend.
 */
public interface OdhBackendService {

    /**
     * Fetch entries from <code>AccommodationRoom</code> endpoint for the given room ID.
     *
     * @param accoId fetch entries for this accommodation ID
     * @return a list of {@link AccomodationRoom} entries fetched from the ODH Backend
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    List<AccomodationRoom> fetchAccomodationRooms(String accoId) throws OdhBackendException;

    /**
     * Fetch data for the accommodation with the given <code>accoid</code> and return
     * it as {@link Accomodation}.
     *
     * @param accoId fetch entries for this accommodation ID
     * @return an instance of {@link Accomodation} fetched from the backend
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    Accomodation fetchAccomodation(String accoId) throws OdhBackendException;

}
