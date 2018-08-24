package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStore;
import com.github.naut92.cl_store.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {
    @Autowired
    ClothesRepository clothesRepository;

    @Override
    public List<ClothesInStore> findAll() {
        return clothesRepository.findAll();
    }
}
