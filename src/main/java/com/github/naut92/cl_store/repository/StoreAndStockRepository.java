package com.github.naut92.cl_store.repository;

import com.github.naut92.cl_store.model.StoreOrStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAndStockRepository extends JpaRepository<StoreOrStock, Long> {
}