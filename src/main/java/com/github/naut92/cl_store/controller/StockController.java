package com.github.naut92.cl_store.controller;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.service.ClothesService;
import com.github.naut92.cl_store.service.StockService;
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
public class StockController {
    private final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    StockService stockService;
    @Autowired
    ClothesService clothesService;

    public StockController(StockService stockService, ClothesService clothesService) {
        this.stockService = stockService;
        this.clothesService = clothesService;
    }


    @GetMapping("/stock")
    public Collection<ClothesInStoreOrInStock> getAllClothesInStock(){
        return stockService.getAllClothesInStock();
    }

    @GetMapping("/stock/{id}")
    ResponseEntity<?> getClothesInStock(@PathVariable Long id) {
        Optional<ClothesInStoreOrInStock> clothes = stockService.findByIdInStock(id);
        return clothes.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/stock")
    ResponseEntity<ClothesInStoreOrInStock> createClothesInStock
            (@Valid @RequestBody ClothesInStoreOrInStock clothesInStock) throws URISyntaxException {
        log.info("Request to create clothes in StoreOrStock: {}", clothesInStock);
        ClothesInStoreOrInStock result = stockService.createClothesInStock(clothesInStock);
        return ResponseEntity.created(new URI("/stock/" + result.getId()))
                .body(result);
    }

    @PutMapping("/stock/{id}")
    ResponseEntity <ClothesInStoreOrInStock> updateClothesInStock
            (@PathVariable Long id, @Valid @RequestBody ClothesInStoreOrInStock clothesInStock) {
        log.info("Request to update clothes in StoreOrStock: {}", clothesInStock);
        ClothesInStoreOrInStock result = stockService.updateClothesInStock(id, clothesInStock);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/stock/move/{id}")
    ResponseEntity <ClothesInStoreOrInStock> moveClothesInStoreToStock
            (@PathVariable Long id) {
        log.info("Request to move clothes from Stock to -> Store: {}", id);
        ClothesInStoreOrInStock result = stockService.moveClothesInStockToStore(id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<?> deleteClothesInStock (@PathVariable Long id) {
        log.info("Request to delete clothes in Stock: {}", id);
        try {
            clothesService.deleteById(id);
        }catch (EmptyResultDataAccessException ex) {
            // either do nothing to return a 204
        }
        return ResponseEntity.ok().build();
    }
}
