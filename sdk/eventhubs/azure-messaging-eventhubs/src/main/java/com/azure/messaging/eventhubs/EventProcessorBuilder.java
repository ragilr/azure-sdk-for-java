// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.messaging.eventhubs;

import com.azure.messaging.eventhubs.models.EventPosition;

/**
 * This class provides a fluent builder API to help aid the configuration and instantiation of the {@link
 * EventProcessor}. Calling {@link #buildEventProcessor()} constructs an instance of {@link EventProcessor}.
 *
 * <p>
 * To create an instance of {@link EventProcessor}, configure the following fields:
 * <ul>
 * <li>Consumer group name.</li>
 * <li>{@link EventHubAsyncClient} - An asynchronous Event Hub client the {@link EventProcessor} will use for
 * consuming events.</li>
 * <li>{@link PartitionManager} - An implementation of {@link PartitionManager} to read/write checkpoint and partition
 * ownership details. A sample {@link InMemoryPartitionManager} implementation is provided for convenience.</li>
 * <li>{@link PartitionProcessorFactory} - Implementation of {@link PartitionProcessorFactory} that can create
 * new instance of {@link PartitionProcessor} for each partition the {@link EventProcessor} processes.</li>
 * </ul>
 *
 * @see EventProcessor
 */
public class EventProcessorBuilder {

    private EventPosition initialEventPosition;
    private PartitionProcessorFactory partitionProcessorFactory;
    private String consumerGroupName;
    private PartitionManager partitionManager;
    private EventHubAsyncClient eventHubAsyncClient;

    /**
     * Sets the {@link EventHubAsyncClient} that will be used to create new consumer(s) to receive events.
     *
     * @param eventHubAsyncClient The {@link EventHubAsyncClient} that will be used to create new consumer(s) to receive
     * events.
     * @return The updated {@link EventProcessorBuilder} object.
     */
    public EventProcessorBuilder eventHubAsyncClient(EventHubAsyncClient eventHubAsyncClient) {
        this.eventHubAsyncClient = eventHubAsyncClient;
        return this;
    }

    /**
     * Sets the consumer group name from which the {@link EventProcessor} should consume events from.
     *
     * @param consumerGroupName The consumer group name this {@link EventProcessor} should consume events from.
     * @return The updated {@link EventProcessorBuilder} object.
     */
    public EventProcessorBuilder consumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
        return this;
    }

    /**
     * Sets the {@link PartitionManager} the {@link EventProcessor} will use for storing partition ownership and
     * checkpoint information.
     *
     * @param partitionManager Implementation of {@link PartitionManager}.
     * @return The updated {@link EventProcessorBuilder} object.
     */
    public EventProcessorBuilder partitionManager(PartitionManager partitionManager) {
        // If this is not set, look for classes implementing PartitionManager interface
        // in the classpath and use it automatically. (To be implemented)
        this.partitionManager = partitionManager;
        return this;
    }

    /**
     * Sets the partition processor factory for creating new instance(s) of {@link PartitionProcessor}.
     *
     * @param partitionProcessorFactory The factory that creates new {@link PartitionProcessor} for each partition.
     * @return The updated {@link EventProcessorBuilder} object.
     */
    public EventProcessorBuilder partitionProcessorFactory(PartitionProcessorFactory partitionProcessorFactory) {
        this.partitionProcessorFactory = partitionProcessorFactory;
        return this;
    }

    /**
     * Sets the initial event position. If this property is not set and if checkpoint for a partition doesn't exist,
     * {@link EventPosition#earliest()} will be used as the initial event position to start consuming events.
     *
     * @param initialEventPosition The initial event position.
     * @return The updated {@link EventProcessorBuilder} object.
     */
    public EventProcessorBuilder initialEventPosition(EventPosition initialEventPosition) {
        this.initialEventPosition = initialEventPosition;
        return this;
    }

    /**
     * This will create a new {@link EventProcessor} configured with the options set in this builder. Each call to this
     * method will return a new instance of {@link EventProcessor}.
     *
     * <p>
     * If the {@link #initialEventPosition(EventPosition) initial event position} is not set, all partitions processed by
     * this {@link EventProcessor} will start processing from {@link EventPosition#earliest() earliest} available event in
     * the respective partitions.
     * </p>
     *
     * @return A new instance of {@link EventProcessor}.
     */
    public EventProcessor buildEventProcessor() {
        EventPosition initialEventPosition =
            this.initialEventPosition == null ? EventPosition.earliest()
                : this.initialEventPosition;
        return new EventProcessor(eventHubAsyncClient, this.consumerGroupName,
            this.partitionProcessorFactory, initialEventPosition, partitionManager);
    }
}
