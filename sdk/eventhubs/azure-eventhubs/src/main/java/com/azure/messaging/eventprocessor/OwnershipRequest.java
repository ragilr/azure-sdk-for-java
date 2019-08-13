package com.azure.messaging.eventprocessor;

public class OwnershipRequest {
    private String eventHub;
    private String consumerName;
    private String partitionId;
    private String instanceId;
    private String version;
    private Long sequenceNumber;
    private long ownerLevel;

    public String getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(String partitionId) {
        this.partitionId = partitionId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public long getOwnerLevel() {
        return ownerLevel;
    }

    public void setOwnerLevel(long ownerLevel) {
        this.ownerLevel = ownerLevel;
    }

}
