package it.bz.opendatahub.alpinebitsserver.odh.inventory.impl;

import it.bz.opendatahub.alpinebitsserver.application.common.exception.StartupConditionFailedException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhBackendService;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.OdhClient;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendServiceImpl;

/**
 * The static methods in this class provide methods to
 * build an {@link OdhInventoryPullService}.
 */
public final class ODHInventoryPullServiceBuilder {

    public static final String ALPINEBITS_ODH_USERNAME = "ALPINEBITS_ODH_USERNAME";

    // Allow PASSWORD as part of the variable name
    @SuppressWarnings("squid:S2068")
    public static final String ALPINEBITS_ODH_PASSWORD = "ALPINEBITS_ODH_PASSWORD";

    private ODHInventoryPullServiceBuilder() {
        // Empty
    }

    /**
     * This method returns an {@link OdhInventoryPullService} that
     * reads its data from ODH.
     * <p>
     * The configuration for the OdhInventoryPullService is read
     * from the following System.env variables:
     * <ul>
     * <li>ALPINEBITS_ODH_USERNAME</li>
     * <li>ALPINEBITS_ODH_PASSWORD</li>
     * </ul>
     * <p>
     * If any of the required environment variables mentioned above could not
     * be found or are null, a {@link StartupConditionFailedException} is thrown.
     *
     * @return a {@link OdhInventoryPullService}
     * @throws StartupConditionFailedException if any of the required environment
     *                                         variables could not be found or is null
     */
    public static OdhInventoryPullService buildInventoryPullService() {
        String odhUsername = System.getenv(ALPINEBITS_ODH_USERNAME);
        if (odhUsername == null) {
            throw new StartupConditionFailedException("Required environment variable \"" + ALPINEBITS_ODH_USERNAME + "\" missing");
        }

        String odhPassword = System.getenv(ALPINEBITS_ODH_PASSWORD);
        if (odhPassword == null) {
            throw new StartupConditionFailedException("Required environment variable \"" + ALPINEBITS_ODH_PASSWORD + "\" missing");
        }

        OdhClient odhClient = new OdhClient(OdhBackendServiceImpl.DEFAULT_ODH_BASE_URL, odhUsername, odhPassword);
        OdhBackendService odhBackendService = new OdhBackendServiceImpl(odhClient);
        return new OdhInventoryPullService(odhBackendService);
    }

}
