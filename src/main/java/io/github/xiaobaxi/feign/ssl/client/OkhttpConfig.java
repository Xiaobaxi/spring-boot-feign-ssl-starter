package io.github.xiaobaxi.feign.ssl.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import okhttp3.OkHttpClient;

/**
 * init OkHttpClient
 * @author xiaobaxi
 *
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
public class OkhttpConfig {

	@Bean
	public okhttp3.OkHttpClient okHttpClient(@Value("${server.ssl.trust-store}") String file,
			@Value("${server.ssl.trust-store-password}") String password) throws KeyStoreException, IOException,
			CertificateException, NoSuchAlgorithmException, KeyManagementException {
		// load trustStore
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		try (FileInputStream instream = new FileInputStream(ResourceUtils.getFile(file))) {
			trustStore.load(instream, password.toCharArray());
		}

		// X509TrustManager
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

		//init the SSLContext
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { trustManager }, null);
		
		// SSLSocketFactory
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		// OkHttpClient build
		return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();
	}
}
