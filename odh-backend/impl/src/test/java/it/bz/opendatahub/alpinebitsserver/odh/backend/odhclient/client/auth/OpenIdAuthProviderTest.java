// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client.auth;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.mockserver.socket.PortFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockserver.model.HttpRequest.request;
import static org.testng.Assert.assertEquals;

/**
 * Tests for {@link OpenIdAuthProvider}.
 */
public class OpenIdAuthProviderTest {

    private ClientAndServer server;
    private String serverUrl;

    @BeforeClass
    private void init() {
        server = ClientAndServer.startClientAndServer(PortFactory.findFreePort());
        serverUrl = "http://localhost:" + server.getPort();
    }

    @AfterClass
    private void destroy() {
        server.stop();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testBuilder_ShouldThrow_WhenAuthUrlIsNull() {
        new OpenIdAuthProvider.Builder(null, "some", "value");
    }

    @Test(expectedExceptions = AuthenticationException.class)
    public void testAuthenticate_ShouldThrow_OnUrlFormatError() {
        OpenIdAuthProvider openIdAuthProvider = new OpenIdAuthProvider.Builder("invalid_url", "some", "value").build();
        openIdAuthProvider.authenticate();
    }

    @Test(expectedExceptions = AuthenticationException.class)
    public void testAuthenticate_ShouldThrow_OnAuthenticationError() {
        server
                .reset()
                .when(request().withMethod("POST"))
                .respond(HttpResponse.response().withStatusCode(401));

        OpenIdAuthProvider openIdAuthProvider = new OpenIdAuthProvider.Builder(serverUrl, "some", "value").build();
        openIdAuthProvider.authenticate();
    }

    @Test
    public void testAuthenticate_ShouldReturnAuthentication() {
        String expectedToken = "123";
        server
                .reset()
                .when(request().withMethod("POST"))
                .respond(HttpResponse
                        .response()
                        .withBody("{\"access_token\":\"" + expectedToken + "\"}")
                        .withHeader("Content-type", "application/json"));

        OpenIdAuthProvider openIdAuthProvider = new OpenIdAuthProvider.Builder(serverUrl, "some", "value").build();
        String token = openIdAuthProvider.authenticate();
        assertEquals(token, expectedToken);
    }
}