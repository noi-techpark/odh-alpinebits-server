// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.CancelPenaltiesType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.CancelPenaltyType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelDescriptiveContentType.Policies;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.CheckoutCharges.CheckoutCharge;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.GuaranteePaymentPolicy;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.PetsPolicies;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.StayRequirements.StayRequirement;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PoliciesType.Policy.TaxPolicies.TaxPolicy;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link Policies} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class PoliciesAdapter {

    private PoliciesAdapter() {
        // Empty
    }

    /**
     * Remove unsupported (sub) elements and attributes from the {@link Policies} element.
     *
     * @param policies This element needs to be cleaned up in order to be AlpineBits 2020-10 compatible.
     */
    public static void removeUnsupported(Policies policies) {
        if (policies == null) {
            return;
        }

        policies.setGuaranteeRoomTypeViaCRC(null);
        policies.setGuaranteeRoomTypeViaGDS(null);
        policies.setGuaranteeRoomTypeViaProperty(null);

        extractPolicies(policies).forEach(policy -> {
            policy.setCode(null);
            policy.setCodeDetail(null);
            policy.setCommissionPolicy(null);
            policy.setDefaultValidBookingMinOffset(null);
            policy.setDuration(null);
            policy.setEnd(null);
            policy.setFeePolicies(null);
            policy.setFri(null);
            policy.setGroupPolicies(null);
            policy.setLastUpdated(null);
            policy.setMon(null);
            policy.setPolicyInfo(null);
            policy.setPolicyInfoCodes(null);
            policy.setRatePolicies(null);
            policy.setRemoval(null);
            policy.setSat(null);
            policy.setStart(null);
            policy.setSun(null);
            policy.setThur(null);
            policy.setTue(null);
            policy.setWeds(null);

            // Cleanup of CancelPolicy element
            if (policy.getCancelPolicy() != null) {
                CancelPenaltiesType cancelPenaltiesType = policy.getCancelPolicy();

                cancelPenaltiesType.setCancelPolicyIndicator(null);

                extractCancelPenaltyTypes(cancelPenaltiesType).forEach(CancelPenaltyTypeAdapter::removeUnsupported);
            }

            // Cleanup of CheckoutCharges elements
            extractCheckoutCharges(policy).forEach(CheckoutChargeAdapter::removeUnsupported);

            // Cleanup of PetsPolicies element
            if (policy.getPetsPolicies() != null) {
                PetsPolicies petsPolicies = policy.getPetsPolicies();

                petsPolicies.setPetsAllowedCode(null);

                extractPetsPolicies(petsPolicies).forEach(PetsPolicyAdapter::removeUnsupported);
            }

            // Cleanup of TaxPolicies elements
            extractTaxPolicies(policy).forEach(TaxPolicyAdapter::removeUnsupported);

            // Cleanup of GuaranteePaymentPolicy element
            if (policy.getGuaranteePaymentPolicy() != null) {
                GuaranteePaymentPolicy guaranteePaymentPolicy = policy.getGuaranteePaymentPolicy();

                guaranteePaymentPolicy.setRemoval(null);

                guaranteePaymentPolicy.getGuaranteePayments().forEach(GuaranteePaymentAdapter::removeUnsupported);
            }

            // Cleanup of StayRequirements elements
            extractStayRequirements(policy).forEach(StayRequirementAdapter::removeUnsupported);
        });
    }

    private static List<Policy> extractPolicies(Policies policies) {
        return policies.getPolicies() != null
                ? policies.getPolicies()
                : Collections.emptyList();
    }

    private static List<CancelPenaltyType> extractCancelPenaltyTypes(CancelPenaltiesType cancelPenaltiesType) {
        return cancelPenaltiesType.getCancelPenalties() != null
                ? cancelPenaltiesType.getCancelPenalties()
                : Collections.emptyList();
    }

    private static List<CheckoutCharge> extractCheckoutCharges(Policy policy) {
        return policy.getCheckoutCharges() != null
                ? policy.getCheckoutCharges().getCheckoutCharges()
                : Collections.emptyList();
    }

    private static List<PetsPolicies.PetsPolicy> extractPetsPolicies(PetsPolicies petsPolicies) {
        return petsPolicies.getPetsPolicies() != null
                ? petsPolicies.getPetsPolicies()
                : Collections.emptyList();
    }

    private static List<TaxPolicy> extractTaxPolicies(Policy policy) {
        return policy.getTaxPolicies() != null
                ? policy.getTaxPolicies().getTaxPolicies()
                : Collections.emptyList();
    }

    private static List<StayRequirement> extractStayRequirements(Policy policy) {
        return policy.getStayRequirements() != null
                ? policy.getStayRequirements().getStayRequirements()
                : Collections.emptyList();
    }
}
