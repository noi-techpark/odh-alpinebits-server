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

    /**
     * Build a {@link MessageAcknowledgementType} that represents a successful outcome.
     *
     * @return A MessageAcknowledgementType for successful outcome.
     */
    public static MessageAcknowledgementType forSuccess() {
        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setSuccess(new SuccessType());
        mat.setVersion(BigDecimal.ONE);
        return mat;
    }

    /**
     * Build a {@link MessageAcknowledgementType} that represents an error outcome.
     * <p>
     * The <code>message</code> is taken from the provided parameter, <code>code</code>
     * is set to {@link #CODE_UNABLE_TO_PROCESS} and <code>type</code> is set to
     * {@link #TYPE_DEFAULT}.
     *
     * @param message The error message.
     * @return A MessageAcknowledgementType representing an error outcome.
     */
    public static MessageAcknowledgementType forError(String message) {
        return forError(message, CODE_UNABLE_TO_PROCESS, TYPE_DEFAULT);
    }

    /**
     * Build a {@link MessageAcknowledgementType} that represents an error outcome.
     * <p>
     * The <code>message</code>, <code>code</code> and <code>type</code> values are
     * provided by the method parameters.
     *
     * @param message The error message.
     * @param code    The error code.
     * @param type    The error type.
     * @return A MessageAcknowledgementType representing an error outcome.
     */
    public static MessageAcknowledgementType forError(String message, String code, String type) {
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
