/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link MessageAcknowledgementTypeBuilder}.
 */
public class MessageAcknowledgementTypeBuilderTest {

    private static final String DEFAULT_MESSAGE = "some error message";

    @Test
    public void testForSuccess_ShouldReturnSuccess() {
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forSuccess();
        assertNotNull(mat.getSuccess());
        assertNotNull(mat.getVersion());
        assertNull(mat.getWarnings());
        assertNull(mat.getErrors());
    }

    @Test
    public void testForError_ShouldReturnError() {
        String code = "23";
        String type = "42";

        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forError(DEFAULT_MESSAGE, code, type);
        assertNotNull(mat.getVersion());
        assertNull(mat.getSuccess());
        assertNull(mat.getWarnings());

        ErrorType errorType = mat.getErrors().getErrors().get(0);
        assertEquals(errorType.getValue(), DEFAULT_MESSAGE);
        assertEquals(errorType.getCode(), code);
        assertEquals(errorType.getType(), type);
    }

    @Test
    public void testForError_WithMessageOnly_ShouldReturnErrorWithDefaultCodeAndType() {
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.forError(DEFAULT_MESSAGE);
        assertNotNull(mat.getVersion());
        assertNull(mat.getSuccess());
        assertNull(mat.getWarnings());

        ErrorType errorType = mat.getErrors().getErrors().get(0);
        assertEquals(errorType.getValue(), DEFAULT_MESSAGE);
        assertEquals(errorType.getCode(), MessageAcknowledgementTypeBuilder.CODE_UNABLE_TO_PROCESS);
        assertEquals(errorType.getType(), MessageAcknowledgementTypeBuilder.TYPE_DEFAULT);
    }

}