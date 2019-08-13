package com.azure.messaging.eventprocessor;

public class Ownership {
    private String eventHub;
    private String consumerName;
    private String partitionId;
    private String instanceId;
    private String version;
    private Long sequenceNumber;
    private long ownerLevel;
    private long secondsSinceLastModified;

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

    public long getSecondsSinceLastModified() {
        return secondsSinceLastModified;
    }

    public void setSecondsSinceLastModified(long secondsSinceLastModified) {
        this.secondsSinceLastModified = secondsSinceLastModified;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{pid:").append(partitionId).append(", ");
        sb.append("instanceId:").append(instanceId).append(", ");
        sb.append("version:").append(version).append("}");
        return sb.toString();
    }
}
