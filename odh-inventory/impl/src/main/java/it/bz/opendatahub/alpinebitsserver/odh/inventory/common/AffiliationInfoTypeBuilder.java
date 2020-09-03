/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.common;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType.Awards;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType.Awards.Award;
import it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto.Accommodation;

import java.util.Optional;

/**
 * Helper class to build {@link AffiliationInfoType} instances.
 */
public final class AffiliationInfoTypeBuilder {

    private AffiliationInfoTypeBuilder() {
        // Empty
    }

    /**
     * Try to extract a {@link AffiliationInfoType} from the given {@link Accommodation}.
     *
     * @param accommodation The accommodation data is used to extract a AffiliationInfoType instance.
     * @return An {@link Optional} wrapping the AffiliationInfoType if such an instance could be
     * extracted from the given accommodation, {@link Optional#empty()} otherwise.
     */
    public static Optional<AffiliationInfoType> extractHotelInfoType(Accommodation accommodation) {
        if (accommodation == null) {
            return Optional.empty();
        }

        AffiliationInfoType affiliationInfoType = new AffiliationInfoType();

        extractAwards(accommodation).ifPresent(affiliationInfoType::setAwards);

        // Check if any data could be extracted from Accommodation
        // If none was extracted, then the whole AffiliationInfoType should be empty
        if (isAffiliationInfoTypeEmpty(affiliationInfoType)) {
            return Optional.empty();
        }

        return Optional.of(affiliationInfoType);
    }

    private static Optional<Awards> extractAwards(Accommodation accommodation) {
        if (accommodation.getTrustYouId() == null
                || Boolean.FALSE.equals(accommodation.getTrustYouActive())
                || accommodation.getTrustYouScore() == null
                || accommodation.getTrustYouState() != 2
        ) {
            return Optional.empty();
        }

        Award award = new Award();
        award.setProvider("TRUSTYOU");
        award.setRating(accommodation.getTrustYouScore().toString());

        Awards awards = new Awards();
        awards.getAwards().add(award);

        return Optional.of(awards);
    }

    private static boolean isAffiliationInfoTypeEmpty(AffiliationInfoType affiliationInfoType) {
        return affiliationInfoType.getAwards() == null;
    }

}
