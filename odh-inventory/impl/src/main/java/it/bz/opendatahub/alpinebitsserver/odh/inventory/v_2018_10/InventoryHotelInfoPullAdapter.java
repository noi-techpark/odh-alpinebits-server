/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2018_10;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.URLsType;

import java.util.Optional;

/**
 * This middleware removes elements and attributes that are not allowed in AlpineBits 2018-10
 * Inventory/HotelInfo action.
 * <p>
 * ODH as persisting backend saves AlpineBits inventory data for different AlpineBits versions.
 * Those versions may allow elements and attributes that are not allowed in AlpineBits 2018-10.
 * Therefor it is necessary to adapt the response data for AlpineBits 2018-10 before it is returned.
 * Otherwise, the XML validation may fail.
 * <p>
 * An example for the problem described is the definition of the <code>ContactInfos</code> element,
 * that changed between AlpineBits 2017-10 and 2018-10 versions: in 2017-10, the <code>ContactInfos</code>
 * element was allowed to contain all elements and attributes defined in OTA-2015A. In AlpineBits 2018-10
 * the usage was restricted to a subset of the allowed elements.
 * <p>
 * Please note, that this middleware only removes data to establish compatibility with AlpineBits 2018-10.
 * It does not provide missing data, because that is not possible most of the time. For example, if some
 * required element or attribute is missing (because that is allowed in a different AlpineBits version),
 * there is no way to build the missing data. An example for that problem is the <code>ID</code> attribute
 * on the <code>URL</code> element inside <code>ContactInfo</code>: AlpineBits 2017-10 doesn't require
 * the <code>URL</code> element to be present and if it is present, the case (upper/lower) of its value
 * doesn't matter. In contrast to that, AlpineBits 2018-10 requires the <code>ID</code> attribute on the
 * <code>URL</code> element to be present and its value must be all uppercase. It is easy to see, that
 * there is no way to convert an AlpineBits 2017-10 message without <code>ID</code> attribute to an
 * AlpineBits 2018-10 message.
 */
public final class InventoryHotelInfoPullAdapter implements Middleware {

    private static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // This middleware is invoked upstreams only (in response phase)
        // Therefor the downstream chain (request phase) is invoked first
        chain.next();

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = ctx.getOrThrow(OTA_INVENTORY_PULL_RESPONSE);

        extractContactInfoRootType(otaHotelDescriptiveInfoRS).ifPresent(contactInfoRootType -> {
            // Remove all elements / attributes except "URLs" element and "Location" attribute
            contactInfoRootType.setAddresses(null);
            contactInfoRootType.setCompanyName(null);
            contactInfoRootType.setContactProfileID(null);
            contactInfoRootType.setContactProfileType(null);
            contactInfoRootType.setEmails(null);
            contactInfoRootType.setLastUpdated(null);
            contactInfoRootType.setNames(null);
            contactInfoRootType.setPhones(null);
            contactInfoRootType.setRemoval(null);

            extractURLsType(contactInfoRootType).ifPresent(url -> {
                url.setDefaultInd(null);
                url.setType(null);
            });
        });

    }

    private Optional<ContactInfoRootType> extractContactInfoRootType(OTAHotelDescriptiveInfoRS ota) {
        if (ota.getHotelDescriptiveContents() != null
                && !ota.getHotelDescriptiveContents().getHotelDescriptiveContents().isEmpty()
                && ota.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).getContactInfos() != null
                && !ota.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).getContactInfos().getContactInfos().isEmpty()
        ) {
            return Optional.of(ota.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0).getContactInfos().getContactInfos().get(0));
        }
        return Optional.empty();
    }

    private Optional<URLsType.URL> extractURLsType(ContactInfoRootType contactInfoRootType) {
        if (contactInfoRootType.getURLs() != null
                && !contactInfoRootType.getURLs().getURLS().isEmpty()
        ) {
            return Optional.of(contactInfoRootType.getURLs().getURLS().get(0));
        }
        return Optional.empty();
    }

}
