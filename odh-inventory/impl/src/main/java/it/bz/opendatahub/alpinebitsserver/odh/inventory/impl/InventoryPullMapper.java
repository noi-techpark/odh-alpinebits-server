/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.impl;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.ImageItem;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TextItemDescription;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TypeRoom;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.AccomodationRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapping utility class for inventory pull actions.
 */
public class InventoryPullMapper {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryPullServiceImpl.class);

    /**
     * Map a list of {@link AccomodationRoom} elements to a
     * {@link HotelDescriptiveContent} element for the Inventory HotelInfo.
     *
     * @param rooms Map this list to a HotelDescriptiveContent element.
     * @return The mapped HotelDescriptiveContent element.
     */
    public HotelDescriptiveContent mapToHotelDescriptiveContentForBasic(List<AccomodationRoom> rooms) {
        List<GuestRoom> guestRooms = rooms
                .stream()
                .map(room -> {
                    // Compute guest room description used as room category
                    // description for AlpineBits GuestRooms
                    GuestRoom guestRoomDescription = new GuestRoom();
                    guestRoomDescription.setCode(room.getRoomcode());
                    guestRoomDescription.setMinOccupancy(room.getRoommin());
                    guestRoomDescription.setMaxOccupancy(room.getRoommax());
                    guestRoomDescription.setTypeRoom(this.buildTypeRoom(room));
                    guestRoomDescription.setLongNames(this.buildLongname(room));
                    guestRoomDescription.setDescriptions(this.buildDescriptions(room));
                    guestRoomDescription.setPictures(this.buildPictures(room));
                    guestRoomDescription.setRoomAmenityCodes(this.buildRoomAmenityCodes(room));

                    // Compute list of real rooms
                    List<GuestRoom> realRooms = new ArrayList<>();
                    if (room.getRoomNumbers() != null) {
                        // Room numbers are available
                        for (String roomNumber : room.getRoomNumbers()) {
                            GuestRoom realRoom = this.buildGuestRoom(room, roomNumber);
                            realRooms.add(realRoom);
                        }
                    } else {
                        // No room numbers defined. Fall back to simple counter implementation.
                        // Note, that the generated room numbers usually are unknown top the hotel
                        // Also note, that the Inventory AlpineBits response doesn't contain a warning
                        // element. Otherwise it would be a good idea to add a warning to the response
                        LOG.warn("No room numbers found for room with ID {}. Falling back to counter", room.getId());

                        for (int i = 0; i < room.getRoomQuantity(); i++) {
                            String roomNumber = guestRoomDescription.getCode() + "-" + i;
                            GuestRoom realRoom = this.buildGuestRoom(room, roomNumber);
                            realRooms.add(realRoom);
                        }
                    }
                    realRooms.sort(Comparator.comparing(o -> o.getTypeRoom().getRoomId()));

                    // Concatenate room category information and all real rooms
                    List<GuestRoom> allRooms = new ArrayList<>();
                    allRooms.add(guestRoomDescription);
                    allRooms.addAll(realRooms);

                    return allRooms;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setGuestRooms(guestRooms);

        return hotelDescriptiveContent;
    }

    /**
     * Map a list of {@link AccomodationRoom} elements to a
     * {@link HotelDescriptiveContent} element for the Inventory HotelInfo.
     *
     * @param rooms Map this list to a HotelDescriptiveContent element.
     * @return The mapped HotelDescriptiveContent element.
     */
    public HotelDescriptiveContent mapToHotelDescriptiveContentForHotelInfo(List<AccomodationRoom> rooms) {
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

    private GuestRoom buildGuestRoom(AccomodationRoom room, String roomId) {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(room.getRoomcode());
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setRoomId(roomId);
        guestRoom.setTypeRoom(typeRoom);
        return guestRoom;
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
                .map(feature -> feature.getRoomAmenityCodes() != null ? feature.getRoomAmenityCodes() : new ArrayList<Integer>())
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(Integer::intValue))
                .distinct()
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

                    // The data provider of ODH don't deliver image descriptions reliably,
                    // so the descriptions are kept empty. This may change in the future
                    imageItem.setDescriptions(null);
                    imageItem.setUrl(imageGallery.getImageUrl());

                    return imageItem;
                })
                .collect(Collectors.toList());
    }

}
