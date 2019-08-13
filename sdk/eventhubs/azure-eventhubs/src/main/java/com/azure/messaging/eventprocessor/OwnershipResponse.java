package com.azure.messaging.eventprocessor;

public class OwnershipResponse {
    private boolean success;
    private String newVersion;
    private long timeSinceLastModifiedInSeconds;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public long getTimeSinceLastModifiedInSeconds() {
        return timeSinceLastModifiedInSeconds;
    }

    public void setTimeSinceLastModifiedInSeconds(long timeSinceLastModifiedInSeconds) {
        this.timeSinceLastModifiedInSeconds = timeSinceLastModifiedInSeconds;
    }
}
