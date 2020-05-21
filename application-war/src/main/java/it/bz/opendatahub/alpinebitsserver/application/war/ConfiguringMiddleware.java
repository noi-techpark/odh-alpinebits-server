/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.war;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.ContentTypeHintMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.StatisticsMiddleware;
import it.bz.opendatahub.alpinebitsserver.application.common.environment.FilePropertySource;
import it.bz.opendatahub.alpinebitsserver.application.common.environment.PropertyProvider;
import it.bz.opendatahub.alpinebitsserver.application.common.routing.RoutingMiddlewareProvider;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.middleware.OdhBackendServiceProvidingMiddleware;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * This {@link Middleware} configures a set of middlewares, such that
 * the resulting server is able to respond to AlpineBits Inventory requests.
 * <p>
 * The resulting server supports the {@link AlpineBitsVersion#V_2017_10}
 * and {@link AlpineBitsVersion#V_2018_10} versions.
 * <p>
 * A basic authentication check is enforced, although any username and
 * password combination is valid. In other words: a request MUST contain
 * basic authentication information, but that information is not checked
 * any further.
 */
public class ConfiguringMiddleware implements Middleware {

    private Middleware middleware;

    public ConfiguringMiddleware() throws JAXBException {
        String odhUrl = this.getOdhUrl();

        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new StatisticsMiddleware(),
                new ContentTypeHintMiddleware(),
                new AlpineBitsClientProtocolMiddleware(),
                new BasicAuthenticationMiddleware(),
                new GzipUnsupportedMiddleware(),
                new MultipartFormDataParserMiddleware(),
                new OdhBackendServiceProvidingMiddleware(odhUrl),
                RoutingMiddlewareProvider.buildRoutingMiddleware()
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

    private String getOdhUrl() {
        PropertyProvider propertyProvider = new PropertyProvider.Builder()
                .withFilePropertySource(FilePropertySource.fromDefaultFile())
                .build();
        return propertyProvider.getValue("odh.url");
    }

}