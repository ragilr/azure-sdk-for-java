package com.azure.messaging.eventprocessor;

import java.util.List;

public interface PartitionManager {

    List<Ownership> getAllPartitionOwners();

    OwnershipResponse claimOwnership(OwnershipRequest partitionOwnership);

    Checkpoint updateCheckpoint(Checkpoint checkpoint);
}
