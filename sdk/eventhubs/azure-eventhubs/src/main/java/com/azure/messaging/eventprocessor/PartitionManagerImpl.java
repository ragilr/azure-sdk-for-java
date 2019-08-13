package com.azure.messaging.eventprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PartitionManagerImpl implements PartitionManager {
    private final Map<String, Ownership> partitionOwnershipMap = new ConcurrentHashMap<>();

    @Override
    public List<Ownership> getAllPartitionOwners() {
        System.out.println("Getting all partition owners " + partitionOwnershipMap);
        return new ArrayList<>(partitionOwnershipMap.values());
    }

    @Override
    public OwnershipResponse claimOwnership(
        OwnershipRequest partitionOwnership) {
        OwnershipResponse response = new OwnershipResponse();
        response.setSuccess(true);
        response.setNewVersion("0");
        response.setTimeSinceLastModifiedInSeconds(0);
        Ownership po = new Ownership();
        po.setSecondsSinceLastModified(0);
        po.setInstanceId(partitionOwnership.getInstanceId());
        po.setPartitionId(partitionOwnership.getPartitionId());
        po.setVersion(partitionOwnership.getVersion());
        po.setSequenceNumber(partitionOwnership.getSequenceNumber());
        po.setOwnerLevel(partitionOwnership.getOwnerLevel());
        partitionOwnershipMap.put(partitionOwnership.getPartitionId(), po);
        System.out
            .println("Instance " + partitionOwnership.getInstanceId() + " claiming ownership of partition " + partitionOwnership.getPartitionId());
        return response;
    }

    @Override
    public Checkpoint updateCheckpoint(Checkpoint checkpoint) {
        System.out.println("Checkpointing...");

        return null;
    }
}
