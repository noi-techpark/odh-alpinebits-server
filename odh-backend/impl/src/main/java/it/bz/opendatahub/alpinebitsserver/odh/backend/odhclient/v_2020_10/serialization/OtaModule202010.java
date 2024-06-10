// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.v_2020_10.serialization;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.xml.bind.JAXBElement;

/**
 * Custom Jackson serializer / deserializer module for ODH support.
 */
// TODO: rename package?
public class OtaModule202010 extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public OtaModule202010() {
        super(PackageVersion.VERSION);

        // Register serializers
        addSerializer(JAXBElement.class, new JAXBElementSerializer());

        // Register deserializers
        addDeserializer(JAXBElement.class, new JAXBElementDeserializer());
    }
}
