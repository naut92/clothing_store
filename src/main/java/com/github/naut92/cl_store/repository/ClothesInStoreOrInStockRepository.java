package com.github.naut92.cl_store.repository;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesInStoreOrInStockRepository extends JpaRepository<ClothesInStoreOrInStock, Long> {
}
