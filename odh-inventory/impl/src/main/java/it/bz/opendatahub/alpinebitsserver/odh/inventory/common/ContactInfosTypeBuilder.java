/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.URLsType;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accomodation;

import java.util.Optional;

/**
 * Helper class to build {@link ContactInfosType} instances.
 */
public final class ContactInfosTypeBuilder {

    private ContactInfosTypeBuilder() {
        // Empty
    }

    /**
     * Try to extract a {@link ContactInfosType} from the given {@link Accomodation}.
     *
     * @param accomodation The accomodation data is used to extract a ContactInfosType instance.
     * @return An {@link Optional} wrapping the ContactInfosType if such an instance could be
     * extracted from the given accomodation, {@link Optional#empty()} otherwise.
     */
    public static Optional<ContactInfosType> extractContactInfosType(Accomodation accomodation) {
        if (accomodation != null
                && accomodation.getTrustYouId() != null
                && Boolean.TRUE.equals(accomodation.getTrustYouActive())
                && accomodation.getTrustYouState() == 2
        ) {
            URLsType.URL url = new URLsType.URL();
            url.setValue(accomodation.getTrustYouId());
            url.setID("TRUSTYOU");

            URLsType urLsType = new URLsType();
            urLsType.getURLS().add(url);

            ContactInfoRootType contactInfoType = new ContactInfoRootType();
            contactInfoType.setURLs(urLsType);
            contactInfoType.setLocation("6");

            ContactInfosType contactInfosType = new ContactInfosType();
            contactInfosType.getContactInfos().add(contactInfoType);

            return Optional.of(contactInfosType);
        }

        return Optional.empty();
    }

}
