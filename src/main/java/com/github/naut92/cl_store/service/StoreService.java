package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;

import java.util.Collection;
import java.util.Optional;

public interface StoreService {
    Collection <ClothesInStoreOrInStock> getAllClothesInStore();
    Optional<ClothesInStoreOrInStock> findByIdInStore(Long id);
    ClothesInStoreOrInStock saveInStore(ClothesInStoreOrInStock store);
    void deleteById(Long id);
}