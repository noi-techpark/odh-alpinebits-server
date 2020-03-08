/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.PoliciesType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.PoliciesConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies;

import java.io.IOException;

/**
 * JSON serializer for {@link Policies}.
 */
public class PoliciesSerializer201810 extends JsonSerializer<Policies> {

    private static final PoliciesConverter POLICIES_CONVERTER = PoliciesConverter.newInstance();

    @Override
    public void serialize(Policies value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setPolicies(value);
        PoliciesType policiesType = POLICIES_CONVERTER.toPolicies(hotelDescriptiveContent);
        gen.writeObject(policiesType);
    }

}
