/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;

/**
 * Utility class to extract the hotel code from {@link OTAHotelDescriptiveInfoRQ}
 * or {@link OTAHotelDescriptiveContentNotifRQ} instances.
 */
public final class HotelCodeExtractor {

    private HotelCodeExtractor() {
        // Empty
    }

    /**
     * Extract the hotel code from the given {@link OTAHotelDescriptiveInfoRQ} instance or
     * throw an {@link AlpineBitsException} if such an element does not exist (either because
     * the path leading to the hotel code is somewhere empty, or because the hotel code is
     * null).
     *
     * @param otaHotelDescriptiveInfoRQ Extract the hotel code from this element.
     * @return The hotel code if found. If no hotel code could be found, an {@link AlpineBitsException}
     * will be thrown, indicating the cause.
     * @throws AlpineBitsException If no hotel code could be found.
     */
    public static String getHotelCodeOrThrowIfNotExistent(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        if (otaHotelDescriptiveInfoRQ == null) {
            throw new AlpineBitsException("Element OTAHotelDescriptiveInfoRQ is required", 400);
        }
        if (otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos() == null) {
            throw new AlpineBitsException("Element HotelDescriptiveContents is required", 400);
        }
        if (otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().isEmpty()) {
            throw new AlpineBitsException("Element HotelDescriptiveContent is required", 400);
        }

        HotelDescriptiveInfo hotelDescriptiveInfo = otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().get(0);
        if (hotelDescriptiveInfo.getHotelCode() == null) {
            throw new AlpineBitsException("Attribute HotelCode is required", 400);
        }
        return hotelDescriptiveInfo.getHotelCode();
    }

    /**
     * Extract the hotel code from the given {@link OTAHotelDescriptiveContentNotifRQ} instance or
     * throw an {@link AlpineBitsException} if such an element does not exist (either because
     * the path leading to the hotel code is somewhere empty, or because the hotel code is
     * null).
     *
     * @param otaHotelDescriptiveContentNotifRQ Extract the hotel code from this element.
     * @return The hotel code if found. If no hotel code could be found, an {@link AlpineBitsException}
     * will be thrown, indicating the cause.
     * @throws AlpineBitsException If no hotel code could be found.
     */
    public static String getHotelCodeOrThrowIfNotExistent(OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ) {
        if (otaHotelDescriptiveContentNotifRQ == null) {
            throw new AlpineBitsException("Element OTAHotelDescriptiveContentNotifRQ is required", 400);
        }
        if (otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents() == null) {
            throw new AlpineBitsException("Element HotelDescriptiveContents is required", 400);
        }
        if (otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContents().isEmpty()) {
            throw new AlpineBitsException("Element HotelDescriptiveContent is required", 400);
        }

        HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
        if (hotelDescriptiveContent.getHotelCode() == null) {
            throw new AlpineBitsException("Attribute HotelCode is required", 400);
        }
        return hotelDescriptiveContent.getHotelCode();
    }
}
