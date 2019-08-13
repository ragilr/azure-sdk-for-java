package com.azure.messaging.eventprocessor;

import com.azure.core.implementation.annotation.Immutable;

@Immutable
public class Checkpoint {
    private String partitionId;
    private long sequenceNumber;

    Checkpoint(String partitionId, long sequenceNumber) {
        this.partitionId = partitionId;
        this.sequenceNumber = sequenceNumber;
    }

    public String getPartition() {
        return this.partitionId;
    }

    public long getSequenceNumber() {
        return this.sequenceNumber;
    }
}
