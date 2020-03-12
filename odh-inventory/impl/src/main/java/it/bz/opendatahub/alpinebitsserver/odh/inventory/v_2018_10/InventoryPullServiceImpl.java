/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2018_10;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.InventoryPullService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.ContactInfosTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.InventoryPullMapper;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This service uses the ODH tourism data to provide a response to
 * an AlpineBits Inventory/Basic pull request.
 */
public class InventoryPullServiceImpl implements InventoryPullService {


    private final OdhBackendService service;
    private final InventoryPullMapper inventoryPullMapper;

    public InventoryPullServiceImpl(OdhBackendService service) {
        this.service = service;
        this.inventoryPullMapper = new InventoryPullMapper();
    }

    public HotelDescriptiveInfoResponse readBasic(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest) {
        HotelDescriptiveInfoResponse response = new HotelDescriptiveInfoResponse();
        try {
            String hotelCode = hotelDescriptiveInfoRequest.getHotelCode();

            List<AccomodationRoom> accomodationRooms = this.service.fetchAccomodationRooms(hotelCode);

            // Map to HotelDescriptiveContent
            HotelDescriptiveContent hotelDescriptiveContent = inventoryPullMapper
                    .mapToHotelDescriptiveContentForBasic(accomodationRooms);

            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Accomodation accomodation = this.service.fetchAccomodation(hotelCode);

            // Set hotel name
            hotelDescriptiveContent.setHotelName(accomodation.getShortname());

            response.setHotelDescriptiveContent(hotelDescriptiveContent);
            response.setSuccess("");
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }

        return response;
    }

    public HotelDescriptiveInfoResponse readHotelInfo(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest) {
        HotelDescriptiveInfoResponse response = new HotelDescriptiveInfoResponse();
        try {
            String hotelCode = hotelDescriptiveInfoRequest.getHotelCode();

            // Fetch room info
            List<AccomodationRoom> accomodationRooms = this.service.fetchAccomodationRooms(hotelCode);

            // Map to HotelDescriptiveContent
            HotelDescriptiveContent hotelDescriptiveContent = inventoryPullMapper
                    .mapToHotelDescriptiveContentForHotelInfo(accomodationRooms);

            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Accomodation accomodation = this.service.fetchAccomodation(hotelCode);

            // Set hotel name
            hotelDescriptiveContent.setHotelName(accomodation.getShortname());

            // Add ContactInfos if appropriate
            ContactInfosTypeBuilder.extractContactInfosType(accomodation).ifPresent(hotelDescriptiveContent::setContactInfos);

            // Add some hotel info

            HotelInfoType.Position position = new HotelInfoType.Position();
            position.setAltitude(accomodation.getAltitude() != null ? accomodation.getAltitude().toString() : "");
            position.setLatitude(accomodation.getLatitude() != null ? accomodation.getLatitude().toString() : "");
            position.setLongitude(accomodation.getLongitude() != null ? accomodation.getLongitude().toString() : "");

            HotelInfoType hotelInfoType = new HotelInfoType();
            hotelInfoType.setPosition(position);

            hotelDescriptiveContent.setHotelInfo(hotelInfoType);

            response.setHotelDescriptiveContent(hotelDescriptiveContent);
            response.setSuccess("");
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }

        return response;
    }

}
