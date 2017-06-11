package io.github.xiaobaxi.feign.ssl.client;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import feign.httpclient.ApacheHttpClient;

/**
 * init httpClient
 * @author xiaobaxi
 *
 */
@Configuration
@ConditionalOnClass(ApacheHttpClient.class)
@ConditionalOnProperty(value = "feign.httpclient.enabled", matchIfMissing = true)
public class HttpClientConfig {

	@Bean
	public HttpClient httpClient(@Value("${server.ssl.trust-store}") String file,
			@Value("${server.ssl.trust-store-password}") String password) throws Exception {

		// load trustStore and init the SSLContext
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(ResourceUtils.getFile(file), password.toCharArray(), new TrustSelfSignedStrategy())
				.build();
		
		// SSLSocketFactory
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
		
		//HttpClient build
		return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
	}
}
