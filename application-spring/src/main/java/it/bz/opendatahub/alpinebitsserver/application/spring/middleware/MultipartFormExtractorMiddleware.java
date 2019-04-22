package it.bz.opendatahub.alpinebitsserver.application.spring.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.servlet.ServletContextKey;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFormExtractorMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        try {
            HttpServletRequest httpServletRequest = ctx.getOrThrow(ServletContextKey.SERVLET_REQUEST);

            Part actionPart = httpServletRequest.getPart("action");
            Part requestPart = httpServletRequest.getPart("request");

            String action = IOUtils.toString(actionPart.getInputStream());
            InputStream request = requestPart.getInputStream();

            ctx.put(RequestContextKey.REQUEST_ACTION, action);
            ctx.put(RequestContextKey.REQUEST_CONTENT_STREAM, request);

            chain.next();
        } catch (IOException | ServletException e) {
            throw new AlpineBitsException("Error while parsing multipart/form-data", 500, e);
        }
    }
}
