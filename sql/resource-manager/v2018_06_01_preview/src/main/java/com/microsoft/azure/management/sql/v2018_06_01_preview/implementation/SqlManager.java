/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.v2018_06_01_preview.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.arm.resources.AzureConfigurable;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;
import com.microsoft.azure.management.sql.v2018_06_01_preview.DatabaseSecurityAlertPolicies;
import com.microsoft.azure.management.sql.v2018_06_01_preview.ManagedDatabaseSensitivityLabels;
import com.microsoft.azure.management.sql.v2018_06_01_preview.ManagedInstanceVulnerabilityAssessments;
import com.microsoft.azure.management.sql.v2018_06_01_preview.ServerVulnerabilityAssessments;
import com.microsoft.azure.arm.resources.implementation.AzureConfigurableCoreImpl;
import com.microsoft.azure.arm.resources.implementation.ManagerCore;

/**
 * Entry point to Azure Sql resource management.
 */
public final class SqlManager extends ManagerCore<SqlManager, SqlManagementClientImpl> {
    private DatabaseSecurityAlertPolicies databaseSecurityAlertPolicies;
    private ManagedDatabaseSensitivityLabels managedDatabaseSensitivityLabels;
    private ManagedInstanceVulnerabilityAssessments managedInstanceVulnerabilityAssessments;
    private ServerVulnerabilityAssessments serverVulnerabilityAssessments;
    /**
    * Get a Configurable instance that can be used to create SqlManager with optional configuration.
    *
    * @return the instance allowing configurations
    */
    public static Configurable configure() {
        return new SqlManager.ConfigurableImpl();
    }
    /**
    * Creates an instance of SqlManager that exposes Sql resource management API entry points.
    *
    * @param credentials the credentials to use
    * @param subscriptionId the subscription UUID
    * @return the SqlManager
    */
    public static SqlManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new SqlManager(new RestClient.Builder()
            .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
            .withCredentials(credentials)
            .withSerializerAdapter(new AzureJacksonAdapter())
            .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
            .build(), subscriptionId);
    }
    /**
    * Creates an instance of SqlManager that exposes Sql resource management API entry points.
    *
    * @param restClient the RestClient to be used for API calls.
    * @param subscriptionId the subscription UUID
    * @return the SqlManager
    */
    public static SqlManager authenticate(RestClient restClient, String subscriptionId) {
        return new SqlManager(restClient, subscriptionId);
    }
    /**
    * The interface allowing configurations to be set.
    */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
        * Creates an instance of SqlManager that exposes Sql management API entry points.
        *
        * @param credentials the credentials to use
        * @param subscriptionId the subscription UUID
        * @return the interface exposing Sql management API entry points that work across subscriptions
        */
        SqlManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * @return Entry point to manage DatabaseSecurityAlertPolicies.
     */
    public DatabaseSecurityAlertPolicies databaseSecurityAlertPolicies() {
        if (this.databaseSecurityAlertPolicies == null) {
            this.databaseSecurityAlertPolicies = new DatabaseSecurityAlertPoliciesImpl(this);
        }
        return this.databaseSecurityAlertPolicies;
    }

    /**
     * @return Entry point to manage ManagedDatabaseSensitivityLabels.
     */
    public ManagedDatabaseSensitivityLabels managedDatabaseSensitivityLabels() {
        if (this.managedDatabaseSensitivityLabels == null) {
            this.managedDatabaseSensitivityLabels = new ManagedDatabaseSensitivityLabelsImpl(this);
        }
        return this.managedDatabaseSensitivityLabels;
    }

    /**
     * @return Entry point to manage ManagedInstanceVulnerabilityAssessments.
     */
    public ManagedInstanceVulnerabilityAssessments managedInstanceVulnerabilityAssessments() {
        if (this.managedInstanceVulnerabilityAssessments == null) {
            this.managedInstanceVulnerabilityAssessments = new ManagedInstanceVulnerabilityAssessmentsImpl(this);
        }
        return this.managedInstanceVulnerabilityAssessments;
    }

    /**
     * @return Entry point to manage ServerVulnerabilityAssessments.
     */
    public ServerVulnerabilityAssessments serverVulnerabilityAssessments() {
        if (this.serverVulnerabilityAssessments == null) {
            this.serverVulnerabilityAssessments = new ServerVulnerabilityAssessmentsImpl(this);
        }
        return this.serverVulnerabilityAssessments;
    }

    /**
    * The implementation for Configurable interface.
    */
    private static final class ConfigurableImpl extends AzureConfigurableCoreImpl<Configurable> implements Configurable {
        public SqlManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
           return SqlManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
     }
    private SqlManager(RestClient restClient, String subscriptionId) {
        super(
            restClient,
            subscriptionId,
            new SqlManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
    }
}
