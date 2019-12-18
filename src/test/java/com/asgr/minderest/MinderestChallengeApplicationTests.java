package com.asgr.minderest;

import com.asgr.minderest.model.ClientProduct;
import com.asgr.minderest.rest.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MinderestChallengeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class MinderestChallengeApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testCreateProductNotFoundClientId() {
		HttpEntity<ProductInfo> request = new HttpEntity<>(
				new ProductInfo(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

		ResponseEntity<ClientProduct> responseEntity =
				restTemplate.postForEntity(buildUri(), request, ClientProduct.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testCreateProduct() {
		ProductInfo productInfo = new ProductInfo("MEDIAM", "Xiaomi MI8");
		createProduct(productInfo);
	}

	private void createProduct(ProductInfo productInfo) {
		HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
		ResponseEntity<ProductInfo> responseEntity =
				restTemplate.postForEntity(buildUri(), request, ProductInfo.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertTrue(responseEntity.hasBody());
		assertThat(responseEntity.getBody()).isEqualTo(productInfo);
	}

	@Test
	public void testAlreadyExistingProductName() {
		ProductInfo productInfo = new ProductInfo("MEDIAM", "Iphone X");
		HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);

		createProduct(productInfo);

		ResponseEntity<ProductInfo> responseEntity = restTemplate.postForEntity(
				buildUri(),
				request,
				ProductInfo.class);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}

	private String buildUri() {
		return String.format("http://localhost:%d/api/v1/product", port);
	}

}
