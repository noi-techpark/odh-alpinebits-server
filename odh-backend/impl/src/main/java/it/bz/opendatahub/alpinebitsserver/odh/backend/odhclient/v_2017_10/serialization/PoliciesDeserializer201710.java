/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory.PoliciesConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies;

import java.io.IOException;

/**
 * JSON deserializer for {@link Policies}.
 */
public class PoliciesDeserializer201710 extends JsonDeserializer<Policies> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final PoliciesConverter POLICIES_CONVERTER = PoliciesConverter.newInstance();

    static {
        MAPPER.registerModule(new JaxbAnnotationModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Policies deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        HotelDescriptiveContentType.Policies policiesType = MAPPER.treeToValue(node, HotelDescriptiveContentType.Policies.class);

        return buildPolicies(policiesType);
    }

    private Policies buildPolicies(HotelDescriptiveContentType.Policies policiesType) {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        POLICIES_CONVERTER.applyPolicies(hotelDescriptiveContent, policiesType);
        return hotelDescriptiveContent.getPolicies();
    }

}
