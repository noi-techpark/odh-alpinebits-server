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
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * This middleware checks if the HotelCode is present in the given document.
 * <p>
 * If it is not, an AlpineBits Warning outcome will be returned, because ODH
 * requires a HotelCode element to be set.
 *
 * @param <T> The type of the XML request document.
 * @param <S> The type of the XML response document.
 */
public class HotelCodeMissingChecker<T, S> implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(HotelCodeMissingChecker.class);

    private final Key<T> requestKey;
    private final Key<S> responseKey;
    private final Function<T, Boolean> checkHotelCodeFun;
    private final Function<MessageAcknowledgementType, S> responseOutcomeBuilder;

    public HotelCodeMissingChecker(
            Key<T> requestKey,
            Key<S> responseKey,
            Function<T, Boolean> checkHotelCodeFun,
            Function<MessageAcknowledgementType, S> responseOutcomeBuilder
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
        this.checkHotelCodeFun = checkHotelCodeFun;
        this.responseOutcomeBuilder = responseOutcomeBuilder;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        T data = ctx.getOrThrow(requestKey);
        Boolean hasHotelCode = checkHotelCodeFun.apply(data);

        if (Boolean.TRUE.equals(hasHotelCode)) {
            chain.next();
        } else {
            String requestVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
            String requestAction = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
            String requestId = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
            LOG.warn(
                    "Warning while handling {} action \"{}\": required HotelCode is missing, request is aborted (rid = {})",
                    requestVersion,
                    requestAction,
                    requestId
            );

            String message = String.format("Open Data Hub requires the HotelCode to be specified, but it is missing. Process aborted (rid = %s)", requestId);

            // Returning a WARNING instead of an ERROR would be more appropriate according to AlpineBits spec.
            // Unfortunately, XSD and RNG definitions for Inventory Basic / HotelInfo pull responses don't allow a WARNING element,
            // which contradicts the written standard and seems to be a bug in XSD / RNG files.
            // Until the bug is fixed, an ERROR is returned
            ErrorEntry entry = new ErrorEntry(message, OTACodeErrorCodes.INVALID_HOTEL_CODE);
            MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.error(entry);

            S responseOutcome = responseOutcomeBuilder.apply(mat);
            ctx.put(this.responseKey, responseOutcome);
        }
    }

}
