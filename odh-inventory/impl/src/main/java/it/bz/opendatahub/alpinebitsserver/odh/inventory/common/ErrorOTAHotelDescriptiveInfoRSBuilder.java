/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Helper class to build error responses for {@link OTAHotelDescriptiveInfoRS} documents.
 */
public final class ErrorOTAHotelDescriptiveInfoRSBuilder {

    private ErrorOTAHotelDescriptiveInfoRSBuilder() {
        // Empty
    }

    public static OTAHotelDescriptiveInfoRS noDataFound(String hotelCode) {
        Objects.requireNonNull(hotelCode, "HotelCode must not be null");

        // The value "425" means "No match found" according to OTA 2015A "Error Codes" list
        ErrorsType errors = buildError(
                "425",
                String.format("No data for HotelCode %s found", hotelCode)
        );

        OTAHotelDescriptiveInfoRS ota = new OTAHotelDescriptiveInfoRS();
        // Set error information for the client. A warning for the "no data found" - case would
        // be more appropriate, but is not allowed due to a bug in the AlpineBits XSD.
        ota.setErrors(errors);
        ota.setVersion(BigDecimal.valueOf(8.000));

        return ota;
    }

    public static OTAHotelDescriptiveInfoRS undeterminedError(String hotelCode, String errorMessage) {
        Objects.requireNonNull(hotelCode, "HotelCode must not be null");

        // The value "197" means "Undetermined error - please report" according to OTA 2015A "Error Codes" list
        ErrorsType errors = buildError(
                "197",
                String.format("Undetermined error for HotelCode %s - please report. Details: %s", hotelCode, errorMessage)
        );

        OTAHotelDescriptiveInfoRS ota = new OTAHotelDescriptiveInfoRS();
        ota.setErrors(errors);
        ota.setVersion(BigDecimal.valueOf(8.000));

        return ota;
    }

    public static ErrorsType buildError(String code, String message) {
        ErrorType errorType = new ErrorType();
        errorType.setType("13");
        errorType.setCode(code);
        errorType.setValue(message);

        ErrorsType errors = new ErrorsType();
        errors.getErrors().add(errorType);
        return errors;
    }

}
