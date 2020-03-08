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
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.AffiliationInfoConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo;

import java.io.IOException;

/**
 * JSON deserializer for {@link AffiliationInfo}.
 */
public class AffiliationInfoDeserializer201810 extends JsonDeserializer<AffiliationInfo> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AffiliationInfoConverter AFFILIATION_INFO_CONVERTER = AffiliationInfoConverter.newInstance();

    static {
        MAPPER.registerModule(new JaxbAnnotationModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public AffiliationInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        AffiliationInfoType affiliationInfoType = MAPPER.treeToValue(node, AffiliationInfoType.class);

        return buildAffiliationInfo(affiliationInfoType);
    }

    private AffiliationInfo buildAffiliationInfo(AffiliationInfoType affiliationInfoType) {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        AFFILIATION_INFO_CONVERTER.applyAffiliationInfo(hotelDescriptiveContent, affiliationInfoType);
        return hotelDescriptiveContent.getAffiliationInfo();
    }

}
