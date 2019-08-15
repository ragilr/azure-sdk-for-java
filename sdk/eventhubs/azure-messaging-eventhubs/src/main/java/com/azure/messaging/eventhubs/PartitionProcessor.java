// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.messaging.eventhubs;

import com.azure.core.util.logging.ClientLogger;
import com.azure.messaging.eventhubs.models.PartitionContext;
import java.util.Objects;
import reactor.core.publisher.Mono;

/**
 * An abstract class for processing events from a partition an {@link EventProcessor} instance is responsible for.
 *
 * <p>
 * An instance of partition processor will process events only from a single partition. New instances of partition
 * processors will be created through {@link PartitionProcessorFactory#createPartitionProcessor(PartitionContext,
 * CheckpointManager) PartitionProcessorFactory}.
 * </p>
 * <p>
 * Concrete implementations of this abstract class also have the responsibility of updating checkpoints when
 * appropriate.
 * </p>
 */
public abstract class PartitionProcessor {

    private final ClientLogger logger = new ClientLogger(PartitionProcessor.class);
    private final CheckpointManager checkpointManager;
    private final PartitionContext partitionContext;

    protected PartitionProcessor(PartitionContext partitionContext, CheckpointManager checkpointManager) {
        this.partitionContext = Objects.requireNonNull(partitionContext);
        this.checkpointManager = Objects.requireNonNull(checkpointManager);
    }

    /**
     * Returns the {@link CheckpointManager} that can be used to update checkpoints for the partition.
     *
     * @return The {@link CheckpointManager} for updating checkpoints.
     */
    public CheckpointManager checkpointManager() {
        return this.checkpointManager;
    }

    /**
     * Returns the {@link PartitionContext} containing information about the partition this PartitionProcessor is
     * assigned to process.
     *
     * @return The {@link PartitionContext} containing the Event Hub name, consumer group and partition id this
     * PartitionProcessor is assigned to process.
     */
    public PartitionContext partitionContext() {
        return this.partitionContext;
    }


    /**
     * This method is called when this {@link EventProcessor} takes ownership of a new partition and before any events
     * from this partition are received.
     *
     * @return a representation of the deferred computation of this call.
     */
    public Mono<Void> initialize() {
        logger.info("Initializing partition processor for partition {}", partitionContext.partitionId());
        return Mono.empty();
    }

    /**
     * This method is called when a new event is received for this partition. Processing of this event can happen
     * asynchronously.
     *
     * <p>
     * This is also a good place to update checkpoints as appropriate.
     *
     * @param eventData {@link EventData} received from this partition.
     * @return a representation of the deferred computation of this call.
     */
    public abstract Mono<Void> processEvent(EventData eventData);

    /**
     * This method is called when an error occurs while receiving events from Event Hub. An error also marks the end of
     * event data stream.
     *
     * @param throwable The {@link Throwable} that caused this method to be called.
     */
    public void processError(Throwable throwable) {
        logger.warning("Error processing events from partition {} - {}", partitionContext.partitionId(),
            throwable.getMessage(), throwable);
    }

    /**
     * This method is called before the partition processor is closed. A partition processor could be closed for various
     * reasons and the reasons and implementations of this interface can take appropriate actions to cleanup before the
     * partition processor is shutdown.
     *
     * @param closeReason The reason for closing this partition processor.
     * @return a representation of the deferred computation of this call.
     */
    public Mono<Void> close(CloseReason closeReason) {
        logger.info("Closing partition processor for partition {}", partitionContext.partitionId());
        return Mono.empty();
    }

}
