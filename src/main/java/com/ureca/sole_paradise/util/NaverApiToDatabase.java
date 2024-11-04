package com.ureca.sole_paradise.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverApiToDatabase {

    public void fetchDataAndSave() {
        String naverApiUrl = "https://openapi.naver.com/v1/search/shop.json?query=강아지&display=100";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", "pbrU5z9Fz5jVwPM575m5");
        headers.set("X-Naver-Client-Secret", "En9XTv8UVY");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(naverApiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                System.out.println("Naver API data retrieved successfully.");

                String saveProductsUrl = "http://localhost:8080/api/products/saveProducts";
                HttpEntity<String> request = new HttpEntity<>(response.getBody(), headers);

                ResponseEntity<Void> saveResponse = restTemplate.postForEntity(saveProductsUrl, request, Void.class);

                if (saveResponse.getStatusCode() == HttpStatus.CREATED) {
                    System.out.println("Products saved successfully in the database.");
                } else {
                    System.out.println("Failed to save products. Status code: " + saveResponse.getStatusCode());
                }
            } else {
                System.out.println("Failed to retrieve data from Naver API. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while executing the API requests.");
        }
    }
}
