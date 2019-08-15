// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.messaging.eventhubs;

import com.azure.core.util.logging.ClientLogger;
import com.azure.messaging.eventhubs.models.Checkpoint;
import com.azure.messaging.eventhubs.models.PartitionOwnership;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple in-memory implementation of a {@link PartitionManager}. This implementation keeps track of partition
 * ownership details including checkpointing information in-memory. Using this implementation will only facilitate
 * checkpointing and load balancing of Event Processors running within this process.
 */
public class InMemoryPartitionManager implements PartitionManager {

    private final Map<String, PartitionOwnership> partitionOwnershipMap = new ConcurrentHashMap<>();
    private final ClientLogger logger = new ClientLogger(InMemoryPartitionManager.class);

    /**
     * {@inheritDoc}
     *
     * @param eventHubName The name of the Event Hub to list ownership of.
     * @param consumerGroupName The name of the consumer group to list ownership of.
     * @return A {@link Flux} of partition ownership information.
     */
    @Override
    public Flux<PartitionOwnership> listOwnership(String eventHubName, String consumerGroupName) {
        logger.info("Listing partition ownership");
        return Flux.fromStream(partitionOwnershipMap.values()
            .stream()
            .filter(po -> po.consumerGroupName().equals(consumerGroupName) && po.eventHubName().equals(eventHubName)));
    }

    /**
     * Returns a {@link Flux} of partition ownership details for successfully claimed partitions. If a partition is
     * already claimed by an instance or if the ETag in the request doesn't match the previously stored ETag, then
     * ownership claim is denied.
     *
     * @param requestedPartitionOwnerships Array of partition ownerships this instance is requesting to own.
     * @return Successfully claimed partition ownerships.
     */
    @Override
    public Flux<PartitionOwnership> claimOwnership(PartitionOwnership... requestedPartitionOwnerships) {
        return Flux.fromArray(requestedPartitionOwnerships)
            .filter(partitionOwnership -> {

                final String key = getKey(partitionOwnership.eventHubName(), partitionOwnership.consumerGroupName(),
                    partitionOwnership.partitionId());

                return !partitionOwnershipMap.containsKey(key)
                    || partitionOwnershipMap.get(key).eTag()
                    .equals(partitionOwnership.eTag());
            })
            .doOnNext(partitionOwnership -> logger
                .info("Ownership of partition {} claimed by {}", partitionOwnership.partitionId(),
                    partitionOwnership.ownerId()))
            .map(partitionOwnership -> {
                partitionOwnership.eTag(UUID.randomUUID().toString())
                    .lastModifiedTime(System.currentTimeMillis());

                final String key = getKey(partitionOwnership.eventHubName(), partitionOwnership.consumerGroupName(),
                    partitionOwnership.partitionId());

                partitionOwnershipMap.put(key, partitionOwnership);
                return partitionOwnership;
            });
    }

    /**
     * Updates the in-memory storage with the provided checkpoint information.
     *
     * @param checkpoint The checkpoint containing the information to be stored in-memory.
     * @return A new ETag associated with the updated checkpoint.
     */
    @Override
    public Mono<String> updateCheckpoint(final Checkpoint checkpoint) {
        final String key = getKey(checkpoint.eventHubName(), checkpoint.consumerGroupName(), checkpoint.partitionId());
        final String updatedETag = UUID.randomUUID().toString();

        //TODO (conniey): What happens if the partitionOwnershipMap does not contain the key?
        partitionOwnershipMap.get(key)
            .sequenceNumber(checkpoint.sequenceNumber())
            .offset(checkpoint.offset())
            .eTag(updatedETag);
        logger.info("Updated checkpoint for partition {} with sequence number {}", checkpoint.partitionId(),
            checkpoint.sequenceNumber());
        return Mono.just(updatedETag);
    }

    private static String getKey(String eventHubName, String consumerGroupName, String partitionId) {
        return String.join(",", eventHubName, consumerGroupName, partitionId);
    }
}
