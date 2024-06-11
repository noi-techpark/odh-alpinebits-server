// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accommodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccommodationRoom;
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
     * @return An {@link Optional} wrapping the OTAHotelDescriptiveInfoRS result for the given hotelCode,
     * or an empty Optional if the given hotelCode was unknown.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    Optional<OTAHotelDescriptiveInfoRS> fetchInventoryBasic(String hotelCode);

    /**
     * Fetch Inventory HotelInfo {@link OTAHotelDescriptiveInfoRS} data from ODH for the given hotelCode.
     *
     * @param hotelCode Fetch the data for this hotel code.
     * @return An {@link Optional} wrapping the OTAHotelDescriptiveInfoRS result for the given hotelCode,
     * or an empty Optional if the given hotelCode was unknown.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    Optional<OTAHotelDescriptiveInfoRS> fetchInventoryHotelInfo(String hotelCode);

    /**
     * Fetch entries from <code>AccommodationRoom</code> endpoint for the given room ID.
     *
     * @param accoId Fetch entries for this accommodation ID.
     * @return An {@link Optional} wrapping the list of {@link AccommodationRoom} entries fetched from
     * the ODH Backend, or an empty Optional if the given accoId was unknown.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    Optional<List<AccommodationRoom>> fetchAccommodationRooms(String accoId);

    /**
     * Fetch data for the accommodation with the given <code>accoid</code> and return
     * it as {@link Accommodation}.
     *
     * @param accoId Fetch entries for this accommodation ID.
     * @return {@link Optional} wrapping the instance of {@link Accommodation} fetched from the backend,
     * or an empty Optional if the given accoId was unknown.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    Optional<Accommodation> fetchAccommodation(String accoId);

    /**
     * Push FreeRooms data to ODH.
     *
     * @param pushWrapper Contains the message and other useful information for the push.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    void pushFreeRooms(PushWrapper pushWrapper);

    /**
     * Push Inventory/Basic data to ODH.
     *
     * @param pushWrapper Contains the message and other useful information for the push.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    void pushInventoryBasic(PushWrapper pushWrapper);

    /**
     * Push Inventory/HotelInfo data to ODH.
     *
     * @param pushWrapper Contains the message and other useful information for the push.
     * @throws OdhBackendException On any exceptional circumstance in the ODH backend.
     */
    void pushInventoryHotelInfo(PushWrapper pushWrapper);
}
