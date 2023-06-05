// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Open Data Hub "Accommodation" AccoDetail element.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccoDetail {

    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Zip")
    private String zip;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("Street")
    private String street;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Lastname")
    private String lastname;

    @JsonProperty("Longdesc")
    private String longdesc;

    @JsonProperty("Firstname")
    private String firstname;

    @JsonProperty("Shortdesc")
    private String shortdesc;

    @JsonProperty("CountryCode")
    private String countryCode;

    @JsonProperty("NameAddition")
    private String nameAddition;

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNameAddition() {
        return nameAddition;
    }

    public void setNameAddition(String nameAddition) {
        this.nameAddition = nameAddition;
    }

    @Override
    public String toString() {
        return "AccoDetail{" +
                "fax='" + fax + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", street='" + street + '\'' +
                ", website='" + website + '\'' +
                ", language='" + language + '\'' +
                ", lastname='" + lastname + '\'' +
                ", longdesc='" + longdesc + '\'' +
                ", firstname='" + firstname + '\'' +
                ", shortdesc='" + shortdesc + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", nameAddition='" + nameAddition + '\'' +
                '}';
    }
}
