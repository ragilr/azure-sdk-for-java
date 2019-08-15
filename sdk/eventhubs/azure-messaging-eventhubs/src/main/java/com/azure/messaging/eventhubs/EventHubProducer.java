// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.messaging.eventhubs;

import com.azure.core.implementation.annotation.Immutable;
import com.azure.messaging.eventhubs.models.BatchOptions;
import com.azure.messaging.eventhubs.models.SendOptions;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * A producer responsible for transmitting {@link EventData} to a specific Event Hub. Depending on the options specified
 * at creation, the producer may be created to allow event data to be automatically routed to an available partition or
 * to a specific partition.
 *
 * <p>
 * Allowing automatic routing of partitions is recommended when:
 * <ul>
 * <li>The sending of events needs to be highly available.</li>
 * <li>The event data should be evenly distributed among all available partitions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * If no partition is specified, the following rules are used for automatically selecting one:
 * <ol>
 * <li>Distribute the events equally amongst all available partitions using a round-robin approach.</li>
 * <li>If a partition becomes unavailable, the Event Hubs service will automatically detect it and forward the
 * message to another available partition.</li>
 * </ol>
 * </p>
 *
 * @see EventHubClient#createProducer()
 * @see EventHubAsyncProducer To asynchronously generate events to an Event Hub, see EventHubAsyncProducer.
 */
@Immutable
public class EventHubProducer implements Closeable {
    private final EventHubAsyncProducer producer;
    private final Duration tryTimeout;

    /**
     * Creates a new instance of {@link EventHubProducer} that sends messages to an Azure Event Hub.
     *
     * @throws NullPointerException if {@code producer} or {@code tryTimeout} is null.
     */
    EventHubProducer(EventHubAsyncProducer producer, Duration tryTimeout) {
        this.producer = Objects.requireNonNull(producer);
        this.tryTimeout = Objects.requireNonNull(tryTimeout);
    }

    /**
     * Creates an {@link EventDataBatch} that can fit as many events as the transport allows.
     *
     * @return A new {@link EventDataBatch} that can fit as many events as the transport allows.
     */
    public EventDataBatch createBatch() {
        return producer.createBatch().block(tryTimeout);
    }

    /**
     * Creates an {@link EventDataBatch} that can fit as many events as the transport allows.
     *
     * @param options A set of options used to configure the {@link EventDataBatch}.
     * @return A new {@link EventDataBatch} that can fit as many events as the transport allows.
     */
    public EventDataBatch createBatch(BatchOptions options) {
        return producer.createBatch(options).block(tryTimeout);
    }

    /**
     * Sends a single event to the associated Event Hub. If the size of the single event exceeds the maximum size
     * allowed, an exception will be triggered and the send will fail.
     *
     * <p>
     * For more information regarding the maximum event size allowed, see
     * <a href="https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-quotas">Azure Event Hubs Quotas and
     * Limits</a>.
     * </p>
     *
     * @param event Event to send to the service.
     */
    public void send(EventData event) {
        producer.send(event).block();
    }

    /**
     * Sends a single event to the associated Event Hub with the send options. If the size of the single event exceeds
     * the maximum size allowed, an exception will be triggered and the send will fail.
     *
     * <p>
     * For more information regarding the maximum event size allowed, see
     * <a href="https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-quotas">Azure Event Hubs Quotas and
     * Limits</a>.
     * </p>
     *
     * @param event Event to send to the service.
     * @param options The set of options to consider when sending this event.
     */
    public void send(EventData event, SendOptions options) {
        producer.send(event, options).block();
    }

    /**
     * Sends a set of events to the associated Event Hub using a batched approach. If the size of events exceed the
     * maximum size of a single batch, an exception will be triggered and the send will fail. By default, the message
     * size is the max amount allowed on the link.
     *
     * <p>
     * For more information regarding the maximum event size allowed, see
     * <a href="https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-quotas">Azure Event Hubs Quotas and
     * Limits</a>.
     * </p>
     *
     * @param events Events to send to the service.
     */
    public void send(Iterable<EventData> events) {
        producer.send(events).block();
    }

    /**
     * Sends a set of events to the associated Event Hub using a batched approach. If the size of events exceed the
     * maximum size of a single batch, an exception will be triggered and the send will fail. By default, the message
     * size is the max amount allowed on the link.
     *
     * <p>
     * For more information regarding the maximum event size allowed, see
     * <a href="https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-quotas">Azure Event Hubs Quotas and
     * Limits</a>.
     * </p>
     *
     * @param events Events to send to the service.
     * @param options The set of options to consider when sending this batch.
     */
    public void send(Iterable<EventData> events, SendOptions options) {
        producer.send(events, options).block();
    }

    /**
     * Sends the batch to the associated Event Hub.
     *
     * @param batch The batch to send to the service.
     * @throws NullPointerException if {@code batch} is {@code null}.
     * @see EventHubProducer#createBatch()
     * @see EventHubProducer#createBatch(BatchOptions)
     */
    public void send(EventDataBatch batch) {
        producer.send(batch).block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        producer.close();
    }
}
