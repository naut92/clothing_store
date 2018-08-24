package com.github.naut92.cl_store.repository;

import com.github.naut92.cl_store.model.ClothesInStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothesRepository extends JpaRepository<ClothesInStore, Long> {
    @Override
    List<ClothesInStore> findAll();
}
