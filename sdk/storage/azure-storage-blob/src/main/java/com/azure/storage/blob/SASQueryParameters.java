// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.blob;

import com.azure.storage.blob.models.UserDelegationKey;
import com.azure.storage.common.Constants;
import com.azure.storage.common.IPRange;
import com.azure.storage.common.SASProtocol;
import com.azure.storage.common.Utility;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the components that make up an Azure Storage SAS' query parameters. This type is not constructed directly
 * by the user; it is only generated by the {@link AccountSASSignatureValues} and {@link ServiceSASSignatureValues}
 * types. Once generated, it can be set on a {@link BlobURLParts} object to be constructed as part of a URL or it can be
 * encoded into a {@code String} and appended to a URL directly (though caution should be taken here in case there are
 * existing query parameters, which might affect the appropriate means of appending these query parameters). NOTE:
 * Instances of this class are immutable to ensure thread safety.
 */
public final class SASQueryParameters {

    private final String version;

    private final String services;

    private final String resourceTypes;

    private final SASProtocol protocol;

    private final OffsetDateTime startTime;

    private final OffsetDateTime expiryTime;

    private final IPRange ipRange;

    private final String identifier;

    private final String keyOid;

    private final String keyTid;

    private final OffsetDateTime keyStart;

    private final OffsetDateTime keyExpiry;

    private final String keyService;

    private final String keyVersion;

    private final String resource;

    private final String permissions;

    private final String signature;

    private final String cacheControl;

    private final String contentDisposition;

    private final String contentEncoding;

    private final String contentLanguage;

    private final String contentType;

