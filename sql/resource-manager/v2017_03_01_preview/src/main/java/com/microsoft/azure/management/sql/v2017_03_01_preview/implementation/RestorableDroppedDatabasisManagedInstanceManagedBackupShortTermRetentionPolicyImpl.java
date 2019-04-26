/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.v2017_03_01_preview.implementation;

import com.microsoft.azure.management.sql.v2017_03_01_preview.RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy;
import com.microsoft.azure.arm.model.implementation.CreatableUpdatableImpl;
import rx.Observable;

class RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl extends CreatableUpdatableImpl<RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy, ManagedBackupShortTermRetentionPolicyInner, RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl> implements RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy, RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy.Definition, RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy.Update {
    private final SqlManager manager;
    private String resourceGroupName;
    private String managedInstanceName;
    private String restorableDroppedDatabaseId;
    private Integer cretentionDays;
    private Integer uretentionDays;

    RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl(String name, SqlManager manager) {
        super(name, new ManagedBackupShortTermRetentionPolicyInner());
        this.manager = manager;
        // Set resource name
        this.restorableDroppedDatabaseId = name;
        //
    }

    RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl(ManagedBackupShortTermRetentionPolicyInner inner, SqlManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.restorableDroppedDatabaseId = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.managedInstanceName = IdParsingUtils.getValueFromIdByName(inner.id(), "managedInstances");
        this.restorableDroppedDatabaseId = IdParsingUtils.getValueFromIdByName(inner.id(), "restorableDroppedDatabases");
        //
    }

    @Override
    public SqlManager manager() {
        return this.manager;
    }

    @Override
    public Observable<RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy> createResourceAsync() {
        ManagedRestorableDroppedDatabaseBackupShortTermRetentionPoliciesInner client = this.manager().inner().managedRestorableDroppedDatabaseBackupShortTermRetentionPolicies();
        return client.createOrUpdateAsync(this.resourceGroupName, this.managedInstanceName, this.restorableDroppedDatabaseId, this.cretentionDays)
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicy> updateResourceAsync() {
        ManagedRestorableDroppedDatabaseBackupShortTermRetentionPoliciesInner client = this.manager().inner().managedRestorableDroppedDatabaseBackupShortTermRetentionPolicies();
        return client.updateAsync(this.resourceGroupName, this.managedInstanceName, this.restorableDroppedDatabaseId, this.uretentionDays)
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ManagedBackupShortTermRetentionPolicyInner> getInnerAsync() {
        ManagedRestorableDroppedDatabaseBackupShortTermRetentionPoliciesInner client = this.manager().inner().managedRestorableDroppedDatabaseBackupShortTermRetentionPolicies();
        return client.getAsync(this.resourceGroupName, this.managedInstanceName, this.restorableDroppedDatabaseId);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public Integer retentionDays() {
        return this.inner().retentionDays();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl withExistingRestorableDroppedDatabasis(String resourceGroupName, String managedInstanceName, String restorableDroppedDatabaseId) {
        this.resourceGroupName = resourceGroupName;
        this.managedInstanceName = managedInstanceName;
        this.restorableDroppedDatabaseId = restorableDroppedDatabaseId;
        return this;
    }

    @Override
    public RestorableDroppedDatabasisManagedInstanceManagedBackupShortTermRetentionPolicyImpl withRetentionDays(Integer retentionDays) {
        if (isInCreateMode()) {
            this.cretentionDays = retentionDays;
        } else {
            this.uretentionDays = retentionDays;
        }
        return this;
    }

}
