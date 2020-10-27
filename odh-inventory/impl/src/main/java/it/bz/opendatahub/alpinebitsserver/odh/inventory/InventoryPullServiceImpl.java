/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accommodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccommodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.AffiliationInfoTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.ContactInfosTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.HotelCodeExtractor;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.HotelInfoTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.InventoryPullMapper;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

/**
 * This service uses the ODH tourism data to provide a response to
 * an AlpineBits Inventory/Basic pull request.
 */
public class InventoryPullServiceImpl implements InventoryPullService {


    private final OdhBackendService service;
    private final boolean withExtendedHotelInfoServiceCodes;
    private final InventoryPullMapper inventoryPullMapper;

    public InventoryPullServiceImpl(OdhBackendService service, boolean withExtendedHotelInfoServiceCodes) {
        this.service = service;
        this.withExtendedHotelInfoServiceCodes = withExtendedHotelInfoServiceCodes;
        this.inventoryPullMapper = new InventoryPullMapper();
    }

    public OTAHotelDescriptiveInfoRS readBasic(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        try {
            String hotelCode = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelDescriptiveInfoRQ);

            List<AccommodationRoom> accommodationRooms = this.service.fetchAccommodationRooms(hotelCode);

            // Map to HotelDescriptiveContent
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullMapper.mapToHotelDescriptiveInfoForBasic(accommodationRooms);

            HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Accommodation accommodation = this.service.fetchAccommodation(hotelCode);

            // Set hotel name
            hotelDescriptiveContent.setHotelName(accommodation.getShortname());

            otaHotelDescriptiveInfoRS.setSuccess(new SuccessType());
            otaHotelDescriptiveInfoRS.setVersion(BigDecimal.valueOf(8.000));

            return otaHotelDescriptiveInfoRS;
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }
    }

    public OTAHotelDescriptiveInfoRS readHotelInfo(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        try {
            String hotelCode = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelDescriptiveInfoRQ);

            // Fetch room info
            List<AccommodationRoom> accommodationRooms = this.service.fetchAccommodationRooms(hotelCode);

            // Map to HotelDescriptiveContent
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullMapper.mapToHotelDescriptiveInfoForHotelInfo(accommodationRooms);

            HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Accommodation accommodation = this.service.fetchAccommodation(hotelCode);

            // Set hotel name
            hotelDescriptiveContent.setHotelName(accommodation.getShortname());

            // Add HotelInfo if appropriate
            HotelInfoTypeBuilder.extractHotelInfoType(accommodation, this.withExtendedHotelInfoServiceCodes).ifPresent(hotelDescriptiveContent::setHotelInfo);

            // Add AffiliationInfo if appropriate
            AffiliationInfoTypeBuilder.extractHotelInfoType(accommodation).ifPresent(hotelDescriptiveContent::setAffiliationInfo);

            // Add ContactInfos if appropriate
            ContactInfosTypeBuilder.extractContactInfosType(accommodation).ifPresent(hotelDescriptiveContent::setContactInfos);

            otaHotelDescriptiveInfoRS.setSuccess(new SuccessType());
            otaHotelDescriptiveInfoRS.setVersion(BigDecimal.valueOf(8.000));

            return otaHotelDescriptiveInfoRS;
        } catch (OdhBackendException e) {
            throw new AlpineBitsException("ODH backend client error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e);
        }
    }

}
