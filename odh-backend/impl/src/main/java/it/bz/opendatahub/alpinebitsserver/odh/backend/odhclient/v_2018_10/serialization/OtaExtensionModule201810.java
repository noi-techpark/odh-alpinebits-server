/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies;

/**
 * Class that registers capability of serializing OTA extensions for AlpineBits 2018-10
 * with the Jackson core.
 */
public final class OtaExtensionModule201810 extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public OtaExtensionModule201810() {
        super(PackageVersion.VERSION);

        addSerializer(AffiliationInfo.class, new AffiliationInfoSerializer201810());
        addSerializer(ContactInfos.class, new ContactInfosSerializer201810());
        addSerializer(HotelInfo.class, new HotelInfoSerializer201810());
        addSerializer(Policies.class, new PoliciesSerializer201810());

        addDeserializer(AffiliationInfo.class, new AffiliationInfoDeserializer201810());
        addDeserializer(ContactInfos.class, new ContactInfosDeserializer201810());
        addDeserializer(HotelInfo.class, new HotelInfoDeserializer201810());
        addDeserializer(Policies.class, new PoliciesDeserializer201810());
    }

}
