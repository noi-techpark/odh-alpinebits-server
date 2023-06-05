// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.inventory.v_2020_10.adapter.policies;

import it.bz.opendatahub.alpinebits.xml.schema.ota.AcceptedPaymentsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.BankAcctType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.EncryptionTokenType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.PaymentCardType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.RequiredPaymentsType.GuaranteePayment;

import java.util.Collections;
import java.util.List;

/**
 * This adapter is used to remove elements from {@link GuaranteePayment} that are not
 * allowed in AlpineBits 2020-10.
 */
public final class GuaranteePaymentAdapter {

    private GuaranteePaymentAdapter() {
        // Empty
    }

    public static void removeUnsupported(GuaranteePayment guaranteePayment) {
        if (guaranteePayment == null) {
            return;
        }

        guaranteePayment.setAddressInd(null);
        guaranteePayment.setAddressRetainInd(null);
        guaranteePayment.setAgencyNameAddrReqInd(null);
        guaranteePayment.setCompanyNameAddrReqInd(null);
        guaranteePayment.setDuration(null);
        guaranteePayment.setEnd(null);
        guaranteePayment.setFri(null);
        guaranteePayment.setGuaranteeCode(null);
        guaranteePayment.setGuaranteeType(null);
        guaranteePayment.setHoldTime(null);
        guaranteePayment.setInfoSource(null);
        guaranteePayment.setInterbankNbrInd(null);
        guaranteePayment.setMon(null);
        guaranteePayment.setNameInd(null);
        guaranteePayment.setNameRetainInd(null);
        guaranteePayment.setNoCardHolderInfoReqInd(null);
        guaranteePayment.setNoCardHolderInfoRetainInd(null);
        guaranteePayment.setNonRefundableIndicator(null);
        guaranteePayment.setPaymentCode(null);
        guaranteePayment.setPolicyCode(null);
        guaranteePayment.setRetributionType(null);
        guaranteePayment.setRoomTypeCode(null);
        guaranteePayment.setSat(null);
        guaranteePayment.setStart(null);
        guaranteePayment.setSun(null);
        guaranteePayment.setThur(null);
        guaranteePayment.setTPAExtensions(null);
        guaranteePayment.setTue(null);
        guaranteePayment.setType(null);
        guaranteePayment.setWeds(null);

        extractAcceptedPayments(guaranteePayment).forEach(acceptedPayment -> {
            acceptedPayment.setCostCenterID(null);
            acceptedPayment.setDirectBill(null);
            acceptedPayment.setGuaranteeID(null);
            acceptedPayment.setGuaranteeIndicator(null);
            acceptedPayment.setGuaranteeTypeCode(null);
            acceptedPayment.setLoyaltyRedemption(null);
            acceptedPayment.setMiscChargeOrder(null);
            acceptedPayment.setPaymentTransactionTypeCode(null);
            acceptedPayment.setRemark(null);
            acceptedPayment.setRPH(null);
            acceptedPayment.setShareMarketInd(null);
            acceptedPayment.setShareSynchInd(null);
            acceptedPayment.setTicket(null);
            acceptedPayment.setTPAExtensions(null);
            acceptedPayment.setVoucher(null);

            if (acceptedPayment.getBankAcct() != null) {
                BankAcctType bankAcctType = acceptedPayment.getBankAcct();

                bankAcctType.setCheckNumber(null);
                bankAcctType.setChecksAcceptedInd(null);
                bankAcctType.setShareMarketInd(null);
                bankAcctType.setShareSynchInd(null);
                bankAcctType.setType(null);

                if (bankAcctType.getBankAcctNumber() != null) {
                    EncryptionTokenType bankAcctNumber = bankAcctType.getBankAcctNumber();
                    bankAcctNumber.setAuthenticationMethod(null);
                    bankAcctNumber.setEncryptedValue(null);
                    bankAcctNumber.setEncryptionKey(null);
                    bankAcctNumber.setEncryptionKeyMethod(null);
                    bankAcctNumber.setEncryptionMethod(null);
                    bankAcctNumber.setMask(null);
                    bankAcctNumber.setToken(null);
                    bankAcctNumber.setTokenProviderID(null);
                    bankAcctNumber.setTPAExtensions(null);
                    bankAcctNumber.setWarning(null);
                }
            }

            if (acceptedPayment.getPaymentCard() != null) {
                PaymentCardType paymentCard = acceptedPayment.getPaymentCard();
                paymentCard.setAddress(null);
                paymentCard.setCardHolderName(null);
                paymentCard.setCardHolderNameDetails(null);
                paymentCard.setCardHolderRPH(null);
                paymentCard.setCardNumber(null);
                paymentCard.setCardType(null);
                paymentCard.setCompanyCardReference(null);
                paymentCard.setCountryOfIssue(null);
                paymentCard.setEffectiveDate(null);
                paymentCard.setExpireDate(null);
                paymentCard.setExtendedPaymentInd(null);
                paymentCard.setIssuer(null);
                paymentCard.setRemark(null);
                paymentCard.setRPH(null);
                paymentCard.setSecureInd(null);
                paymentCard.setSeriesCode(null);
                paymentCard.setShareMarketInd(null);
                paymentCard.setShareSynchInd(null);
                paymentCard.setSignatureOnFile(null);
                paymentCard.setSignatureOnFileInd(null);
                paymentCard.setThreeDomainSecurity(null);
                paymentCard.setTPAExtensions(null);
            }
        });

        if (guaranteePayment.getAmountPercent() != null) {
            GuaranteePayment.AmountPercent amountPercent = guaranteePayment.getAmountPercent();

            amountPercent.setAmount(null);
            amountPercent.setApplyAs(null);
            amountPercent.setBasisType(null);
            amountPercent.setCurrencyCode(null);
            amountPercent.setDecimalPlaces(null);
            amountPercent.setFeesInclusive(null);
            amountPercent.setNmbrOfNights(null);
            amountPercent.setOverriddenAmountIndicator(null);
            amountPercent.setTaxes(null);
            amountPercent.setTaxInclusive(null);
        }

        extractDeadlines(guaranteePayment).forEach(deadline -> {
            deadline.setAbsoluteDeadline(null);
            deadline.setOverrideIndicator(null);
        });
    }

    private static List<AcceptedPaymentsType.AcceptedPayment> extractAcceptedPayments(GuaranteePayment guaranteePayment) {
        return guaranteePayment.getAcceptedPayments() != null
                ? guaranteePayment.getAcceptedPayments().getAcceptedPayments()
                : Collections.emptyList();
    }

    private static List<GuaranteePayment.Deadline> extractDeadlines(GuaranteePayment guaranteePayment) {
        return guaranteePayment.getDeadlines() != null
                ? guaranteePayment.getDeadlines()
                : Collections.emptyList();
    }
}
