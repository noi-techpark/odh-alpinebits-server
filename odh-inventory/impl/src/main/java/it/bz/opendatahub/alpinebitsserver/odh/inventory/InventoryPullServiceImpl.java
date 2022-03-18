/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accommodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccommodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.AffiliationInfoTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.ContactInfosTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.ErrorOTAHotelDescriptiveInfoRSBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.HotelCodeExtractor;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.HotelInfoTypeBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.common.InventoryPullMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        String hotelCode = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelDescriptiveInfoRQ);

        return this.service.fetchAccommodationRooms(hotelCode)
                .map(mapHotelBasic(hotelCode))
                .orElse(ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(hotelCode));
    }

    public OTAHotelDescriptiveInfoRS readHotelInfo(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        String hotelCode = HotelCodeExtractor.getHotelCodeOrThrowIfNotExistent(otaHotelDescriptiveInfoRQ);

        // Fetch room info
        return this.service.fetchAccommodationRooms(hotelCode)
                .map(mapHotelInfo(hotelCode))
                .orElse(ErrorOTAHotelDescriptiveInfoRSBuilder.noDataFound(hotelCode));
    }

    private Function<List<AccommodationRoom>, OTAHotelDescriptiveInfoRS> mapHotelBasic(String hotelCode) {
        return accommodationRooms -> {
            // Map to HotelDescriptiveContent
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullMapper.mapToHotelDescriptiveInfoForBasic(accommodationRooms);

            HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Optional<Accommodation> accommodationOptional = this.service.fetchAccommodation(hotelCode);

            accommodationOptional.ifPresent(accommodation ->
                    // Set hotel name
                    hotelDescriptiveContent.setHotelName(accommodation.getShortname())
            );

            otaHotelDescriptiveInfoRS.setSuccess(new SuccessType());
            otaHotelDescriptiveInfoRS.setVersion(BigDecimal.valueOf(8.000));

            return otaHotelDescriptiveInfoRS;
        };
    }

    private Function<List<AccommodationRoom>, OTAHotelDescriptiveInfoRS> mapHotelInfo(String hotelCode) {
        return accommodationRooms -> {
            // Map to HotelDescriptiveContent
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullMapper.mapToHotelDescriptiveInfoForHotelInfo(accommodationRooms);

            HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Optional<Accommodation> accommodationOptional = this.service.fetchAccommodation(hotelCode);

            accommodationOptional.ifPresent(accommodation -> {
                // Set hotel name
                hotelDescriptiveContent.setHotelName(accommodation.getShortname());

                // Add HotelInfo if appropriate
                HotelInfoTypeBuilder
                        .extractHotelInfoType(accommodation, this.withExtendedHotelInfoServiceCodes)
                        .ifPresent(hotelDescriptiveContent::setHotelInfo);

                // Add AffiliationInfo if appropriate
                AffiliationInfoTypeBuilder
                        .extractHotelInfoType(accommodation)
                        .ifPresent(hotelDescriptiveContent::setAffiliationInfo);

                // Add ContactInfos if appropriate
                ContactInfosTypeBuilder
                        .extractContactInfosType(accommodation)
                        .ifPresent(hotelDescriptiveContent::setContactInfos);
            });

            otaHotelDescriptiveInfoRS.setSuccess(new SuccessType());
            otaHotelDescriptiveInfoRS.setVersion(BigDecimal.valueOf(8.000));

            return otaHotelDescriptiveInfoRS;
        };
    }

}
