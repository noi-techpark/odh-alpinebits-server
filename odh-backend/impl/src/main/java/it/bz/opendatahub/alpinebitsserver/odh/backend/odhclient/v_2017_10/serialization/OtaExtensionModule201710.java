/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2017_10.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies;

/**
 * Class that registers capability of serializing OTA extensions for AlpineBits 2017-10
 * with the Jackson core.
 */
public final class OtaExtensionModule201710 extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public OtaExtensionModule201710() {
        super(PackageVersion.VERSION);

        addSerializer(AffiliationInfo.class, new AffiliationInfoSerializer201710());
        addSerializer(ContactInfos.class, new ContactInfosSerializer201710());
        addSerializer(HotelInfo.class, new HotelInfoSerializer201710());
        addSerializer(Policies.class, new PoliciesSerializer201710());

        addDeserializer(AffiliationInfo.class, new AffiliationInfoDeserializer201710());
        addDeserializer(ContactInfos.class, new ContactInfosDeserializer201710());
        addDeserializer(HotelInfo.class, new HotelInfoDeserializer201710());
        addDeserializer(Policies.class, new PoliciesDeserializer201710());
    }

}
