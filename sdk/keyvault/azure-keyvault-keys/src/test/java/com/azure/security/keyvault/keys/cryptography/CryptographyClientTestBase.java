// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.security.keyvault.keys.cryptography;

import com.azure.core.credentials.AccessToken;
import com.azure.core.credentials.TokenCredential;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.*;
import com.azure.core.implementation.http.policy.spi.HttpPolicyProviders;
import com.azure.core.test.TestBase;
import com.azure.core.util.configuration.ConfigurationManager;
import com.azure.identity.credential.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.implementation.AzureKeyVaultConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;

public abstract class CryptographyClientTestBase extends TestBase {
    @Rule
    public TestName testName = new TestName();

    @Override
    protected String testName() {
        return testName.getMethodName();
    }

    void beforeTestSetup() {
    }

    <T> T clientSetup(Function<HttpPipeline, T> clientBuilder) {
        final String endpoint = interceptorManager.isPlaybackMode()
            ? "http://localhost:8080"
            : System.getenv("AZURE_KEYVAULT_ENDPOINT");

        TokenCredential credential;
        HttpClient httpClient;

        String tenantId = System.getenv("AZURE_TENANT_ID");
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");
        if (!interceptorManager.isPlaybackMode()) {
            assertNotNull(tenantId);
            assertNotNull(clientId);
            assertNotNull(clientSecret);
        }

        if (interceptorManager.isPlaybackMode()) {
            credential = resource -> Mono.just(new AccessToken("Some fake token",
                OffsetDateTime.now(ZoneOffset.UTC).plus(Duration.ofMinutes(30))));
        } else {
            credential = new DefaultAzureCredentialBuilder().build();
        }

        // Closest to API goes first, closest to wire goes last.
        final List<HttpPipelinePolicy> policies = new ArrayList<>();
        policies.add(new UserAgentPolicy(AzureKeyVaultConfiguration.SDK_NAME, AzureKeyVaultConfiguration.SDK_VERSION,
            ConfigurationManager.getConfiguration().clone()));
        HttpPolicyProviders.addBeforeRetryPolicies(policies);
        policies.add(new RetryPolicy());
        policies.add(new BearerTokenAuthenticationPolicy(credential, CryptographyAsyncClient.KEY_VAULT_SCOPE));
        policies.addAll(policies);
        HttpPolicyProviders.addAfterRetryPolicies(policies);
        policies.add(new HttpLoggingPolicy(HttpLogDetailLevel.BODY_AND_HEADERS));

        if (interceptorManager.isPlaybackMode()) {
            httpClient = interceptorManager.getPlaybackClient();
            policies.add(interceptorManager.getRecordPolicy());
        } else {
            httpClient = HttpClient.createDefault().wiretap(true);
            policies.add(interceptorManager.getRecordPolicy());
        }

        HttpPipeline pipeline = new HttpPipelineBuilder()
            .policies(policies.toArray(new HttpPipelinePolicy[0]))
            .httpClient(httpClient)
            .build();

        T client;
        client = clientBuilder.apply(pipeline);

        return Objects.requireNonNull(client);
    }

    @Test
    public abstract void encryptDecryptRsa() throws Exception;

    void encryptDecryptRsaRunner(Consumer<KeyPair> testRunner) throws Exception {
        final Map<String, String> tags = new HashMap<>();

        testRunner.accept(getWellKnownKey());
    }

    @Test
    public abstract void signVerifyEc() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;

    @Test
    public abstract void wrapUnwraptRsa() throws Exception;

    @Test
    public abstract void signVerifyRsa() throws Exception;

    @Test
    public abstract void wrapUnwrapSymmetricKeyAES128Kw();

    @Test
    public abstract void wrapUnwrapSymmetricKeyAES192Kw();

    @Test
    public abstract void wrapUnwrapSymmetricKeyAES256Kw();

    @Test
    public abstract void encryptDecryptSymmetricKeyAes128CbcHmacSha256();

    @Test
    public abstract void encryptDecryptSymmetricKeyAes128CbcHmacSha384();

    @Test
    public abstract void encryptDecryptSymmetricKeyAes128CbcHmacSha512();

    @Test
    public abstract void encryptDecryptSymmetricKeyaes128CbcOneBlock();

    @Test
    public abstract void encryptDecryptSymmetricKeyaes128CbcTwoBlock();

