// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.blob;

import com.azure.storage.blob.models.ReliableDownloadOptions;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class DownloadResponse {
    private final DownloadAsyncResponse asyncResponse;

    DownloadResponse(DownloadAsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    /**
     * Gets the body of the download response.
     *
     * @param outputStream Stream that has the response body read into it
     * @param options Options for the download
     * @throws IOException If an I/O error occurs
     */
    public void body(OutputStream outputStream, ReliableDownloadOptions options) throws IOException {
        for (ByteBuffer buffer : this.asyncResponse.body(options).toIterable()) {
            if (buffer.hasArray()) {
                outputStream.write(buffer.array());
            }
        }
    }

    //TODO (unknown): determine signature(s) to use
    /*public InputStream body(ReliableDownloadOptions options) {
        return new InputStream() {
            DownloadAsyncResponse response = asyncResponse;
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }*/
}
