// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.file;

import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.http.rest.VoidResponse;
import com.azure.core.util.Context;
import com.azure.storage.common.credentials.SASTokenCredential;
import com.azure.storage.common.credentials.SharedKeyCredential;
import com.azure.storage.file.models.FileHTTPHeaders;
import com.azure.storage.file.models.ShareInfo;
import com.azure.storage.file.models.ShareProperties;
import com.azure.storage.file.models.ShareSnapshotInfo;
import com.azure.storage.file.models.ShareStatistics;
import com.azure.storage.file.models.SignedIdentifier;
import com.azure.storage.file.models.StorageErrorException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * This class provides a client that contains all the operations for interacting with a share in Azure Storage Share.
 * Operations allowed by the client are creating and deleting the share, creating snapshots for the share, creating and
 * deleting directories in the share and retrieving and updating properties metadata and access policies of the share.
 *
 * <p><strong>Instantiating a Synchronous Share Client</strong></p>
 *
 * {@codesnippet com.azure.storage.file.shareClient.instantiation}
 *
 * <p>View {@link ShareClientBuilder this} for additional ways to construct the client.</p>
 *
 * @see ShareClientBuilder
 * @see ShareAsyncClient
 * @see SharedKeyCredential
 * @see SASTokenCredential
 */
public class ShareClient {
    private final ShareAsyncClient client;

    ShareClient(ShareAsyncClient client) {
        this.client = client;
    }

    /**
     * Get the url of the storage share client.
     * @return the url of the Storage Share.
     * @throws RuntimeException If the share is using a malformed URL.
     */
    public URL getShareUrl() {
        return client.getShareUrl();
    }

    /**
     * Constructs a {@link DirectoryClient} that interacts with the root directory in the share.
     *
     * <p>If the directory doesn't exist in the share {@link DirectoryClient#create() create} in the client will
     * need to be called before interaction with the directory can happen.</p>
     *
     * @return a {@link DirectoryClient} that interacts with the root directory in the share
     */
    public DirectoryClient getRootDirectoryClient() {
        return getDirectoryClient("");
    }

    /**
     * Constructs a {@link DirectoryClient} that interacts with the specified directory.
     *
     * <p>If the directory doesn't exist in the share {@link DirectoryClient#create() create} in the client will
     * need to be called before interaction with the directory can happen.</p>
     *
     * @param directoryName Name of the directory
     * @return a {@link DirectoryClient} that interacts with the directory in the share
     */
    public DirectoryClient getDirectoryClient(String directoryName) {
        return new DirectoryClient(client.getDirectoryClient(directoryName));
    }

    /**
     * Constructs a {@link FileClient} that interacts with the specified file.
     *
     * <p>If the file doesn't exist in the share {@link FileClient#create(long)} ) create} in the client will
     * need to be called before interaction with the file can happen.</p>
     *
     * @param filePath Name of the file
     * @return a {@link FileClient} that interacts with the file in the share
     */
    public FileClient getFileClient(String filePath) {
        return new FileClient(client.getFileClient(filePath));
    }

    /**
     * Creates the share in the storage account.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the share</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.create}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-share">Azure Docs</a>.</p>
     *
     * @return The {@link ShareInfo information about the share}.
     * @throws StorageErrorException If the share already exists with different metadata
     */
    public ShareInfo create() {
        return createWithResponse(null, null, Context.NONE).value();
    }

    /**
     * Creates the share in the storage account with the specified metadata and quota.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the share with metadata "share:metadata"</p>
     *
     * {@codesnippet com.azure.storage.file.ShareClient.createWithResponse#Map-Integer-Context.metadata}
     *
     * <p>Create the share with a quota of 10 GB</p>
     *
     * {@codesnippet com.azure.storage.file.ShareClient.createWithResponse#Map-Integer-Context.quota}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-share">Azure Docs</a>.</p>
     *
     * @param metadata Optional metadata to associate with the share
     * @param quotaInGB Optional maximum size the share is allowed to grow to in GB. This must be greater than 0 and
     * less than or equal to 5120. The default value is 5120.
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing the {@link ShareInfo information about the share} and the status its creation.
     * @throws StorageErrorException If the share already exists with different metadata or {@code quotaInGB} is outside the
     * allowed range.
     */
    public Response<ShareInfo> createWithResponse(Map<String, String> metadata, Integer quotaInGB, Context context) {
        return client.createWithResponse(metadata, quotaInGB, context).block();
    }

