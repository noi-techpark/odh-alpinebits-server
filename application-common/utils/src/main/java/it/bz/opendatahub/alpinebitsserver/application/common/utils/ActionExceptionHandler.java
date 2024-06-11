// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.common.utils;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.utils.response.ErrorEntry;
import it.bz.opendatahub.alpinebits.common.utils.response.MessageAcknowledgementTypeBuilder;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.validation.Schema;
import java.util.function.Function;

/**
 * This middleware handler catches all uncaught actions and outputs an XML document
 * of the type specified within the responseKey with the given error message.
 *
 * @param <T> The type of the XML response document.
 */
public class ActionExceptionHandler<T> implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(ActionExceptionHandler.class);

    private final Key<T> responseKey;
    private final Function<MessageAcknowledgementType, T> responseOutcomeBuilder;
    private final Middleware objectToXmlMiddleware;

    public ActionExceptionHandler(String alpineBitsVersion, Key<T> responseKey, Function<MessageAcknowledgementType, T> responseOutcomeBuilder) {
        this.responseKey = responseKey;
        this.responseOutcomeBuilder = responseOutcomeBuilder;

        this.objectToXmlMiddleware = buildObjectToXmlConvertingMiddleware(alpineBitsVersion);
    }

    @Override
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void handleContext(Context ctx, MiddlewareChain chain) {
        try {
            chain.next();
        } catch (Exception e) {
            String requestVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
            String requestAction = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
            String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
            LOG.error("Error while handling {} action \"{}\" (rid = {})", requestVersion, requestAction, requestId, e);

            String message = buildErrorMessage(e.getMessage(), requestId);
            ErrorEntry entry = new ErrorEntry(message, OTACodeErrorCodes.UNABLE_TO_PROCESS);
            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.error(entry);

            T responseOutcome = responseOutcomeBuilder.apply(mat);
            ctx.put(responseKey, responseOutcome);

            objectToXmlMiddleware.handleContext(ctx, () -> {
            });
        }
    }

    private String buildErrorMessage(String message, String requestId) {
        return message + " (rid = " + requestId + ")";
    }

    private Middleware buildObjectToXmlConvertingMiddleware(String alpineBitsVersion) {
        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(alpineBitsVersion);
        ObjectToXmlConverter converter = new JAXBObjectToXmlConverter.Builder()
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, responseKey);
    }
}
