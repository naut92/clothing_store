package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StoreService {
    Collection<ClothesInStoreOrInStock> getAllClothesInStore();
    Optional<ClothesInStoreOrInStock> findByIdInStore(Long id);
    ClothesInStoreOrInStock createClothesInStore(ClothesInStoreOrInStock clothesInStoreOrInStock);
    ClothesInStoreOrInStock updateClothesInStore(Long id, ClothesInStoreOrInStock clothesInStore);
}