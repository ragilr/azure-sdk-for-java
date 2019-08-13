package com.azure.messaging.eventprocessor;

import com.azure.messaging.eventhubs.models.EventPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class LoadBalancer {
    private static final long EXPIRATION_TIME_IN_SECONDS = 30;
    private static final Random RANDOM = new Random();
    private final PartitionManager partitionManager;
    private final Map<String, Ownership> ownedPartitions;
    private final Set<String> eventHubPartitionIds;
    private final String eventHub;
    private final String consumerName;
    private final String instanceId;


    public LoadBalancer(String instanceId, String eventHubPath, String eventHubConsumer,
        Set<String> eventHubPartitionIds,
        PartitionManager partitionManager,
        Map<String, Ownership> ownedPartitions) {
        this.eventHub = eventHubPath;
        this.consumerName = eventHubConsumer;
        this.instanceId = instanceId;
        this.ownedPartitions = ownedPartitions;
        this.partitionManager = partitionManager;
        this.eventHubPartitionIds = eventHubPartitionIds;
    }

    void balance() {
        // Get current partition ownerships from the ownership service (store)
        List<Ownership> allOwnerships = partitionManager.getAllPartitionOwners();

        // If the list returned from the service is empty,
        // This event hub processing is being bootstrapped for the first time.
        // All event hub partitions are available to claim ownership.
        // Claim any partition and return
        if (allOwnerships == null || allOwnerships.isEmpty()) {
            int partitionIdToOwn = RANDOM.nextInt(eventHubPartitionIds.size());
            OwnershipResponse ownershipResponse = createPartitionOwnershipRequest(
                String.valueOf(partitionIdToOwn), EventPosition.earliest().sequenceNumber());
            if (ownershipResponse.isSuccess()) {
                // Successfully claimed ownership of this partition
                Ownership ownership = new Ownership();
                ownership.setVersion(ownershipResponse.getNewVersion());
                ownership.setPartitionId(String.valueOf(partitionIdToOwn));
                ownership.setInstanceId(this.instanceId);
                ownership.setSequenceNumber(EventPosition.earliest().sequenceNumber());
                ownership.setSecondsSinceLastModified(
                    ownershipResponse.getTimeSinceLastModifiedInSeconds());
                this.ownedPartitions.put(String.valueOf(partitionIdToOwn), new Ownership());
            }
            return;
        }

        // The list is not empty, so there are partitions that are previously owned.

        // Create a map of partition id and partition ownership
        Map<String, Ownership> allPartitionOwnersMap = createAllPartitionOwnerMap(
            allOwnerships);

        // Create a map of active partition id and partition ownership
        Map<String, Ownership> activePartitionOwnersMap = createActivePartitionOwnerMap(
            allOwnerships);

        // Get all ownerships that are stale (not modified for a long time)
        List<Ownership> activeOwnerships = new ArrayList<>(
            activePartitionOwnersMap.values());

        // Create a map of all active instance ids and a list of all partitions the instance owns
        Map<String, List<Ownership>> instanceIdPartitionMap = createInstancePartitionOwnershipMap(
            activeOwnerships);

        int noOfInstancesOwnedByMe = instanceIdPartitionMap.get(this.instanceId).size();
        int minPartitionsEachInstanceShouldOwn =
            eventHubPartitionIds.size() / instanceIdPartitionMap.size();
        int maxPartitionsEachInstanceCanOwn = minPartitionsEachInstanceShouldOwn + 1;
        int maxInstancesThatCanOwnOneAdditionalPartition =
            eventHubPartitionIds.size() % instanceIdPartitionMap.size();
        int noOfInstancesThatOwnMaxPartitions = getNoOfInstancesThatOwnMaxPartitions(
            instanceIdPartitionMap, maxPartitionsEachInstanceCanOwn);

        if (isLoadBalanced(minPartitionsEachInstanceShouldOwn, maxPartitionsEachInstanceCanOwn, maxInstancesThatCanOwnOneAdditionalPartition,
            noOfInstancesThatOwnMaxPartitions, instanceIdPartitionMap)
            || noOfInstancesOwnedByMe >= maxPartitionsEachInstanceCanOwn) {
            // This instance is already owning equal to max or more no. of partitions each instance can own
            // So, there's nothing more to do here, return.
            return;
        }

        // If size of active partition owners map is not equal to the size of event hub partitions,
        // there are unclaimed partitions. If current instance doesn't own expected share of partitions,
        // claim any partition that is unclaimed
        if (activePartitionOwnersMap.size() < eventHubPartitionIds.size()) {
            List<String> unownedPartitionIds = getUnownedPartitionsIds(eventHubPartitionIds, activeOwnerships);
            System.out.println("Unowned partitionIds " + unownedPartitionIds);
            if (noOfInstancesOwnedByMe < minPartitionsEachInstanceShouldOwn
                || noOfInstancesThatOwnMaxPartitions
                < maxInstancesThatCanOwnOneAdditionalPartition) {
                String partitionIdToOwn = unownedPartitionIds
                    .get(RANDOM.nextInt(unownedPartitionIds.size()));
                Long startFromSequence =
                    allPartitionOwnersMap.containsKey(partitionIdToOwn) ? allPartitionOwnersMap
                        .get(partitionIdToOwn).getSequenceNumber()
                        : EventPosition.earliest().sequenceNumber();
                OwnershipResponse ownershipResponse = createPartitionOwnershipRequest(
                    partitionIdToOwn, startFromSequence);

                if (ownershipResponse.isSuccess()) {
                    // Successfully claimed ownership of this partition
                    Ownership ownership = new Ownership();
                    ownership.setVersion(ownershipResponse.getNewVersion());
                    ownership.setPartitionId(partitionIdToOwn);
                    ownership.setInstanceId(this.instanceId);
                    ownership.setSequenceNumber(startFromSequence);
                    ownership.setSecondsSinceLastModified(
                        ownershipResponse.getTimeSinceLastModifiedInSeconds());
                    this.ownedPartitions.put(partitionIdToOwn, ownership);
                }
            }
            return;
        }

        // If we have reached this stage, then
        // 1. the load is not balanced,
        // 2. this instance has capacity to own more partitions
        // 3. size of active partition owners map is equal to the size of event hub partitions,
        // 4. every partition is actively owned by some instance.
        // 5. one of the other instances is owning more partitions than necessary

        Entry<String, List<Ownership>> overloadedInstance = instanceIdPartitionMap
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().size() > maxPartitionsEachInstanceCanOwn)
            .findFirst()
            .orElse(null);

        if (overloadedInstance == null) {
            overloadedInstance = instanceIdPartitionMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == maxPartitionsEachInstanceCanOwn)
                .findFirst()
                .orElse(null);
        }

        List<Ownership> partitionsOwnedByOverloadedInstance = overloadedInstance
            .getValue();
        Ownership partitionToOwn = partitionsOwnedByOverloadedInstance
            .get(RANDOM.nextInt(partitionsOwnedByOverloadedInstance.size()));
        OwnershipResponse ownershipResponse = createPartitionOwnershipRequest(
            partitionToOwn.getPartitionId(), partitionToOwn.getSequenceNumber());
        if (ownershipResponse.isSuccess()) {
            Ownership ownership = new Ownership();
            ownership.setVersion(ownershipResponse.getNewVersion());
            ownership.setPartitionId(partitionToOwn.getPartitionId());
            ownership.setInstanceId(this.instanceId);
            ownership.setSequenceNumber(EventPosition.earliest().sequenceNumber());
            ownership.setSecondsSinceLastModified(
                ownershipResponse.getTimeSinceLastModifiedInSeconds());
            this.ownedPartitions.put(partitionToOwn.getPartitionId(), ownership);
        }
    }

    private boolean isLoadBalanced(int minPartitionsEachInstanceShouldOwn,
        int maxPartitionsEachInstanceCanOwn,
        int maxInstancesThatCanOwnOneAdditionalPartition, int noOfInstancesThatOwnMaxPartitions,
        Map<String, List<Ownership>> instanceIdPartitionMap) {

        if (maxInstancesThatCanOwnOneAdditionalPartition < noOfInstancesThatOwnMaxPartitions) {
            return false;
        }

        return instanceIdPartitionMap
            .entrySet()
            .stream()
            .allMatch(entry -> entry.getValue().size() >= minPartitionsEachInstanceShouldOwn
                && entry.getValue().size() <= maxPartitionsEachInstanceCanOwn);
    }

    private int getNoOfInstancesThatOwnMaxPartitions(
        Map<String, List<Ownership>> instanceIdPartitionMap,
        int maxPartitionsEachInstanceCanOwn) {
        int noOfInstancesThatOwnMaxPartitions = 0;
        for (Entry<String, List<Ownership>> entry : instanceIdPartitionMap
            .entrySet()) {
            if (entry.getValue().size() == maxPartitionsEachInstanceCanOwn) {
                noOfInstancesThatOwnMaxPartitions++;
            }
        }
        return noOfInstancesThatOwnMaxPartitions;
    }

    private List<String> getUnownedPartitionsIds(Set<String> eventHubPartitionIds,
        List<Ownership> activeOwnerships) {

        Set<String> unownedPartitionIds = new HashSet<>();
        unownedPartitionIds.addAll(eventHubPartitionIds);
        activeOwnerships.stream()
            .forEach(partitionOwnership -> unownedPartitionIds
                .remove(partitionOwnership.getPartitionId()));
        List<String> result = new ArrayList<>();
        result.addAll(unownedPartitionIds);
        return result;
    }

    private Map<String, List<Ownership>> createInstancePartitionOwnershipMap(
        List<Ownership> activeOwnerships) {
        Map<String, List<Ownership>> instancePartitionOwnershipMap = new HashMap<>();
        activeOwnerships.stream()
            .forEach(partitionOwnership -> {
                List<Ownership> ownerships = instancePartitionOwnershipMap
                    .get(partitionOwnership.getInstanceId());
                if (ownerships == null) {
                    ownerships = new ArrayList<>();
                    instancePartitionOwnershipMap
                        .put(partitionOwnership.getInstanceId(), ownerships);
                }
                ownerships.add(partitionOwnership);
            });

        // add current instance if it's not already there
        if (!instancePartitionOwnershipMap.containsKey(this.instanceId)) {
            instancePartitionOwnershipMap.put(this.instanceId, new ArrayList<>());
        }

        return instancePartitionOwnershipMap;
    }

    private Map<String, Ownership> createActivePartitionOwnerMap(
        List<Ownership> allOwnerships) {
        return allOwnerships.stream()
            .filter(partitionOwnership -> partitionOwnership.getSecondsSinceLastModified()
                < EXPIRATION_TIME_IN_SECONDS)
            .collect(Collectors.toMap(partitionOwnership -> partitionOwnership.getPartitionId(),
                partitionOwnership -> partitionOwnership));
    }

    private Map<String, Ownership> createAllPartitionOwnerMap(
        List<Ownership> allOwnerships) {
        return allOwnerships.stream()
            .collect(Collectors.toMap(partitionOwnership -> partitionOwnership.getPartitionId(),
                partitionOwnership -> partitionOwnership));
    }

    private OwnershipResponse createPartitionOwnershipRequest(String partitionIdToOwn, Long sequenceNumber) {
        OwnershipRequest ownershipRequest = new OwnershipRequest();
        ownershipRequest.setInstanceId(this.instanceId);
        ownershipRequest.setOwnerLevel(0);
        ownershipRequest.setPartitionId(partitionIdToOwn);
        ownershipRequest.setSequenceNumber(sequenceNumber);
        return partitionManager
            .claimOwnership(ownershipRequest);
    }
}
