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
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory.ContactInfosConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;

import java.io.IOException;

/**
 * JSON deserializer for {@link ContactInfos}.
 */
public class ContactInfosDeserializer201710 extends JsonDeserializer<ContactInfos> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ContactInfosConverter CONTACT_INFOS_CONVERTER = ContactInfosConverter.newInstance();

    static {
        MAPPER.registerModule(new JaxbAnnotationModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ContactInfos deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        ContactInfosType contactInfosType = MAPPER.treeToValue(node, ContactInfosType.class);

        return buildContactInfos(contactInfosType);
    }

    private ContactInfos buildContactInfos(ContactInfosType contactInfosType) {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        CONTACT_INFOS_CONVERTER.applyContactInfo(hotelDescriptiveContent, contactInfosType);
        return hotelDescriptiveContent.getContactInfos();
    }

}
