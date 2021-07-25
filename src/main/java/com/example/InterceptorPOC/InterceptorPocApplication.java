package com.example.InterceptorPOC;

import com.example.InterceptorPOC.interceptor.SampleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class InterceptorPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterceptorPocApplication.class, args);
	}

	@Autowired
	SampleInterceptor interceptor;

	@Bean
	@Primary
	// This RestTemplate bean will be used for regular REST call
	public RestTemplate getPlainRestTemplate() {
		RestTemplate restTemplate =
				new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		return restTemplate;
	}

	// This RestTemplate bean will be used if we need to modify req-res through interceptors
	@Bean("interceptedRestTemplate")
	public RestTemplate getInterceptedRestTemplate() {
		RestTemplate restTemplate =
				new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(interceptor);
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

}
