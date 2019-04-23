package it.bz.opendatahub.alpinebitsserver.odh.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;

/**
 * A service to handle an AlpineBits Inventory/Basic pull requests.
 */
public interface InventoryPullService {

    /**
     * Read and return Inventory Basic information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link HotelDescriptiveInfoResponse} with Inventory Basic information
     */
    HotelDescriptiveInfoResponse readBasic(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest);

    /**
     * Read and return Inventory HotelInfo information.
     *
     * @param hotelDescriptiveInfoRequest this element contains the request information
     * @return a {@link HotelDescriptiveInfoResponse} with Inventory HotelInfo information
     */
    HotelDescriptiveInfoResponse readHotelInfo(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest);

}
