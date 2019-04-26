/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.billing.v2018_11_01_preview;

import rx.Observable;
import com.microsoft.azure.management.billing.v2018_11_01_preview.implementation.BillingProfilesInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing BillingProfiles.
 */
public interface BillingProfiles extends HasInner<BillingProfilesInner> {
    /**
     * Lists all billing profiles for a user which that user has access to.
     *
     * @param billingAccountName billing Account Id.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<BillingProfileListResult> listByBillingAccountNameAsync(String billingAccountName);

    /**
     * Get the billing profile by id.
     *
     * @param billingAccountName billing Account Id.
     * @param billingProfileName Billing Profile Id.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<BillingProfile> getAsync(String billingAccountName, String billingProfileName);

}
