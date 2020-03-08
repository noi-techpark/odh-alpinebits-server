/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory.ContactInfosConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;

import java.io.IOException;

/**
 * JSON serializer for {@link ContactInfos}.
 */
public class ContactInfosSerializer201710 extends JsonSerializer<ContactInfos> {

    private static final ContactInfosConverter CONTACT_INFOS_CONVERTER = ContactInfosConverter.newInstance();

    @Override
    public void serialize(ContactInfos value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setContactInfos(value);
        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(hotelDescriptiveContent);
        gen.writeObject(contactInfosType);
    }

}
