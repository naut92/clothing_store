package com.github.naut92.cl_store.controller;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreControllerTest {

    @Autowired
    private TestRestTemplate template;

    private static final String URI = "/api/store/";

    @Test
    public void getAllClothesInStore() {
        ResponseEntity<List<ClothesInStoreOrInStock>> clothesResponse = template.exchange(URI,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ClothesInStoreOrInStock>>() {});
        List<ClothesInStoreOrInStock> clothesInStore =clothesResponse.getBody();
        for (ClothesInStoreOrInStock clothes : clothesInStore){
            System.out.println(clothes.getName());
        }
        Assert.assertNotNull(clothesInStore);
    }

    @Test
    public void getClothesInStoreById() {
        ResponseEntity<ClothesInStoreOrInStock> responseEntity = template.getForEntity(URI + "{id}",
                ClothesInStoreOrInStock.class, new Long(1));
        int status = responseEntity.getStatusCodeValue();
        ClothesInStoreOrInStock resultById = responseEntity.getBody();

        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        Assert.assertNotNull(resultById);
        Assert.assertEquals(1l, resultById.getId().longValue());
    }

    @Test
    public void createClothesInStore(){
        HttpEntity<Object> article = getHttpEntity("{\"name\": \"clothes 1\", \"size\": \"42\" }");
        ResponseEntity<ClothesInStoreOrInStock> resultAsset = template.postForEntity(URI, article, ClothesInStoreOrInStock.class);
        Assert.assertNotNull(resultAsset.getBody().getId());
    }

    @Test
    public void updateClothesInStore() {
        ClothesInStoreOrInStock clothes = new ClothesInStoreOrInStock();
        Long idClothes = 4L;
        clothes.setId(idClothes);
        clothes.setName("test!");
        clothes.setSize(52L);
        clothes.setCost("1 рубль");
        clothes.setColor("test color");
        clothes.setType("джинсы!");
        clothes.setDescription("бла-бла-бла");
        StoreOrStock store = new StoreOrStock();
        store.setStoreOrStock("store");
        Collection<StoreOrStock> collection = new ArrayList<>();
        collection.add(store);
        clothes.setClothesByStoreOrStock(collection);

        HttpEntity<ClothesInStoreOrInStock> requestEntity = new HttpEntity<>(clothes);

        ResponseEntity<Void> responseEntity = template.exchange(URI + idClothes, HttpMethod.PUT, requestEntity, Void.class);

        int status = responseEntity.getStatusCodeValue();
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void deleteClothesInStore() {
        ResponseEntity<Void> responseEntity = template.exchange(URI + "{id}", HttpMethod.DELETE,
                null, Void.class, new Long(6));
        int status = responseEntity.getStatusCodeValue();
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        //Assert.assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

}