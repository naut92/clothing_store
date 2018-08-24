package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> getAllClothesInStore();
    Optional<Store> findById(Long id);
    Store save(Store store);
    void deleteById(Long id);
}