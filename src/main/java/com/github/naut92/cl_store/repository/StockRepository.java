package com.github.naut92.cl_store.repository;

import com.github.naut92.cl_store.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
}