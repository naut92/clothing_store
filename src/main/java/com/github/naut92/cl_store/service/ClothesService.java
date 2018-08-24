package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStore;

import java.util.List;

public interface ClothesService {
    List<ClothesInStore> findAll();
}
