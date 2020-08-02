/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.hotelinfo;

import it.bz.opendatahub.alpinebits.xml.schema.ota.FeaturesType.Feature;

/**
 * This adapter is used to remove elements from {@link Feature} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class FeatureAdapter {

    private FeatureAdapter() {
        // Empty
    }

    public static void removeUnsupported(Feature feature) {
        if (feature == null) {
            return;
        }

        feature.setCharge(null);
        feature.setCodeDetail(null);
        feature.setDescriptiveText(null);
        feature.setExistsCode(null);
        feature.setID(null);
        feature.setMultimediaDescriptions(null);
        feature.setProximityCode(null);
        feature.setRemoval(null);
        feature.setSecurityCode(null);
        feature.setUnitOfMeasure(null);
        feature.setUnitOfMeasureCode(null);
        feature.setUnitOfMeasureQuantity(null);
    }

}
