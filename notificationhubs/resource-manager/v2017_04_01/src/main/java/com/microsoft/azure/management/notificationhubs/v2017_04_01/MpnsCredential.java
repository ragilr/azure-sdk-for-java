/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.notificationhubs.v2017_04_01;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Description of a NotificationHub MpnsCredential.
 */
@JsonFlatten
public class MpnsCredential {
    /**
     * The MPNS certificate.
     */
    @JsonProperty(value = "properties.mpnsCertificate")
    private String mpnsCertificate;

    /**
     * The certificate key for this credential.
     */
    @JsonProperty(value = "properties.certificateKey")
    private String certificateKey;

    /**
     * The MPNS certificate Thumbprint.
     */
    @JsonProperty(value = "properties.thumbprint")
    private String thumbprint;

    /**
     * Get the MPNS certificate.
     *
     * @return the mpnsCertificate value
     */
    public String mpnsCertificate() {
        return this.mpnsCertificate;
    }

    /**
     * Set the MPNS certificate.
     *
     * @param mpnsCertificate the mpnsCertificate value to set
     * @return the MpnsCredential object itself.
     */
    public MpnsCredential withMpnsCertificate(String mpnsCertificate) {
        this.mpnsCertificate = mpnsCertificate;
        return this;
    }

    /**
     * Get the certificate key for this credential.
     *
     * @return the certificateKey value
     */
    public String certificateKey() {
        return this.certificateKey;
    }

    /**
     * Set the certificate key for this credential.
     *
     * @param certificateKey the certificateKey value to set
     * @return the MpnsCredential object itself.
     */
    public MpnsCredential withCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    /**
     * Get the MPNS certificate Thumbprint.
     *
     * @return the thumbprint value
     */
    public String thumbprint() {
        return this.thumbprint;
    }

    /**
     * Set the MPNS certificate Thumbprint.
     *
     * @param thumbprint the thumbprint value to set
     * @return the MpnsCredential object itself.
     */
    public MpnsCredential withThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
        return this;
    }

}
