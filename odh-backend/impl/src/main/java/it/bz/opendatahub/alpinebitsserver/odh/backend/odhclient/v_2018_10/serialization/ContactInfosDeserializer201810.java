/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2018_10.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos.ContactInfo;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos.ContactInfo.URLs.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * JSON deserializer for {@link ContactInfos}.
 */
public class ContactInfosDeserializer201810 extends JsonDeserializer<ContactInfos> {

    private static final Logger LOG = LoggerFactory.getLogger(ContactInfosDeserializer201810.class);
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("^([A-Z]+)$");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JaxbAnnotationModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ContactInfos deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        JsonNode contactInfoNode = node.get("ContactInfo");

        if (contactInfoNode != null && contactInfoNode.isArray()) {
            ContactInfo contactInfo = MAPPER.treeToValue(contactInfoNode.get(0), ContactInfo.class);
            return buildContactInfos(contactInfo);
        }

        return null;
    }

    private ContactInfos buildContactInfos(ContactInfo contactInfo) {
        if (contactInfo == null) {
            logContactInfoInvalidMessage("ContactInfo element is null");
            return null;
        }
        if (contactInfo.getLocation() == null) {
            logContactInfoInvalidMessage("ContactInfo->Location attribute is null");
            return null;
        }
        if (!"6".equals(contactInfo.getLocation())) {
            logContactInfoInvalidMessage("ContactInfo->Location attribute is not equal to \"6\"");
            return null;
        }
        if (contactInfo.getURLs() == null) {
            logContactInfoInvalidMessage("ContactInfo->URLs element is null");
            return null;
        }
        if (contactInfo.getURLs().getURLS().isEmpty()) {
            logContactInfoInvalidMessage("ContactInfo->URLs element is empty");
            return null;
        }

        List<URL> validUrls = filterUrls(contactInfo);

        if (validUrls.isEmpty()) {
            logContactInfoInvalidMessage("ContactInfo->URLs element is empty after URL elements have been filtered");
            return null;
        }

        ContactInfo.URLs urls = new ContactInfo.URLs();
        urls.getURLS().addAll(validUrls);

        ContactInfo validContactInfo = new ContactInfo();
        validContactInfo.setURLs(urls);
        validContactInfo.setLocation(contactInfo.getLocation());

        ContactInfos contactInfos = new ContactInfos();
        contactInfos.setContactInfo(validContactInfo);
        return contactInfos;
    }

    private List<URL> filterUrls(ContactInfo contactInfo) {
        List<URL> validUrls = new ArrayList<>();

        for (int i = 0; i < contactInfo.getURLs().getURLS().size(); i++) {
            URL url = contactInfo.getURLs().getURLS().get(i);

            if (url.getValue() == null) {
                logUrlInvalidMessage(i, "Value is null");
            } else if (url.getID() == null) {
                logUrlInvalidMessage(i, "ID attibute is null");
            } else if (!UPPERCASE_PATTERN.matcher(url.getID()).find()) {
                logUrlInvalidMessage(i, "ID attibute doesn't match the regexp " + UPPERCASE_PATTERN.pattern());
            } else {
                validUrls.add(url);
            }
        }
        return validUrls;
    }

    private void logContactInfoInvalidMessage(String reason) {
        LOG.debug("ContactInfo element read from ODH is not valid in respect to AlpineBits 2018-10 and therefor not part of the result (reason: {})", reason);
    }

    private void logUrlInvalidMessage(int index, String reason) {
        LOG.debug(
                "ContactInfo->URLS->URL[{}] element read from ODH is not valid in respect to AlpineBits 2018-10 and therefor not part of the result " +
                        "(reason: {})",
                index,
                reason
        );
    }
}
