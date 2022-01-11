/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.exception.OdhBackendException;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.service.OdhBackendService;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.testng.Assert.assertNotNull;

/**
 * Tests for {@link AuthenticatedInventoryPullServiceImpl}.
 */
public class AuthenticatedInventoryPullServiceImplTest {

    @Test
    public void testReadBasic_ShouldReturnOTAHotelDescriptiveInfoRSWithError_OnOdhBackendClientException() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryBasic(any())).thenThrow(OdhBackendException.class);

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS ota = inventoryPullService.readBasic("123");

        assertNotNull(ota.getErrors());
    }

    @Test
    public void testReadBasic_ShouldReturnSuccessResult() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryBasic(any())).thenReturn(Optional.of(new OTAHotelDescriptiveInfoRS()));

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullService.readBasic("123");

        assertNotNull(otaHotelDescriptiveInfoRS.getSuccess());
        assertNotNull(otaHotelDescriptiveInfoRS.getVersion());
    }

    @Test
    public void testReadBasic_ShouldReturnErrorResult_WhenOdhBackendClientReturnedNoResult() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryBasic(any())).thenReturn(Optional.empty());

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullService.readBasic("123");

        assertNotNull(otaHotelDescriptiveInfoRS.getErrors());
        assertNotNull(otaHotelDescriptiveInfoRS.getVersion());
    }

    @Test
    public void testReadHotelInfo_ShouldReturnOTAHotelDescriptiveInfoRSWithError_OnOdhBackendClientException() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryHotelInfo(any())).thenThrow(OdhBackendException.class);

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS ota = inventoryPullService.readHotelInfo("123");

        assertNotNull(ota.getErrors());
    }

    @Test
    public void testReadHotelInfo_ShouldReturnSuccessResult() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryHotelInfo(any())).thenReturn(Optional.of(new OTAHotelDescriptiveInfoRS()));

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullService.readHotelInfo("123");

        assertNotNull(otaHotelDescriptiveInfoRS.getSuccess());
        assertNotNull(otaHotelDescriptiveInfoRS.getVersion());
    }

    @Test
    public void testReadHotelInfo_ShouldReturnErrorResult_WhenOdhBackendClientReturnedNoResult() throws OdhBackendException {
        OdhBackendService service = Mockito.mock(OdhBackendService.class);
        Mockito.when(service.fetchInventoryHotelInfo(any())).thenReturn(Optional.empty());

        AuthenticatedInventoryPullServiceImpl inventoryPullService = new AuthenticatedInventoryPullServiceImpl(service);
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = inventoryPullService.readHotelInfo("123");

        assertNotNull(otaHotelDescriptiveInfoRS.getErrors());
        assertNotNull(otaHotelDescriptiveInfoRS.getVersion());
    }
}