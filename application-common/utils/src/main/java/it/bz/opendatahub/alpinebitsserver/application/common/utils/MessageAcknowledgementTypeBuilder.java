/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;

import java.math.BigDecimal;

/**
 * Utility class to build instances of {@link MessageAcknowledgementType}.
 */
public final class MessageAcknowledgementTypeBuilder {

    public static final String TYPE_DEFAULT = "13";
    public static final String CODE_UNABLE_TO_PROCESS = "450";

    private MessageAcknowledgementTypeBuilder() {
        // Empty
    }

    public static MessageAcknowledgementType forSuccess() {
        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setSuccess(new SuccessType());
        mat.setVersion(BigDecimal.ONE);
        return mat;
    }

    public static MessageAcknowledgementType forError(String message) {
        return forError(CODE_UNABLE_TO_PROCESS, TYPE_DEFAULT, message);
    }

    public static MessageAcknowledgementType forError(String code, String type, String message) {
        ErrorType errorType = new ErrorType();
        errorType.setValue(message);
        errorType.setCode(code);
        errorType.setType(type);

        ErrorsType errorsType = new ErrorsType();
        errorsType.getErrors().add(errorType);
        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setErrors(errorsType);
        mat.setVersion(BigDecimal.ONE);

        return mat;
    }
}
