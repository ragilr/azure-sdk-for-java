/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devspaces.v2018_06_01_preview;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.Resource;
import com.microsoft.azure.arm.resources.models.GroupableResourceCore;
import com.microsoft.azure.arm.resources.models.HasResourceGroup;
import com.microsoft.azure.arm.model.Refreshable;
import com.microsoft.azure.arm.model.Updatable;
import com.microsoft.azure.arm.model.Appliable;
import com.microsoft.azure.arm.model.Creatable;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.devspaces.v2018_06_01_preview.implementation.DevSpacesManager;
import com.microsoft.azure.management.devspaces.v2018_06_01_preview.implementation.ControllerInner;

/**
 * Type representing Controller.
 */
public interface Controller extends HasInner<ControllerInner>, Resource, GroupableResourceCore<DevSpacesManager, ControllerInner>, HasResourceGroup, Refreshable<Controller>, Updatable<Controller.Update>, HasManager<DevSpacesManager> {
    /**
     * @return the dataPlaneFqdn value.
     */
    String dataPlaneFqdn();

    /**
     * @return the hostSuffix value.
     */
    String hostSuffix();

    /**
     * @return the provisioningState value.
     */
    ProvisioningState provisioningState();

    /**
     * @return the sku value.
     */
    Sku sku();

    /**
     * @return the targetContainerHostCredentialsBase64 value.
     */
    String targetContainerHostCredentialsBase64();

    /**
     * @return the targetContainerHostResourceId value.
     */
    String targetContainerHostResourceId();

    /**
     * The entirety of the Controller definition.
     */
    interface Definition extends DefinitionStages.Blank, DefinitionStages.WithGroup, DefinitionStages.WithHostSuffix, DefinitionStages.WithSku, DefinitionStages.WithTargetContainerHostCredentialsBase64, DefinitionStages.WithTargetContainerHostResourceId, DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Controller definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Controller definition.
         */
        interface Blank extends GroupableResourceCore.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the Controller definition allowing to specify the resource group.
         */
        interface WithGroup extends GroupableResourceCore.DefinitionStages.WithGroup<WithHostSuffix> {
        }

        /**
         * The stage of the controller definition allowing to specify HostSuffix.
         */
        interface WithHostSuffix {
           /**
            * Specifies hostSuffix.
            * @param hostSuffix DNS suffix for public endpoints running in the Azure Dev Spaces Controller
            * @return the next definition stage
*/
            WithSku withHostSuffix(String hostSuffix);
        }

        /**
         * The stage of the controller definition allowing to specify Sku.
         */
        interface WithSku {
           /**
            * Specifies sku.
            * @param sku the sku parameter value
            * @return the next definition stage
*/
            WithTargetContainerHostCredentialsBase64 withSku(Sku sku);
        }

        /**
         * The stage of the controller definition allowing to specify TargetContainerHostCredentialsBase64.
         */
        interface WithTargetContainerHostCredentialsBase64 {
           /**
            * Specifies targetContainerHostCredentialsBase64.
            * @param targetContainerHostCredentialsBase64 Credentials of the target container host (base64)
            * @return the next definition stage
*/
            WithTargetContainerHostResourceId withTargetContainerHostCredentialsBase64(String targetContainerHostCredentialsBase64);
        }

        /**
         * The stage of the controller definition allowing to specify TargetContainerHostResourceId.
         */
        interface WithTargetContainerHostResourceId {
           /**
            * Specifies targetContainerHostResourceId.
            * @param targetContainerHostResourceId Resource ID of the target container host
            * @return the next definition stage
*/
            WithCreate withTargetContainerHostResourceId(String targetContainerHostResourceId);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        interface WithCreate extends Creatable<Controller>, Resource.DefinitionWithTags<WithCreate> {
        }
    }
    /**
     * The template for a Controller update operation, containing all the settings that can be modified.
     */
    interface Update extends Appliable<Controller>, Resource.UpdateWithTags<Update> {
    }

    /**
     * Grouping of Controller update stages.
     */
    interface UpdateStages {
    }
}
