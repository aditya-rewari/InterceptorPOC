package com.example.InterceptorPOC.interceptor;

import com.example.InterceptorPOC.service.EncryptDecryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class SampleInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    EncryptDecryptService service;

    /**
     * The Request is modified here, as encryption has been applied on it
     *
     * The Response received has been decrypted
     * The decrypted response body is further used in a new object of ClientHttpResponse
     * This new object will now be returned as a Modified Response
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String reqBody = new String(body);
        byte[] encryptedRequestBody = service.doEncryption(reqBody);
        ClientHttpResponse response = execution.execute(request, encryptedRequestBody);
        String responseBody = new String(response.getBody().readAllBytes());
        byte[] decryptedResponseBodyBytes = service.doDecryption(responseBody);
        String decryptedResponseBody = new String(decryptedResponseBodyBytes);

        // prepare modified response
        ClientHttpResponse decryptedRes = new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                return response.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                // The expected modified response body to be populated here
                return new ByteArrayInputStream(decryptedResponseBody.getBytes());
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return response.getStatusCode();
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return response.getRawStatusCode();
            }

            @Override
            public String getStatusText() throws IOException {
                return response.getStatusText();
            }

            @Override
            public void close() {

            }
        };
        return decryptedRes;
    }
}
