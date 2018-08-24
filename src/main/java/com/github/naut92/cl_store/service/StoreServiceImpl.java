package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.Store;
import com.github.naut92.cl_store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;

    public List<Store> getAllClothesInStore(){
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    public void deleteById(Long id){
        storeRepository.deleteById(id);
    }
}