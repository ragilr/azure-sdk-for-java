// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.messaging.eventhubs;

import com.azure.core.amqp.RetryOptions;
import com.azure.core.http.rest.IterableResponse;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceClient;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.messaging.eventhubs.implementation.ConnectionOptions;
import com.azure.messaging.eventhubs.models.EventHubProducerOptions;

import java.io.Closeable;
import java.time.Duration;
import java.util.Objects;

/**
 * A <strong>synchronous</strong> client that is the main point of interaction with Azure Event Hubs. It connects to a
 * specific Event Hub and allows operations for sending event data, receiving data, and inspecting the Event Hub's
 * metadata.
 *
 * <p>
 * <strong>Creating a synchronous {@link EventHubClient} using an Event Hub instance connection string</strong>
 * </p>
 *
 * {@codesnippet com.azure.messaging.eventhubs.eventhubclient.instantiation}
 *
 * @see EventHubClientBuilder
 * @see EventHubAsyncClient See EventHubAsyncClient to communicate with an Event Hub using an asynchronous client.
 * @see <a href="https://docs.microsoft.com/Azure/event-hubs/event-hubs-about">About Azure Event Hubs</a>
 */
@ServiceClient(builder = EventHubClientBuilder.class)
public class EventHubClient implements Closeable {
    private final EventHubAsyncClient client;
    private final RetryOptions retry;
    private final EventHubProducerOptions defaultProducerOptions;

    EventHubClient(EventHubAsyncClient client, ConnectionOptions connectionOptions) {
        Objects.requireNonNull(connectionOptions);

        this.client = Objects.requireNonNull(client);
        this.retry = connectionOptions.retry();
        this.defaultProducerOptions = new EventHubProducerOptions()
            .retry(connectionOptions.retry());
    }

    /**
     * Retrieves information about an Event Hub, including the number of partitions present and their identifiers.
     *
     * @return The set of information for the Event Hub that this client is associated with.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public EventHubProperties getProperties() {
        return client.getProperties().block(retry.tryTimeout());
    }

    /**
     * Retrieves the identifiers for all the partitions of an Event Hub.
     *
     * @return The identifiers for all partitions of an Event Hub.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public IterableResponse<String> getPartitionIds() {
        return new IterableResponse<>(client.getPartitionIds());
    }

    /**
     * Retrieves information about a specific partition for an Event Hub, including elements that describe the available
     * events in the partition event stream.
     *
     * @param partitionId The unique identifier of a partition associated with the Event Hub.
     * @return The information for the requested partition under the Event Hub this client is associated with.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PartitionProperties getPartitionProperties(String partitionId) {
        return client.getPartitionProperties(partitionId).block(retry.tryTimeout());
    }

    /**
     * Creates an Event Hub producer responsible for transmitting {@link EventData} to the Event Hub, grouped together
     * in batches. Event data is automatically routed to an available partition.
     *
     * @return A new {@link EventHubProducer}.
     */
    public EventHubProducer createProducer() {
        return createProducer(defaultProducerOptions);
    }

    /**
     * Creates an Event Hub producer responsible for transmitting {@link EventData} to the Event Hub, grouped together
     * in batches. If {@link EventHubProducerOptions#partitionId() options.partitionId()} is not {@code null}, the
     * events are routed to that specific partition. Otherwise, events are automatically routed to an available
     * partition.
     *
     * @param options The set of options to apply when creating the producer.
     * @return A new {@link EventHubProducer}.
     * @throws NullPointerException if {@code options} is {@code null}.
     */
    public EventHubProducer createProducer(EventHubProducerOptions options) {
        Objects.requireNonNull(options);

        final EventHubAsyncProducer producer = client.createProducer(options);

        final Duration tryTimeout = options.retry() != null && options.retry().tryTimeout() != null
            ? options.retry().tryTimeout()
            : defaultProducerOptions.retry().tryTimeout();

        return new EventHubProducer(producer, tryTimeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        client.close();
    }
}
