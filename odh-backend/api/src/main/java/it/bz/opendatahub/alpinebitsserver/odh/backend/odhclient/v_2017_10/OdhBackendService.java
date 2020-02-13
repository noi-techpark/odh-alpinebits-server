/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10;

import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.PushWrapper;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import java.util.List;
import java.util.Optional;

/**
 * This service provides methods to access the ODH backend.
 */
public interface OdhBackendService {

    /**
     * Return if the current instance of the service is authorized against ODH.
     * <p>
     * Based on the result, e.g. different endpoints may be called.
     *
     * @return <code>true</code> if the service is authorized against the ODH,
     * <code>false</code> otherwise.
     */
    boolean isAuthenticated();

    /**
     * Fetch Inventory Basic {@link OTAHotelDescriptiveInfoRS} data from ODH for the given hotelCode.
     *
     * @param hotelCode Fetch the data for this hotel code.
     * @return An {@link Optional} containing the list of OTAHotelDescriptiveInfoRS elements
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    List<OTAHotelDescriptiveInfoRS> fetchInventoryBasic(String hotelCode) throws OdhBackendException;

    /**
     * Fetch Inventory HotelInfo {@link OTAHotelDescriptiveInfoRS} data from ODH for the given hotelCode.
     *
     * @param hotelCode Fetch the data for this hotel code.
     * @return An {@link Optional} containing the list of OTAHotelDescriptiveInfoRS elements
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    List<OTAHotelDescriptiveInfoRS> fetchInventoryHotelInfo(String hotelCode) throws OdhBackendException;

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

    /**
     * Push FreeRooms data to ODH.
     *
     * @param pushWrapper contains the message and other useful information for the push
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    void pushFreeRooms(PushWrapper pushWrapper) throws OdhBackendException;

    /**
     * Push Inventory/Basic data to ODH.
     *
     * @param pushWrapper contains the message and other useful information for the push
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    void pushInventoryBasic(PushWrapper pushWrapper) throws OdhBackendException;

    /**
     * Push Inventory/HotelInfo data to ODH.
     *
     * @param pushWrapper contains the message and other useful information for the push
     * @throws OdhBackendException any exception is caught and wrapped inside an OdhBackendException
     */
    void pushInventoryHotelInfo(PushWrapper pushWrapper) throws OdhBackendException;
}
