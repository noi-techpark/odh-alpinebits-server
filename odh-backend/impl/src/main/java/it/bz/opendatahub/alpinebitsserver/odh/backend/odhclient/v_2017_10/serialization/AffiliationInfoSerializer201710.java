/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory.AffiliationInfoConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo;

import java.io.IOException;

/**
 * JSON serializer for {@link AffiliationInfo}.
 */
public class AffiliationInfoSerializer201710 extends JsonSerializer<AffiliationInfo> {

    private static final AffiliationInfoConverter AFFILIATION_INFO_CONVERTER = AffiliationInfoConverter.newInstance();

    @Override
    public void serialize(AffiliationInfo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
        hotelDescriptiveContent.setAffiliationInfo(value);
        AffiliationInfoType affiliationInfoType = AFFILIATION_INFO_CONVERTER.toAffiliationInfoType(hotelDescriptiveContent);
        gen.writeObject(affiliationInfoType);
    }

}