    /**
     * Creates a snapshot of the share with the same metadata associated to the share at the time of creation.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create a snapshot</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createSnapshot}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/snapshot-share">Azure Docs</a>.</p>
     *
     * @return The {@link ShareSnapshotInfo information about snapshot of share}
     * @throws StorageErrorException If the share doesn't exist, there are 200 snapshots of the share, or a snapshot is
     * in progress for the share
     */
    public ShareSnapshotInfo createSnapshot() {
        return createSnapshotWithResponse(null, Context.NONE).value();
    }

    /**
     * Creates a snapshot of the share with the metadata that was passed associated to the snapshot.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create a snapshot with metadata "snapshot:metadata"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createSnapshotWithResponse#map-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/snapshot-share">Azure Docs</a>.</p>
     *
     * @param metadata Optional metadata to associate with the snapshot. If {@code null} the metadata of the share
     * will be copied to the snapshot.
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing the {@link ShareSnapshotInfo information about snapshot of the share} and status of creation.
     * @throws StorageErrorException If the share doesn't exist, there are 200 snapshots of the share, or a snapshot is
     * in progress for the share
     */
    public Response<ShareSnapshotInfo> createSnapshotWithResponse(Map<String, String> metadata, Context context) {
        return client.createSnapshotWithResponse(metadata, context).block();
    }

    /**
     * Deletes the share in the storage account
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the share</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.delete}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-share">Azure Docs</a>.</p>
     *
     * @throws StorageErrorException If the share doesn't exist
     */
    public void delete() {
        deleteWithResponse(Context.NONE);
    }

    /**
     * Deletes the share in the storage account
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the share</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.deleteWithResponse#Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-share">Azure Docs</a>.</p>
     *
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response that only contains headers and response status code
     * @throws StorageErrorException If the share doesn't exist
     */
    public VoidResponse deleteWithResponse(Context context) {
        return client.deleteWithResponse(context).block();
    }

    /**
     * Retrieves the properties of the share, these include the metadata associated to it and the quota that the share
     * is restricted to.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Retrieve the share properties</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getProperties}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-properties">Azure Docs</a>.</p>
     *
     * @return The {@link ShareProperties properties of the share}
     * @throws StorageErrorException If the share doesn't exist
     */
    public ShareProperties getProperties() {
        return getPropertiesWithResponse(Context.NONE).value();
    }

    /**
     * Retrieves the properties of the share, these include the metadata associated to it and the quota that the share
     * is restricted to.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Retrieve the share properties</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getPropertiesWithResponse#Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-properties">Azure Docs</a>.</p>
     *
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing {@link ShareProperties properties of the share} with response status code
     * @throws StorageErrorException If the share doesn't exist
     */
    public Response<ShareProperties> getPropertiesWithResponse(Context context) {
        return client.getPropertiesWithResponse(context).block();
    }

    /**
     * Sets the maximum size in GB that the share is allowed to grow.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set the quota to 1024 GB</p>
     *
     * {@codesnippet com.azure.storage.file.ShareClient.setQuota#int}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-properties">Azure Docs</a>.</p>
     *
     * @param quotaInGB Size in GB to limit the share's growth. The quota in GB must be between 1 and 5120.
     * @return The {@link ShareProperties properties of the share}
     * @throws StorageErrorException If the share doesn't exist or {@code quotaInGB} is outside the allowed bounds
     */
    public ShareInfo setQuota(int quotaInGB) {
        return setQuotaWithResponse(quotaInGB, Context.NONE).value();
    }

    /**
     * Sets the maximum size in GB that the share is allowed to grow.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set the quota to 1024 GB</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.setQuotaWithResponse#int-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-properties">Azure Docs</a>.</p>
     *
     * @param quotaInGB Size in GB to limit the share's growth. The quota in GB must be between 1 and 5120.
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing {@link ShareProperties properties of the share} with response status code
     * @throws StorageErrorException If the share doesn't exist or {@code quotaInGB} is outside the allowed bounds
     */
    public Response<ShareInfo> setQuotaWithResponse(int quotaInGB, Context context) {
        return client.setQuotaWithResponse(quotaInGB, context).block();
    }

    /**
     * Sets the user-defined metadata to associate to the share.
     *
     * <p>If {@code null} is passed for the metadata it will clear the metadata associated to the share.</p>
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set the metadata to "share:updatedMetadata"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.setMetadata#map}
     *
     * <p>Clear the metadata of the share</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.clearMetadata#map}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/set-share-metadata">Azure Docs</a>.</p>
     *
     * @param metadata Metadata to set on the share, if null is passed the metadata for the share is cleared
     * @return The {@link ShareProperties properties of the share}
     * @throws StorageErrorException If the share doesn't exist or the metadata contains invalid keys
     */
    public ShareInfo setMetadata(Map<String, String> metadata) {
        return setMetadataWithResponse(metadata, Context.NONE).value();
    }

