package com.example.InterceptorPOC.service;

import com.example.InterceptorPOC.model.SampleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SampleService {

    private static String HTTP_URI_1 = "https://someThirdPartyApi/v1/sample";
    private static String HTTP_URI_2 = "https://someThirdPartyApi/v1/sample";

    @Autowired
    RestTemplate plainRestTemplate; // to make regular REST calls

    @Autowired
    @Qualifier("interceptedRestTemplate")
    RestTemplate interceptedRestTemplate; // to make REST calls in which req-res will be modified by interceptor

    /**
    * Service to make REST call without interceptor
    * i.e., no modifying of request and response objects
    * */
    public SampleData doPlainRestCall(SampleData data) {
        HttpEntity<SampleData> entity = new HttpEntity<>(data, this.getHeaders());
        ResponseEntity<SampleData> response =
                plainRestTemplate
                        .exchange(
                                HTTP_URI_1,
                                HttpMethod.POST,
                                entity,
                                SampleData.class);
        SampleData responseBody = response.getBody();
        return responseBody;
    }

    /**
     * Request and Response will be modified by Interceptor applied at the used restTemplate
     * */
    public SampleData doInterceptedRestCall(SampleData data) {
        HttpEntity<SampleData> entity = new HttpEntity<>(data, this.getHeaders());
        ResponseEntity<SampleData> response =
                interceptedRestTemplate
                        .exchange(
                                HTTP_URI_2,
                                HttpMethod.POST,
                                entity,
                                SampleData.class);
        SampleData responseBody = response.getBody();
        return responseBody;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
