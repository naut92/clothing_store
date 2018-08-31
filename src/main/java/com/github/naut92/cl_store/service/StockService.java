package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockService {
    Collection<ClothesInStoreOrInStock> getAllClothesInStock();
    Optional<ClothesInStoreOrInStock> findByIdInStock(Long id);
    ClothesInStoreOrInStock createClothesInStock (ClothesInStoreOrInStock stock);
    ClothesInStoreOrInStock updateClothesInStock(Long id, ClothesInStoreOrInStock clothesInStock);
    ClothesInStoreOrInStock moveClothesInStockToStore(Long id);
}
