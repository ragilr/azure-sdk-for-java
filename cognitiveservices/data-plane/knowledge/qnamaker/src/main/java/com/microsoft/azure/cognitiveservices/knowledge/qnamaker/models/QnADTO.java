/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.cognitiveservices.knowledge.qnamaker.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Q-A object.
 */
public class QnADTO {
    /**
     * Unique id for the Q-A.
     */
    @JsonProperty(value = "id")
    private Integer id;

    /**
     * Answer text.
     */
    @JsonProperty(value = "answer", required = true)
    private String answer;

    /**
     * Source from which Q-A was indexed. eg.
     * https://docs.microsoft.com/en-us/azure/cognitive-services/QnAMaker/FAQs.
     */
    @JsonProperty(value = "source")
    private String source;

    /**
     * List of questions associated with the answer.
     */
    @JsonProperty(value = "questions", required = true)
    private List<String> questions;

    /**
     * List of metadata associated with the answer.
     */
    @JsonProperty(value = "metadata")
    private List<MetadataDTO> metadata;

    /**
     * Context of a QnA.
     */
    @JsonProperty(value = "context")
    private QnADTOContext context;

    /**
     * Get unique id for the Q-A.
     *
     * @return the id value
     */
    public Integer id() {
        return this.id;
    }

    /**
     * Set unique id for the Q-A.
     *
     * @param id the id value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get answer text.
     *
     * @return the answer value
     */
    public String answer() {
        return this.answer;
    }

    /**
     * Set answer text.
     *
     * @param answer the answer value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    /**
     * Get source from which Q-A was indexed. eg. https://docs.microsoft.com/en-us/azure/cognitive-services/QnAMaker/FAQs.
     *
     * @return the source value
     */
    public String source() {
        return this.source;
    }

    /**
     * Set source from which Q-A was indexed. eg. https://docs.microsoft.com/en-us/azure/cognitive-services/QnAMaker/FAQs.
     *
     * @param source the source value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * Get list of questions associated with the answer.
     *
     * @return the questions value
     */
    public List<String> questions() {
        return this.questions;
    }

    /**
     * Set list of questions associated with the answer.
     *
     * @param questions the questions value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withQuestions(List<String> questions) {
        this.questions = questions;
        return this;
    }

    /**
     * Get list of metadata associated with the answer.
     *
     * @return the metadata value
     */
    public List<MetadataDTO> metadata() {
        return this.metadata;
    }

    /**
     * Set list of metadata associated with the answer.
     *
     * @param metadata the metadata value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withMetadata(List<MetadataDTO> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get context of a QnA.
     *
     * @return the context value
     */
    public QnADTOContext context() {
        return this.context;
    }

    /**
     * Set context of a QnA.
     *
     * @param context the context value to set
     * @return the QnADTO object itself.
     */
    public QnADTO withContext(QnADTOContext context) {
        this.context = context;
        return this;
    }

}
