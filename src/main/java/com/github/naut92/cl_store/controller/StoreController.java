package com.github.naut92.cl_store.controller;

import com.github.naut92.cl_store.model.Store;
import com.github.naut92.cl_store.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StoreController {

    private final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    StoreService storeService;

    public StoreController(StoreService storeService) {this.storeService = storeService;
    }

    @GetMapping("/store")
    public List<Store> getAllClothesInStore() {
        return storeService.getAllClothesInStore();
    }

    @GetMapping("/store/{id}")
    ResponseEntity<?> getClothes(@PathVariable Long id) {
        Optional<Store> clothes = storeService.findById(id);
        return clothes.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/store")
    ResponseEntity<Store> createClothesInStore(@Valid @RequestBody Store clothes) throws URISyntaxException {
        log.info("Request to create clothes in Store: {}", clothes);
        Store result = storeService.save(clothes);
        return ResponseEntity.created(new URI("/store/" + result.getId()))
                .body(result);
    }

    @PutMapping("/store/{id}")
    ResponseEntity<Store> updateClothesInStore(@PathVariable Long id, @Valid @RequestBody Store store) {
        store.setId(id);
        log.info("Request to update clothes in Store: {}", store);
        Store result = storeService.save(store);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/store/{id}")
    public ResponseEntity<?> deleteClothesInStore(@PathVariable Long id) {
        log.info("Request to delete clothes in Store: {}", id);
        storeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}