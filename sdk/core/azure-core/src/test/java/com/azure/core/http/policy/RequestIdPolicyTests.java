// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.policy;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.clients.NoOpHttpClient;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class RequestIdPolicyTests {
    private final HttpResponse mockResponse = new HttpResponse() {
        @Override
        public int statusCode() {
            return 500;
        }

        @Override
        public String headerValue(String name) {
            return null;
        }

        @Override
        public HttpHeaders headers() {
            return new HttpHeaders();
        }

        @Override
        public Mono<byte[]> bodyAsByteArray() {
            return Mono.empty();
        }

        @Override
        public Flux<ByteBuffer> body() {
            return Flux.empty();
        }

        @Override
        public Mono<String> bodyAsString() {
            return Mono.empty();
        }

        @Override
        public Mono<String> bodyAsString(Charset charset) {
            return Mono.empty();
        }
    };

    private static final String REQUEST_ID_HEADER = "x-ms-client-request-id";

    @Test
    public void newRequestIdForEachCall() throws Exception {
        HttpPipeline pipeline = new HttpPipelineBuilder()
            .httpClient(new NoOpHttpClient() {
                String firstRequestId = null;
                @Override
                public Mono<HttpResponse> send(HttpRequest request) {
                    if (firstRequestId != null) {
                        String newRequestId = request.headers().value(REQUEST_ID_HEADER);
                        Assert.assertNotNull(newRequestId);
                        Assert.assertNotEquals(newRequestId, firstRequestId);
                    }

                    firstRequestId = request.headers().value(REQUEST_ID_HEADER);
                    if (firstRequestId == null) {
                        Assert.fail();
                    }
                    return Mono.just(mockResponse);
                }
            })
            .policies(new RequestIdPolicy())
            .build();

        pipeline.send(new HttpRequest(HttpMethod.GET, new URL("http://localhost/"))).block();
        pipeline.send(new HttpRequest(HttpMethod.GET, new URL("http://localhost/"))).block();
    }

    @Test
    public void sameRequestIdForRetry() throws Exception {
        final HttpPipeline pipeline = new HttpPipelineBuilder()
            .httpClient(new NoOpHttpClient() {
                String firstRequestId = null;

                @Override
                public Mono<HttpResponse> send(HttpRequest request) {
                    if (firstRequestId != null) {
                        String newRequestId = request.headers().value(REQUEST_ID_HEADER);
                        Assert.assertNotNull(newRequestId);
                        Assert.assertEquals(newRequestId, firstRequestId);
                    }
                    firstRequestId = request.headers().value(REQUEST_ID_HEADER);
                    if (firstRequestId == null) {
                        Assert.fail();
                    }
                    return Mono.just(mockResponse);
                }
            })
            .policies(new RequestIdPolicy(), new RetryPolicy(1, Duration.of(0, ChronoUnit.SECONDS)))
            .build();

        pipeline.send(new HttpRequest(HttpMethod.GET, new URL("http://localhost/"))).block();
    }
}