    /**
     * Sets the user-defined metadata to associate to the share.
     *
     * <p>If {@code null} is passed for the metadata it will clear the metadata associated to the share.</p>
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set the metadata to "share:updatedMetadata"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.setMetadataWithResponse#map-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/set-share-metadata">Azure Docs</a>.</p>
     *
     * @param metadata Metadata to set on the share, if null is passed the metadata for the share is cleared
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing {@link ShareProperties properties of the share} with response status code
     * @throws StorageErrorException If the share doesn't exist or the metadata contains invalid keys
     */
    public Response<ShareInfo> setMetadataWithResponse(Map<String, String> metadata, Context context) {
        return client.setMetadataWithResponse(metadata, context).block();
    }

    /**
     * Retrieves stored access policies specified for the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>List the stored access policies</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getAccessPolicy}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-acl">Azure Docs</a>.</p>
     *
     * @return The stored access policies specified on the queue.
     * @throws StorageErrorException If the share doesn't exist
     */
    public Iterable<SignedIdentifier> getAccessPolicy() {
        return client.getAccessPolicy().toIterable();
    }

    /**
     * Sets stored access policies for the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set a read only stored access policy</p>
     *
     * {@codesnippet com.azure.storage.file.ShareClient.setAccessPolicy#List}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/set-share-acl">Azure Docs</a>.</p>
     *
     * @param permissions Access policies to set on the queue
     * @return The {@link ShareInfo information of the share}
     * @throws StorageErrorException If the share doesn't exist, a stored access policy doesn't have all fields filled out,
     * or the share will have more than five policies.
     */
    public ShareInfo setAccessPolicy(List<SignedIdentifier> permissions) {
        return setAccessPolicyWithResponse(permissions, Context.NONE).value();
    }

    /**
     * Sets stored access policies for the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Set a read only stored access policy</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.setAccessPolicyWithResponse#List-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/set-share-acl">Azure Docs</a>.</p>
     *
     * @param permissions Access policies to set on the queue
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing the {@link ShareInfo information of the share} with headers and response status code
     * @throws StorageErrorException If the share doesn't exist, a stored access policy doesn't have all fields filled out,
     * or the share will have more than five policies.
     */
    public Response<ShareInfo> setAccessPolicyWithResponse(List<SignedIdentifier> permissions, Context context) {
        return client.setAccessPolicyWithResponse(permissions, context).block();
    }

    /**
     * Retrieves storage statistics about the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Retrieve the storage statistics</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getStatistics}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-stats">Azure Docs</a>.</p>
     *
     * @return The storage {@link ShareStatistics statistics of the share}
     */
    public ShareStatistics getStatistics() {
        return getStatisticsWithResponse(Context.NONE).value();
    }

    /**
     * Retrieves storage statistics about the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Retrieve the storage statistics</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getStatisticsWithResponse#Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/get-share-stats">Azure Docs</a>.</p>
     *
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing the {@link ShareStatistics statistics of the share}
     */
    public Response<ShareStatistics> getStatisticsWithResponse(Context context) {
        return client.getStatisticsWithResponse(context).block();
    }

    /**
     * Creates the directory in the share with the given name.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the directory "documents"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createDirectory#string}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-directory">Azure Docs</a>.</p>
     *
     * @param directoryName Name of the directory
     * @return A response containing a {@link DirectoryClient} to interact with the created directory.
     * @throws StorageErrorException If the share doesn't exist, the directory already exists or is in the process of
     * being deleted, or the parent directory for the new directory doesn't exist
     */
    public DirectoryClient createDirectory(String directoryName) {
        return createDirectoryWithResponse(directoryName, null, Context.NONE).value();
    }

    /**
     * Creates the directory in the share with the given name and associates the passed metadata to it.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the directory "documents" with metadata "directory:metadata"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createDirectoryWithResponse#string-map-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-directory">Azure Docs</a>.</p>
     *
     * @param directoryName Name of the directory
     * @param metadata Optional metadata to associate with the directory
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing a {@link DirectoryAsyncClient} to interact with the created directory and the
     * status of its creation.
     * @throws StorageErrorException If the share doesn't exist, the directory already exists or is in the process of
     * being deleted, the parent directory for the new directory doesn't exist, or the metadata is using an illegal
     * key name
     */
    public Response<DirectoryClient> createDirectoryWithResponse(String directoryName, Map<String, String> metadata, Context context) {
        DirectoryClient directoryClient = getDirectoryClient(directoryName);
        return new SimpleResponse<>(directoryClient.createWithResponse(metadata, context), directoryClient);
    }

