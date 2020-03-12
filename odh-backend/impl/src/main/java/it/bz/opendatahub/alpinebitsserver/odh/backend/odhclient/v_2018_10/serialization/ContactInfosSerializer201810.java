/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;

import java.io.IOException;

/**
 * JSON serializer for {@link ContactInfos}.
 */
public class ContactInfosSerializer201810 extends JsonSerializer<ContactInfos> {

    @Override
    public void serialize(ContactInfos value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Wrap ContactInfo into array to make the generated JSON compatible with OTA-2015A
        // This simplifies the handling of the ContactInfos element for different AlpineBits versions
        gen.writeStartObject();
        gen.writeArrayFieldStart("ContactInfo");
        gen.writeObject(value.getContactInfo());
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