    private static KeyPair getWellKnownKey() throws Exception {
        BigInteger modulus = new BigInteger(
            "272667837130401637534737343340212305926316524508928506486201199149580661814004323642132981818464623"
                + "85257448168605902438305568194683691563208578540343969522651422088760509452879461613852042845039552"
                + "54783400216873735026418981081573592273444783072509916386921536040116245000867386970777411978588111"
                + "50444061013464509110548194483757124327469683017390076249524833472789547554601527958018942833895400"
                + "36131881712321193750961817346255102052653789197325341350920441746054233522546543768770643593655942"
                + "24689165263411492227713893727303490243432143167205822063182505378826281048054354159728437626143832"
                + "4665363067125951152574540779");
        BigInteger publicExponent = new BigInteger("65537");
        BigInteger privateExponent = new BigInteger(
            "1046661394126907547715242892779608615009589210227980291693755217206463632643378056649700081420741648"
                + "573968328696184884325576665202340095908629034498730856281706250647646575684099998198995745689702036"
                + "171719780519287609436231549645953596030492817112958581347713233153857751908400659533505548702887241"
                + "057912769220964293872485060355488547876320539486810329847347681162723154350419065248329094421800408"
                + "645780543182432844842203488714811599050170134553582511096280447127049959023411610021684117034468638"
                + "190232836237662440580364858883057555805825774207396303626427358275662046965946427820723334578435522"
                + "0317478103481872995809");
        BigInteger primeP = new BigInteger(
            "1750029411045688427150963391075667715920091121281842319615299539781427507323177249517477977646382172"
                + "876187690072955052149231879713505182176706040440043813624951868640513944041656027442352991007905517"
                + "751473221532067305624503018742368754593361545698932555705769670362376615945958032048080641278452574"
                + "96057219227");
        BigInteger primeQ = new BigInteger(
            "1558075740952693248971444286221853802839671591906263453350836901141473155099626987650449500019095538"
                + "615714930352405420314202131442370332086121327045621747728943690539167299019824205359409398216732771"
                + "401801135939515225222223489105362026642524814052410424141836687233383006499547084326812416213746449"
                + "26879028977");
        BigInteger primeExponentP = new BigInteger(
            "7974560680450499593883816883757837659373728007989523327737202718469345725117012585194617136034844013"
                + "423633852074206887313221669555231206879342843233817301691496804107650399752813769861060122291238595"
                + "317148524929987337713071723106352211296847460328199619084960470528406130675815290459416859352687443"
                + "5238915345");
        BigInteger primeExponentQ = new BigInteger(
            "8061996498382101830396668628418951784197644590556983073161760555809465822754085597176311548460800587"
                + "454034973096177763442774078664299606538666756403875534009217615983902570618316161548885683343397624"
                + "396368207401147565880467634931707537036278586040143719284346842359468870013296485436705349073707347"
                + "1709030801");
        BigInteger crtCoefficient = new BigInteger(
            "2157818511040667226980891229484210846757728661751992467240662009652654684725325675037512595031058612"
                + "950802328971801913498711880111052682274056041470625863586779333188842602381844572406517251106159327"
                + "934511268610438516820278066686225397795046020275055545005189953702783748235257613991379770525910232"
                + "674719428");

        KeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeySpec privateKeySpec = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ,
            primeExponentP, primeExponentQ, crtCoefficient);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return new KeyPair(keyFactory.generatePublic(publicKeySpec), keyFactory.generatePrivate(privateKeySpec));
    }


    public String getEndpoint() {
        final String endpoint = interceptorManager.isPlaybackMode()
            ? "http://localhost:8080"
            : "https://cameravault.vault.azure.net";
        //    : System.getenv("AZURE_KEYVAULT_ENDPOINT");
        Objects.requireNonNull(endpoint);
        return endpoint;
    }

    static void assertRestException(Runnable exceptionThrower, int expectedStatusCode) {
        assertRestException(exceptionThrower, HttpResponseException.class, expectedStatusCode);
    }

    static void assertRestException(Runnable exceptionThrower,
                                    Class<? extends HttpResponseException> expectedExceptionType,
                                    int expectedStatusCode) {
        try {
            exceptionThrower.run();
            fail();
        } catch (Throwable ex) {
            assertRestException(ex, expectedExceptionType, expectedStatusCode);
        }
    }

    /**
     * Helper method to verify the error was a HttpRequestException and it has a specific HTTP response code.
     *
     * @param exception Expected error thrown during the test
     * @param expectedStatusCode Expected HTTP status code contained in the error response
     */
    static void assertRestException(Throwable exception, int expectedStatusCode) {
        assertRestException(exception, HttpResponseException.class, expectedStatusCode);
    }

    static void assertRestException(Throwable exception, Class<? extends HttpResponseException> expectedExceptionType,
                                    int expectedStatusCode) {
        assertEquals(expectedExceptionType, exception.getClass());
        assertEquals(expectedStatusCode, ((HttpResponseException) exception).response().statusCode());
    }

    /**
     * Helper method to verify that a command throws an IllegalArgumentException.
     *
     * @param exceptionThrower Command that should throw the exception
     */
    static <T> void assertRunnableThrowsException(Runnable exceptionThrower, Class<T> exception) {
        try {
            exceptionThrower.run();
            fail();
        } catch (Exception ex) {
            assertEquals(exception, ex.getClass());
        }
    }

    public void sleepInRecordMode(long millis) {
        if (interceptorManager.isPlaybackMode()) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
