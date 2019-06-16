/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.impl;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.ImageItem;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TextItemDescription;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TypeRoom;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service uses the ODH tourism data to provide a response to
 * an AlpineBits Inventory/Basic pull request.
 */
public class OdhInventoryPullService {

    private final OdhBackendService service;

    public OdhInventoryPullService(OdhBackendService service) {
        this.service = service;
    }

    public HotelDescriptiveInfoResponse readBasic(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest) {
        HotelDescriptiveInfoResponse response = new HotelDescriptiveInfoResponse();
        try {
            String hotelCode = hotelDescriptiveInfoRequest.getHotelCode();

            List<AccomodationRoom> accomodationRooms;
            accomodationRooms = this.service.fetchAccomodationRooms(hotelCode);

            HotelDescriptiveContent hotelDescriptiveContent = this.buildHotelDescriptiveContentForBasic(accomodationRooms);
            hotelDescriptiveContent.setHotelCode(hotelCode);

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

            HotelDescriptiveContent hotelDescriptiveContent = this.buildHotelDescriptiveContentForHotelInfo(accomodationRooms);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Fetch accommodation info
            Accomodation accomodation = this.service.fetchAccomodation(hotelCode);

            // Add some contact infos

            ContactInfosType contactInfosType = new ContactInfosType();
            ContactInfoRootType contactInfoType = new ContactInfoRootType();

            ContactInfoType.CompanyName companyName = new ContactInfoType.CompanyName();
            companyName.setValue(accomodation.getShortname());
            contactInfoType.setCompanyName(companyName);
            contactInfosType.getContactInfos().add(contactInfoType);

            hotelDescriptiveContent.setContactInfos(contactInfosType);

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

    private HotelDescriptiveContent buildHotelDescriptiveContentForBasic(List<AccomodationRoom> rooms) {
        List<GuestRoom> guestRooms = rooms
                .stream()
                .map(room -> {
                    GuestRoom guestRoom = new GuestRoom();
                    guestRoom.setCode(room.getRoomcode());
                    guestRoom.setMinOccupancy(room.getRoommin());
                    guestRoom.setMaxOccupancy(room.getRoommax());
                    guestRoom.setTypeRoom(this.buildTypeRoom(room));
                    guestRoom.setLongNames(this.buildLongname(room));
                    guestRoom.setDescriptions(this.buildDescriptions(room));
                    guestRoom.setPictures(this.buildPictures(room));

                    if ("hgv".equalsIgnoreCase(room.getSource())) {
                        guestRoom.setRoomAmenityCodes(this.buildRoomAmenityCodes(room));
                    }


                    List<GuestRoom> resultingGuestRooms = new ArrayList<>();
                    resultingGuestRooms.add(guestRoom);

                    for (int i = 1; i < room.getRoomQuantity(); i++) {
                        GuestRoom sameGuestRoom = new GuestRoom();
                        sameGuestRoom.setCode(room.getRoomcode());
                        TypeRoom typeRoom = new TypeRoom();
                        // TODO: check what RoomID should be set for same rooms
                        typeRoom.setRoomId(guestRoom.getCode() + "-" + i);
                        sameGuestRoom.setTypeRoom(typeRoom);
                        resultingGuestRooms.add(sameGuestRoom);
                    }
                    return resultingGuestRooms;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setGuestRooms(guestRooms);

        return hotelDescriptiveContent;
    }

    private HotelDescriptiveContent buildHotelDescriptiveContentForHotelInfo(List<AccomodationRoom> rooms) {
        List<GuestRoom> guestRooms = rooms
                .stream()
                .map(room -> {
                    GuestRoom guestRoom = new GuestRoom();
                    guestRoom.setCode(room.getRoomcode());
                    guestRoom.setPictures(this.buildPictures(room));
                    return guestRoom;
                })
                .collect(Collectors.toList());

        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setGuestRooms(guestRooms);

        return hotelDescriptiveContent;
    }


    private TypeRoom buildTypeRoom(AccomodationRoom room) {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(room.getRoomstd());

        // Use ODH RoomClassificationCode if set,
        // otherwise use default value
        if (room.getRoomClassificationCode() != null) {
            typeRoom.setRoomClassificationCode(room.getRoomClassificationCode());
        } else {
            typeRoom.setRoomClassificationCode(42);
        }

        return typeRoom;
    }

    private List<Integer> buildRoomAmenityCodes(AccomodationRoom room) {
        return room.getFeatures()
                .stream()
                .map(feature -> {
                    if ("hgv".equalsIgnoreCase(room.getSource())) {
                        return Integer.parseInt(feature.getId());
                    }
                    return -100;
                })
                .collect(Collectors.toList());
    }

    private List<TextItemDescription> buildLongname(AccomodationRoom room) {
        return room.getAccoRoomDetailMap().values()
                .stream()
                .map(value -> {
                    TextItemDescription textItemDescription = new TextItemDescription();
                    textItemDescription.setLanguage(value.getLanguage());
                    textItemDescription.setTextFormat("PlainText");
                    textItemDescription.setValue(value.getName());
                    return textItemDescription;
                })
                .collect(Collectors.toList());
    }

    private List<TextItemDescription> buildDescriptions(AccomodationRoom room) {
        return room.getAccoRoomDetailMap().values()
                .stream()
                .map(value -> {
                    TextItemDescription textItemDescription = new TextItemDescription();
                    textItemDescription.setLanguage(value.getLanguage());
                    textItemDescription.setTextFormat("PlainText");
                    textItemDescription.setValue(value.getLongdesc());
                    return textItemDescription;
                })
                .collect(Collectors.toList());
    }

    private List<ImageItem> buildPictures(AccomodationRoom room) {
        return room.getImageGalleryEntries()
                .stream()
                .map(imageGallery -> {
                    ImageItem imageItem = new ImageItem();

                    // See OTA PIC 20 -> "Miscellaneous"
                    imageItem.setCategory(20);
                    imageItem.setCopyrightNotice(imageGallery.getCopyRight());
                    // TODO: handle image descriptions
                    imageItem.setDescriptions(null);
                    imageItem.setUrl(imageGallery.getImageUrl());

                    return imageItem;
                })
                .collect(Collectors.toList());
    }

}
