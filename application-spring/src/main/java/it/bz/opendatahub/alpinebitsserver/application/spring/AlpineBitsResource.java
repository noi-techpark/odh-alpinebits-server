package it.bz.opendatahub.alpinebitsserver.application.spring;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.opendatahub.alpinebits.servlet.ContextBuilder;
import it.bz.opendatahub.alpinebits.servlet.impl.AlpineBitsServlet;
import it.bz.opendatahub.alpinebits.servlet.impl.DefaultContextBuilder;
import it.bz.opendatahub.alpinebits.servlet.impl.DefaultRequestExceptionHandler;
import it.bz.opendatahub.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.StatisticsMiddleware;
import it.bz.opendatahub.alpinebitsserver.application.common.middleware.UsernameAndPasswordMatchAuthorizationMiddleware;
import it.bz.opendatahub.alpinebitsserver.application.spring.middleware.MultipartFormExtractorMiddleware;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.impl.ODHInventoryPullServiceBuilder;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.impl.OdhInventoryPullService;
import it.bz.opendatahub.alpinebitsserver.odh.inventory.middleware.InventoryPullMiddlewareBuilder;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
public class AlpineBitsResource {

    private final ContextBuilder contextBuilder = new DefaultContextBuilder();
    private final DefaultRequestExceptionHandler defaultRequestExceptionHandler = new DefaultRequestExceptionHandler();

    private Middleware middleware;

    @PostConstruct
    public void initMiddleware() throws JAXBException {
        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new StatisticsMiddleware(),
                new AlpineBitsClientProtocolMiddleware(),
                new BasicAuthenticationMiddleware(),
                new UsernameAndPasswordMatchAuthorizationMiddleware(),
                new GzipUnsupportedMiddleware(),
                new MultipartFormExtractorMiddleware(),
                this.buildRoutingMiddleware()
        ));
    }

    @PostMapping("${alpinebits.path}")
    public void alpineBitsEntryPoint(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestId = UUID.randomUUID().toString();
            request.setAttribute(AlpineBitsServlet.REQUEST_ID, requestId);
            MDC.put(AlpineBitsServlet.REQUEST_ID, requestId);

            Context ctx = contextBuilder.fromRequest(request, response, requestId);
            this.middleware.handleContext(ctx, null);
        } catch (Exception e) {
            this.defaultRequestExceptionHandler.handleRequestException(request, response, e);
        }
    }

    private Middleware buildRoutingMiddleware() throws JAXBException {
        OdhInventoryPullService inventoryPullService = ODHInventoryPullServiceBuilder.buildInventoryPullService();

        Router router = new DefaultRouter.Builder()
                .version(AlpineBitsVersion.V_2017_10)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_BASIC_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware(inventoryPullService))
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware(inventoryPullService))
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

}
