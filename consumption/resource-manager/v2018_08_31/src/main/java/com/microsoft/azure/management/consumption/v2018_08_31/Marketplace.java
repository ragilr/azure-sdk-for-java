/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.consumption.v2018_08_31;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.consumption.v2018_08_31.implementation.ConsumptionManager;
import com.microsoft.azure.management.consumption.v2018_08_31.implementation.MarketplaceInner;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Map;
import org.joda.time.DateTime;

/**
 * Type representing Marketplace.
 */
public interface Marketplace extends HasInner<MarketplaceInner>, HasManager<ConsumptionManager> {
    /**
     * @return the accountName value.
     */
    String accountName();

    /**
     * @return the additionalProperties value.
     */
    String additionalProperties();

    /**
     * @return the billingPeriodId value.
     */
    String billingPeriodId();

    /**
     * @return the consumedQuantity value.
     */
    BigDecimal consumedQuantity();

    /**
     * @return the consumedService value.
     */
    String consumedService();

    /**
     * @return the costCenter value.
     */
    String costCenter();

    /**
     * @return the currency value.
     */
    String currency();

    /**
     * @return the departmentName value.
     */
    String departmentName();

    /**
     * @return the id value.
     */
    String id();

    /**
     * @return the instanceId value.
     */
    String instanceId();

    /**
     * @return the instanceName value.
     */
    String instanceName();

    /**
     * @return the isEstimated value.
     */
    Boolean isEstimated();

    /**
     * @return the isRecurringCharge value.
     */
    Boolean isRecurringCharge();

    /**
     * @return the meterId value.
     */
    UUID meterId();

    /**
     * @return the name value.
     */
    String name();

    /**
     * @return the offerName value.
     */
    String offerName();

    /**
     * @return the orderNumber value.
     */
    String orderNumber();

    /**
     * @return the planName value.
     */
    String planName();

    /**
     * @return the pretaxCost value.
     */
    BigDecimal pretaxCost();

    /**
     * @return the publisherName value.
     */
    String publisherName();

    /**
     * @return the resourceGroup value.
     */
    String resourceGroup();

    /**
     * @return the resourceRate value.
     */
    BigDecimal resourceRate();

    /**
     * @return the subscriptionGuid value.
     */
    UUID subscriptionGuid();

    /**
     * @return the subscriptionName value.
     */
    String subscriptionName();

    /**
     * @return the tags value.
     */
    Map<String, String> tags();

    /**
     * @return the type value.
     */
    String type();

    /**
     * @return the unitOfMeasure value.
     */
    String unitOfMeasure();

    /**
     * @return the usageEnd value.
     */
    DateTime usageEnd();

    /**
     * @return the usageStart value.
     */
    DateTime usageStart();

}
