package com.github.naut92.cl_store.controller;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.service.ClothesService;
import com.github.naut92.cl_store.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StoreController {

    private final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    StoreService storeService;

    @Autowired
    ClothesService clothesService;

    public StoreController(StoreService storeService, ClothesService clothesService) {
        this.storeService = storeService;
        this.clothesService = clothesService;
    }

    @GetMapping("/store")
    public Collection<ClothesInStoreOrInStock> getAllClothesInStore(){
        return storeService.getAllClothesInStore();
    }

    @GetMapping("/store/{id}")
    ResponseEntity<?> getClothesInStoreById(@PathVariable Long id) {
        Optional<ClothesInStoreOrInStock> clothes = storeService.findByIdInStore(id);
        return clothes.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/store")
    ResponseEntity<ClothesInStoreOrInStock> createClothesInStore
    (@Valid @RequestBody ClothesInStoreOrInStock clothesInStore) throws URISyntaxException {
        log.info("Request to create clothes in StoreOrStock: {}", clothesInStore);
        ClothesInStoreOrInStock result = storeService.createClothesInStore(clothesInStore);
        return ResponseEntity.created(new URI("/store/" + result.getId()))
                .body(result);
    }

    @PutMapping("/store/{id}")
    ResponseEntity <ClothesInStoreOrInStock> updateClothesInStore
            (@PathVariable Long id, @Valid @RequestBody ClothesInStoreOrInStock clothesInStore) {
        log.info("Request to update clothes in StoreOrStock: {}", clothesInStore);
        ClothesInStoreOrInStock result = storeService.updateClothesInStore(id, clothesInStore);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/store/move/{id}")
    ResponseEntity <ClothesInStoreOrInStock> moveClothesInStoreToStock
            (@PathVariable Long id) {
        log.info("Request to move clothes from Store to -> Stock: {}", id);
        ClothesInStoreOrInStock result = storeService.moveClothesInStoreToStock(id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/store/{id}")
    public ResponseEntity<?> deleteClothesInStore(@PathVariable Long id) {
        log.info("Request to delete clothes in Store: {}", id);
        try {
            clothesService.deleteById(id);
        }catch (EmptyResultDataAccessException ex) {
            // either do nothing to return a 204
        }
        return ResponseEntity.ok().build();
    }
}