    /**
     * Creates a new {@link SASQueryParameters} object.
     *
     * @param queryParamsMap All query parameters for the request as key-value pairs
     * @param removeSASParametersFromMap When {@code true}, the SAS query parameters will be removed from
     * queryParamsMap
     */
    public SASQueryParameters(Map<String, String[]> queryParamsMap, boolean removeSASParametersFromMap) {
        this.version = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SERVICE_VERSION, removeSASParametersFromMap);
        this.services = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SERVICES, removeSASParametersFromMap);
        this.resourceTypes = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_RESOURCES_TYPES, removeSASParametersFromMap);
        this.protocol = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_PROTOCOL, removeSASParametersFromMap, SASProtocol::parse);
        this.startTime = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_START_TIME, removeSASParametersFromMap, Utility::parseDate);
        this.expiryTime = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_EXPIRY_TIME, removeSASParametersFromMap, Utility::parseDate);
        this.ipRange = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_IP_RANGE, removeSASParametersFromMap, IPRange::parse);
        this.identifier = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_IDENTIFIER, removeSASParametersFromMap);
        this.keyOid = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_OBJECT_ID, removeSASParametersFromMap);
        this.keyTid = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_TENANT_ID, removeSASParametersFromMap);
        this.keyStart = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_KEY_START, removeSASParametersFromMap, Utility::parseDate);
        this.keyExpiry = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_KEY_EXPIRY, removeSASParametersFromMap, Utility::parseDate);
        this.keyService = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_KEY_SERVICE, removeSASParametersFromMap);
        this.keyVersion = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_KEY_VERSION, removeSASParametersFromMap);
        this.resource = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_RESOURCE, removeSASParametersFromMap);
        this.permissions = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNED_PERMISSIONS, removeSASParametersFromMap);
        this.signature = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_SIGNATURE, removeSASParametersFromMap);
        this.cacheControl = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_CACHE_CONTROL, removeSASParametersFromMap);
        this.contentDisposition = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_CONTENT_DISPOSITION, removeSASParametersFromMap);
        this.contentEncoding = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_CONTENT_ENCODING, removeSASParametersFromMap);
        this.contentLanguage = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_CONTENT_LANGUAGE, removeSASParametersFromMap);
        this.contentType = getQueryParameter(queryParamsMap, Constants.UrlConstants.SAS_CONTENT_TYPE, removeSASParametersFromMap);
    }

    private String getQueryParameter(Map<String, String[]> parameters, String name, boolean remove) {
        return getQueryParameter(parameters, name, remove, value -> value);
    }

    private <T> T getQueryParameter(Map<String, String[]> parameters, String name, Boolean remove, Function<String, T> converter) {
        String[] parameterValue = parameters.get(name);
        if (parameterValue == null) {
            return null;
        }

        if (remove) {
            parameters.remove(name);
        }

        return converter.apply(parameterValue[0]);
    }


    /**
     * Creates a new {@link SASQueryParameters} object. These objects are only created internally by SASSignatureValues
     * classes.
     *
     * @param version A {@code String} representing the storage version.
     * @param services A {@code String} representing the storage services being accessed (only for Account SAS).
     * @param resourceTypes A {@code String} representing the storage resource types being accessed (only for Account
     * SAS).
     * @param protocol A {@code String} representing the allowed HTTP protocol(s) or {@code null}.
     * @param startTime A {@code java.util.Date} representing the start time for this SAS token or {@code null}.
     * @param expiryTime A {@code java.util.Date} representing the expiry time for this SAS token.
     * @param ipRange A {@link IPRange} representing the range of valid IP addresses for this SAS token or {@code null}.
     * @param identifier A {@code String} representing the signed identifier (only for Service SAS) or {@code null}.
     * @param resource A {@code String} representing the storage container or blob (only for Service SAS).
     * @param permissions A {@code String} representing the storage permissions or {@code null}.
     * @param signature A {@code String} representing the signature for the SAS token.
     */
    SASQueryParameters(String version, String services, String resourceTypes, SASProtocol protocol,
                       OffsetDateTime startTime, OffsetDateTime expiryTime, IPRange ipRange, String identifier,
                       String resource, String permissions, String signature, String cacheControl, String contentDisposition,
                       String contentEncoding, String contentLanguage, String contentType, UserDelegationKey key) {

        this.version = version;
        this.services = services;
        this.resourceTypes = resourceTypes;
        this.protocol = protocol;
        this.startTime = startTime;
        this.expiryTime = expiryTime;
        this.ipRange = ipRange;
        this.identifier = identifier;
        this.resource = resource;
        this.permissions = permissions;
        this.signature = signature;
        this.cacheControl = cacheControl;
        this.contentDisposition = contentDisposition;
        this.contentEncoding = contentEncoding;
        this.contentLanguage = contentLanguage;
        this.contentType = contentType;

        if (key != null) {
            this.keyOid = key.signedOid();
            this.keyTid = key.signedTid();
            this.keyStart = key.signedStart();
            this.keyExpiry = key.signedExpiry();
            this.keyService = key.signedService();
            this.keyVersion = key.signedVersion();
        } else {
            this.keyOid = null;
            this.keyTid = null;
            this.keyStart = null;
            this.keyExpiry = null;
            this.keyService = null;
            this.keyVersion = null;
        }
    }

    /**
     * @return The storage version
     */
    public String version() {
        return version;
    }

    /**
     * @return The storage services being accessed (only for Account SAS). Please refer to {@link AccountSASService} for
     * more details.
     */
    public String services() {
        return services;
    }

    /**
     * @return The storage resource types being accessed (only for Account SAS). Please refer to {@link
     * AccountSASResourceType} for more details.
     */
    public String resourceTypes() {
        return resourceTypes;
    }

    /**
     * @return The allowed HTTP protocol(s) or {@code null}. Please refer to {@link SASProtocol} for more details.
     */
    public SASProtocol protocol() {
        return protocol;
    }

    /**
     * @return The start time for this SAS token or {@code null}.
     */
    public OffsetDateTime startTime() {
        return startTime;
    }

    /**
     * @return The expiry time for this SAS token.
     */
    public OffsetDateTime expiryTime() {
        return expiryTime;
    }

    /**
     * @return {@link IPRange}
     */
    public IPRange ipRange() {
        return ipRange;
    }

    /**
     * @return The signed identifier (only for {@link ServiceSASSignatureValues}) or {@code null}. Please see
     * <a href="https://docs.microsoft.com/en-us/rest/api/storageservices/establishing-a-stored-access-policy">here</a>
     * for more information.
     */
    public String identifier() {
        return identifier;
    }

    /**
     * @return The storage container or blob (only for {@link ServiceSASSignatureValues}).
     */
    public String resource() {
        return resource;
    }

    /**
     * @return Please refer to {@link AccountSASPermission}, {@link BlobSASPermission}, or {@link
     * ContainerSASPermission} for more details.
     */
    public String permissions() {
        return permissions;
    }

    /**
     * @return The signature for the SAS token.
     */
    public String signature() {
        return signature;
    }

    /**
     * @return The Cache-Control header value when a client accesses the resource with this sas token.
     */
    public String cacheControl() {
        return cacheControl;
    }

    /**
     * @return The Content-Disposition header value when a client accesses the resource with this sas token.
     */
    public String contentDisposition() {
        return contentDisposition;
    }

    /**
     * @return The Content-Encoding header value when a client accesses the resource with this sas token.
     */
    public String contentEncoding() {
        return contentEncoding;
    }

    /**
     * @return The Content-Language header value when a client accesses the resource with this sas token.
     */
    public String contentLanguage() {
        return contentLanguage;
    }

    /**
     * @return The Content-Type header value when a client accesses the resource with this sas token.
     */
    public String contentType() {
        return contentType;
    }

    /**
     * @return the object ID of the key.
     */
    public String keyOid() {
        return keyOid;
    }

    /**
     * @return the tenant ID of the key.
     */
    public String keyTid() {
        return keyTid;
    }

    /**
     * @return the datetime when the key becomes active.
     */
    public OffsetDateTime keyStart() {
        return keyStart;
    }

    /**
     * @return the datetime when the key expires.
     */
    public OffsetDateTime keyExpiry() {
        return keyExpiry;
    }

    /**
     * @return the services that are permitted by the key.
     */
    public String keyService() {
        return keyService;
    }

    /**
     * @return the service version that created the key.
     */
    public String keyVersion() {
        return keyVersion;
    }

    UserDelegationKey userDelegationKey() {
        return new UserDelegationKey()
            .signedExpiry(this.keyExpiry)
            .signedOid(this.keyOid)
            .signedService(this.keyService)
            .signedStart(this.keyStart)
            .signedTid(this.keyTid)
            .signedVersion(this.keyVersion);
    }

    private void tryAppendQueryParameter(StringBuilder sb, String param, Object value) {
        if (value != null) {
            if (sb.length() != 0) {
                sb.append('&');
            }
            sb.append(Utility.urlEncode(param)).append('=').append(Utility.urlEncode(value.toString()));
        }
    }

    private String formatQueryParameterDate(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            return Utility.ISO_8601_UTC_DATE_FORMATTER.format(dateTime);
        }
    }

    /**
     * Encodes all SAS query parameters into a string that can be appended to a URL.
     *
     * @return A {@code String} representing all SAS query parameters.
     */
    public String encode() {
        /*
         We should be url-encoding each key and each value, but because we know all the keys and values will encode to
         themselves, we cheat except for the signature value.
         */
        StringBuilder sb = new StringBuilder();

        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SERVICE_VERSION, this.version);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SERVICES, this.services);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_RESOURCES_TYPES, this.resourceTypes);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_PROTOCOL, this.protocol);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_START_TIME, formatQueryParameterDate(this.startTime));
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_EXPIRY_TIME, formatQueryParameterDate(this.expiryTime));
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_IP_RANGE, this.ipRange);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_IDENTIFIER, this.identifier);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_OBJECT_ID, this.keyOid);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_TENANT_ID, this.keyTid);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_KEY_START, formatQueryParameterDate(this.keyStart));
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_KEY_EXPIRY, formatQueryParameterDate(this.keyExpiry));
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_KEY_SERVICE, this.keyService);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_KEY_VERSION, this.keyVersion);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_RESOURCE, this.resource);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNED_PERMISSIONS, this.permissions);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_SIGNATURE, this.signature);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_CACHE_CONTROL, this.cacheControl);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_CONTENT_DISPOSITION, this.contentDisposition);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_CONTENT_ENCODING, this.contentEncoding);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_CONTENT_LANGUAGE, this.contentLanguage);
        tryAppendQueryParameter(sb, Constants.UrlConstants.SAS_CONTENT_TYPE, this.contentType);

        return sb.toString();
    }
}