    /**
     * Creates the file in the share with the given name and file max size.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the file "myfile" with size of 1024 bytes.</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createFile#string-long}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-file">Azure Docs</a>.</p>
     *
     * @param fileName Name of the file.
     * @param maxSize The maximum size in bytes for the file, up to 1 TiB.
     * @return A response containing a {@link FileClient} to interact with the created file.
     * @throws StorageErrorException If one of the following cases happen:
     * <ul>
     *     <li>
     *         If the share or parent directory does not exist.
     *     </li>
     *     <li>
     *          An attempt to create file on a share snapshot will fail with 400 (InvalidQueryParameterValue).
     *     </li>
     * </ul>
     */
    public FileClient createFile(String fileName, long maxSize) {
        return createFileWithResponse(fileName, maxSize, null, null, Context.NONE).value();
    }

    /**
     * Creates the file in the share with the given name, file max size and associates the passed httpHeaders and metadata to it.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Create the file "myfile" with length of 1024 bytes, some headers and metadata</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.createFileWithResponse#string-long-filehttpheaders-map-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/create-file">Azure Docs</a>.</p>
     *
     * @param fileName Name of the file.
     * @param maxSize The maximum size in bytes for the file, up to 1 TiB.
     * @param httpHeaders Additional parameters for the operation.
     * @param metadata Optional metadata to associate with the file.
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response containing a {@link FileClient} to interact with the created file and the
     * status of its creation.
     * @throws StorageErrorException If one of the following cases happen:
     * <ul>
     *     <li>
     *         If the share or parent directory does not exist.
     *     </li>
     *     <li>
     *          An attempt to create file on a share snapshot will fail with 400 (InvalidQueryParameterValue).
     *     </li>
     * </ul>
     */
    public Response<FileClient> createFileWithResponse(String fileName, long maxSize, FileHTTPHeaders httpHeaders, Map<String, String> metadata, Context context) {
        FileClient fileClient = getFileClient(fileName);
        return new SimpleResponse<>(fileClient.createWithResponse(maxSize, httpHeaders, metadata, context), fileClient);
    }

    /**
     * Deletes the specified directory in the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the directory "mydirectory"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.deleteDirectory#string}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-directory">Azure Docs</a>.</p>
     *
     * @param directoryName Name of the directory
     * @throws StorageErrorException If the share doesn't exist or the directory isn't empty
     */
    public void deleteDirectory(String directoryName) {
        deleteDirectoryWithResponse(directoryName, Context.NONE);
    }


    /**
     * Deletes the specified directory in the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the directory "mydirectory"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.deleteDirectoryWithResponse#string-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-directory">Azure Docs</a>.</p>
     *
     * @param directoryName Name of the directory
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response that only contains headers and response status code
     * @throws StorageErrorException If the share doesn't exist or the directory isn't empty
     */
    public VoidResponse deleteDirectoryWithResponse(String directoryName, Context context) {
        return client.deleteDirectoryWithResponse(directoryName, context).block();
    }

    /**
     * Deletes the specified file in the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the file "myfile"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.deleteFile#string}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-file2">Azure Docs</a>.</p>
     *
     * @param fileName Name of the file
     * @throws StorageErrorException If the share or the file doesn't exist.
     */
    public void deleteFile(String fileName) {
        deleteFileWithResponse(fileName, Context.NONE);
    }

    /**
     * Deletes the specified file in the share.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Delete the file "myfile"</p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.deleteFileWithResponse#string-Context}
     *
     * <p>For more information, see the
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/delete-file2">Azure Docs</a>.</p>
     *
     * @param fileName Name of the file
     * @param context Additional context that is passed through the Http pipeline during the service call.
     * @return A response that only contains headers and response status code
     * @throws StorageErrorException If the share or the file doesn't exist.
     */
    public VoidResponse deleteFileWithResponse(String fileName, Context context) {
        return client.deleteFileWithResponse(fileName, context).block();
    }

    /**
     * Get snapshot id which attached to {@link ShareClient}.
     * Return {@code null} if no snapshot id attached.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * <p>Get the share snapshot id. </p>
     *
     * {@codesnippet com.azure.storage.file.shareClient.getSnapshotId}
     *
     * @return The snapshot id which is a unique {@code DateTime} value that identifies the share snapshot to its base share.
     */
    public String getSnapshotId() {
        return client.getSnapshotId();
    }
}
