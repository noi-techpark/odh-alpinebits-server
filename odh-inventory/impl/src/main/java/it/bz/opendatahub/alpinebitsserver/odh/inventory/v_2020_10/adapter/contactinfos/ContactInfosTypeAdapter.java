// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.contactinfos;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AddressesType.Address;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ContactInfosType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.EmailsType.Email;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PhonesType.Phone;
import it.bz.opendatahub.alpinebits.xml.schema.ota.URLsType.URL;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link ContactInfosType} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class ContactInfosTypeAdapter {

    private ContactInfosTypeAdapter() {
        // Empty
    }

    /**
     * Remove unsupported (sub) elements and attributes from the {@link ContactInfosType} element.
     *
     * @param contactInfosType This element needs to be cleaned up in order to be AlpineBits 2020-10 compatible.
     */
    public static void removeUnsupported(ContactInfosType contactInfosType) {
        if (contactInfosType == null) {
            return;
        }

        extractContactInfoRootTypes(contactInfosType).forEach(contactInfoRootType -> {
            contactInfoRootType.setCompanyName(null);
            contactInfoRootType.setContactProfileID(null);
            contactInfoRootType.setContactProfileType(null);
            contactInfoRootType.setLastUpdated(null);
            contactInfoRootType.setNames(null);
            contactInfoRootType.setRemoval(null);

            // Cleanup of Address elements
            extractAddresses(contactInfoRootType).forEach(AddressAdapter::removeUnsupported);

            // Cleanup of Email elements
            extractEmails(contactInfoRootType).forEach(EmailAdapter::removeUnsupported);

            // Cleanup of Phone elements
            List<Phone> phones = extractPhones(contactInfoRootType);
            phones.forEach(PhoneAdapter::removeUnsupported);
            phones.forEach(phone -> {
                String phoneNumberWithoutBlanks = phone.getPhoneNumber().replace(" ", "");
                phone.setPhoneNumber(phoneNumberWithoutBlanks);
            });

            // Cleanup of URL elements
            extractURLs(contactInfoRootType).forEach(URLAdapter::removeUnsupported);
        });
    }

    private static List<ContactInfoRootType> extractContactInfoRootTypes(ContactInfosType contactInfosType) {
        return contactInfosType.getContactInfos() != null
                ? contactInfosType.getContactInfos()
                : Collections.emptyList();
    }

    private static List<Address> extractAddresses(ContactInfoRootType contactInfoRootType) {
        return contactInfoRootType.getAddresses() != null
                ? contactInfoRootType.getAddresses().getAddresses()
                : Collections.emptyList();
    }

    private static List<Email> extractEmails(ContactInfoRootType contactInfoRootType) {
        return contactInfoRootType.getEmails() != null
                ? contactInfoRootType.getEmails().getEmails()
                : Collections.emptyList();
    }

    private static List<Phone> extractPhones(ContactInfoRootType contactInfoRootType) {
        return contactInfoRootType.getPhones() != null
                ? contactInfoRootType.getPhones().getPhones()
                : Collections.emptyList();
    }

    private static List<URL> extractURLs(ContactInfoRootType contactInfoRootType) {
        return contactInfoRootType.getURLs() != null
                ? contactInfoRootType.getURLs().getURLS()
                : Collections.emptyList();
    }

}
