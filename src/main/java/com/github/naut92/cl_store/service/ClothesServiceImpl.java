package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.repository.ClothesInStoreOrInStockRepository;
import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClothesServiceImpl implements ClothesService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesRepository;

    public ClothesServiceImpl(StoreAndStockRepository store, ClothesInStoreOrInStockRepository clothes) {
        this.storeAndStockRepository = store;
        this.clothesRepository = clothes;
    }

    //delete entities from all relations:
    @Override
    public void deleteById(Long id) {
        storeAndStockRepository.deleteById(id);
        clothesRepository.deleteById(id);
    }
}