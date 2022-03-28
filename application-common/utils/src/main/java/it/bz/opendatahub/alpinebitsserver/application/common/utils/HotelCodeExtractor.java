/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRQ;

/**
 * Utility class to extract the hotel code from {@link OTAHotelDescriptiveInfoRQ}
 * or {@link OTAHotelDescriptiveContentNotifRQ} instances.
 */
public final class HotelCodeExtractor {

    private static final String ATTRIBUTE_HOTEL_CODE_IS_REQUIRED = "Attribute HotelCode is required";

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
            throw new AlpineBitsException(ATTRIBUTE_HOTEL_CODE_IS_REQUIRED, 400);
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
            throw new AlpineBitsException(ATTRIBUTE_HOTEL_CODE_IS_REQUIRED, 400);
        }
        return hotelDescriptiveContent.getHotelCode();
    }

    /**
     * Extract the hotel code from the given {@link OTAHotelInvCountNotifRQ} instance or
     * throw an {@link AlpineBitsException} if such an element does not exist (either because
     * the path leading to the hotel code is somewhere empty, or because the hotel code is
     * null).
     *
     * @param otaHotelInvCountNotifRQ Extract the hotel code from this element.
     * @return The hotel code if found. If no hotel code could be found, an {@link AlpineBitsException}
     * will be thrown, indicating the cause.
     * @throws AlpineBitsException If no hotel code could be found.
     */
    public static String getHotelCodeOrThrowIfNotExistent(OTAHotelInvCountNotifRQ otaHotelInvCountNotifRQ) {
        if (otaHotelInvCountNotifRQ == null) {
            throw new AlpineBitsException("Element OTAHotelInvCountNotifRQ is required", 400);
        }
        if (otaHotelInvCountNotifRQ.getInventories() == null) {
            throw new AlpineBitsException("Element Inventories is required", 400);
        }
        if (otaHotelInvCountNotifRQ.getInventories().getHotelCode() == null) {
            throw new AlpineBitsException(ATTRIBUTE_HOTEL_CODE_IS_REQUIRED, 400);
        }

        return otaHotelInvCountNotifRQ.getInventories().getHotelCode();
    }

    /**
     * Extract the hotel code from the given {@link OTAHotelAvailNotifRQ} instance or
     * throw an {@link AlpineBitsException} if such an element does not exist (either because
     * the path leading to the hotel code is somewhere empty, or because the hotel code is
     * null).
     *
     * @param otaHotelAvailNotifRQ Extract the hotel code from this element.
     * @return The hotel code if found. If no hotel code could be found, an {@link AlpineBitsException}
     * will be thrown, indicating the cause.
     * @throws AlpineBitsException If no hotel code could be found.
     */
    public static String getHotelCodeOrThrowIfNotExistent(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        if (otaHotelAvailNotifRQ == null) {
            throw new AlpineBitsException("Element OTAHotelAvailNotifRQ is required", 400);
        }
        if (otaHotelAvailNotifRQ.getAvailStatusMessages() == null) {
            throw new AlpineBitsException("Element AvailStatusMessages is required", 400);
        }
        if (otaHotelAvailNotifRQ.getAvailStatusMessages().getHotelCode() == null) {
            throw new AlpineBitsException(ATTRIBUTE_HOTEL_CODE_IS_REQUIRED, 400);
        }

        return otaHotelAvailNotifRQ.getAvailStatusMessages().getHotelCode();
    }

    /**
     * Check if a HotelCode element is specified in the given {@link OTAHotelDescriptiveInfoRQ} document.
     *
     * @param otaHotelDescriptiveInfoRQ Check if this document species a HotelCode.
     * @return <code>true</code> if the document specifies a HotelCode, <code>false</code> otherwise.
     */
    public static boolean hasHotelCode(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        return otaHotelDescriptiveInfoRQ != null
                && otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos() != null
                && !otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().isEmpty()
                && otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().get(0) != null
                && otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().get(0).getHotelCode() != null;
    }

    /**
     * Check if a HotelCode element is specified in the given {@link OTAHotelDescriptiveContentNotifRQ} document.
     *
     * @param otaHotelDescriptiveContentNotifRQ Check if this document species a HotelCode.
     * @return <code>true</code> if the document specifies a HotelCode, <code>false</code> otherwise.
     */
    public static boolean hasHotelCode(OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ) {
        return otaHotelDescriptiveContentNotifRQ != null
                && otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents() != null
                && otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContents() != null
                && !otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContents().isEmpty()
                && otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).getHotelCode() != null;
    }

    /**
     * Check if a HotelCode element is specified in the given {@link OTAHotelInvCountNotifRQ} document.
     *
     * @param otaHotelInvCountNotifRQ Check if this document species a HotelCode.
     * @return <code>true</code> if the document specifies a HotelCode, <code>false</code> otherwise.
     */
    public static boolean hasHotelCode(OTAHotelInvCountNotifRQ otaHotelInvCountNotifRQ) {
        return otaHotelInvCountNotifRQ != null
                && otaHotelInvCountNotifRQ.getInventories() != null
                && otaHotelInvCountNotifRQ.getInventories().getHotelCode() != null;
    }

    /**
     * Check if a HotelCode element is specified in the given {@link OTAHotelAvailNotifRQ} document.
     *
     * @param otaHotelAvailNotifRQ Check if this document species a HotelCode.
     * @return <code>true</code> if the document specifies a HotelCode, <code>false</code> otherwise.
     */
    public static boolean hasHotelCode(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        return otaHotelAvailNotifRQ != null
                && otaHotelAvailNotifRQ.getAvailStatusMessages() != null
                && otaHotelAvailNotifRQ.getAvailStatusMessages().getHotelCode() != null;
    }

}
