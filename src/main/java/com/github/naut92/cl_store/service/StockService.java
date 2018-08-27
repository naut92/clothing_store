package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;

import java.util.Collection;
import java.util.Optional;

public interface StockService {
    Collection<ClothesInStoreOrInStock> getAllClothesInStock();
    Optional<ClothesInStoreOrInStock> findByIdInStock(Long id);
    ClothesInStoreOrInStock saveInStock(ClothesInStoreOrInStock stock);
    void deleteById(Long id);
}
