/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.cognitiveservices.vision.computervision.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An entity observation in the image, along with the confidence score.
 */
public class ImageTag {
    /**
     * Name of the entity.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * The level of confidence that the entity was observed.
     */
    @JsonProperty(value = "confidence")
    private double confidence;

    /**
     * Optional hint/details for this tag.
     */
    @JsonProperty(value = "hint")
    private String hint;

    /**
     * Get name of the entity.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set name of the entity.
     *
     * @param name the name value to set
     * @return the ImageTag object itself.
     */
    public ImageTag withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the level of confidence that the entity was observed.
     *
     * @return the confidence value
     */
    public double confidence() {
        return this.confidence;
    }

    /**
     * Set the level of confidence that the entity was observed.
     *
     * @param confidence the confidence value to set
     * @return the ImageTag object itself.
     */
    public ImageTag withConfidence(double confidence) {
        this.confidence = confidence;
        return this;
    }

    /**
     * Get optional hint/details for this tag.
     *
     * @return the hint value
     */
    public String hint() {
        return this.hint;
    }

    /**
     * Set optional hint/details for this tag.
     *
     * @param hint the hint value to set
     * @return the ImageTag object itself.
     */
    public ImageTag withHint(String hint) {
        this.hint = hint;
        return this;
    }

}
