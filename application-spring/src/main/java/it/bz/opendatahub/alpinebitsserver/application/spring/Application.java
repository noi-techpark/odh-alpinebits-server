/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.application.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class for Spring Boot application.
 */
@SpringBootApplication
@ComponentScan(basePackages = "it.bz.opendatahub.alpinebitsserver")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}