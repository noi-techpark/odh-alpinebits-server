/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory.HotelInfoConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo;

import java.io.IOException;

/**
 * JSON serializer for {@link HotelInfo}.
 */
public class HotelInfoSerializer201710 extends JsonSerializer<HotelInfo> {

    private static final HotelInfoConverter HOTEL_INFO_CONVERTER = HotelInfoConverter.newInstance();

    @Override
    public void serialize(HotelInfo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setHotelInfo(value);
        HotelInfoType hotelInfoType = HOTEL_INFO_CONVERTER.toHotelInfoType(hotelDescriptiveContent);
        gen.writeObject(hotelInfoType);
    }

}
