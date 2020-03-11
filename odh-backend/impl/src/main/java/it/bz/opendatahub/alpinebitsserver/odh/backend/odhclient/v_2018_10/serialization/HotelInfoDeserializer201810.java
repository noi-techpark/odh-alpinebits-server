/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.HotelInfoConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo;

import java.io.IOException;

/**
 * JSON deserializer for {@link HotelInfo}.
 */
public class HotelInfoDeserializer201810 extends JsonDeserializer<HotelInfo> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HotelInfoConverter HOTEL_INFO_CONVERTER = HotelInfoConverter.newInstance();

    static {
        MAPPER.registerModule(new JaxbAnnotationModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public HotelInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        HotelInfoType hotelInfoType = MAPPER.treeToValue(node, HotelInfoType.class);

        return buildHotelInfo(hotelInfoType);
    }

    private HotelInfo buildHotelInfo(HotelInfoType hotelInfoType) {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        HOTEL_INFO_CONVERTER.applyHotelInfo(hotelDescriptiveContent, hotelInfoType);
        return hotelDescriptiveContent.getHotelInfo();
    }

}
