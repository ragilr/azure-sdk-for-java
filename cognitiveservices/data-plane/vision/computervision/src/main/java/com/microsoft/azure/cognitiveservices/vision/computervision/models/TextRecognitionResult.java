/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.cognitiveservices.vision.computervision.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json object representing a recognized text region.
 */
public class TextRecognitionResult {
    /**
     * The 1-based page number of the recognition result.
     */
    @JsonProperty(value = "page")
    private Integer page;

    /**
     * The orientation of the image in degrees in the clockwise direction.
     * Range between [0, 360).
     */
    @JsonProperty(value = "clockwiseOrientation")
    private Double clockwiseOrientation;

    /**
     * The width of the image in pixels or the PDF in inches.
     */
    @JsonProperty(value = "width")
    private Double width;

    /**
     * The height of the image in pixels or the PDF in inches.
     */
    @JsonProperty(value = "height")
    private Double height;

    /**
     * The unit used in the Width, Height and BoundingBox. For images, the unit
     * is "pixel". For PDF, the unit is "inch". Possible values include:
     * 'pixel', 'inch'.
     */
    @JsonProperty(value = "unit")
    private TextRecognitionResultDimensionUnit unit;

    /**
     * A list of recognized text lines.
     */
    @JsonProperty(value = "lines", required = true)
    private List<Line> lines;

    /**
     * Get the 1-based page number of the recognition result.
     *
     * @return the page value
     */
    public Integer page() {
        return this.page;
    }

    /**
     * Set the 1-based page number of the recognition result.
     *
     * @param page the page value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withPage(Integer page) {
        this.page = page;
        return this;
    }

    /**
     * Get the orientation of the image in degrees in the clockwise direction. Range between [0, 360).
     *
     * @return the clockwiseOrientation value
     */
    public Double clockwiseOrientation() {
        return this.clockwiseOrientation;
    }

    /**
     * Set the orientation of the image in degrees in the clockwise direction. Range between [0, 360).
     *
     * @param clockwiseOrientation the clockwiseOrientation value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withClockwiseOrientation(Double clockwiseOrientation) {
        this.clockwiseOrientation = clockwiseOrientation;
        return this;
    }

    /**
     * Get the width of the image in pixels or the PDF in inches.
     *
     * @return the width value
     */
    public Double width() {
        return this.width;
    }

    /**
     * Set the width of the image in pixels or the PDF in inches.
     *
     * @param width the width value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withWidth(Double width) {
        this.width = width;
        return this;
    }

    /**
     * Get the height of the image in pixels or the PDF in inches.
     *
     * @return the height value
     */
    public Double height() {
        return this.height;
    }

    /**
     * Set the height of the image in pixels or the PDF in inches.
     *
     * @param height the height value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withHeight(Double height) {
        this.height = height;
        return this;
    }

    /**
     * Get the unit used in the Width, Height and BoundingBox. For images, the unit is "pixel". For PDF, the unit is "inch". Possible values include: 'pixel', 'inch'.
     *
     * @return the unit value
     */
    public TextRecognitionResultDimensionUnit unit() {
        return this.unit;
    }

    /**
     * Set the unit used in the Width, Height and BoundingBox. For images, the unit is "pixel". For PDF, the unit is "inch". Possible values include: 'pixel', 'inch'.
     *
     * @param unit the unit value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withUnit(TextRecognitionResultDimensionUnit unit) {
        this.unit = unit;
        return this;
    }

    /**
     * Get a list of recognized text lines.
     *
     * @return the lines value
     */
    public List<Line> lines() {
        return this.lines;
    }

    /**
     * Set a list of recognized text lines.
     *
     * @param lines the lines value to set
     * @return the TextRecognitionResult object itself.
     */
    public TextRecognitionResult withLines(List<Line> lines) {
        this.lines = lines;
        return this;
    }

}
