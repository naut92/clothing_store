package com.github.naut92.cl_store.repository;

import com.github.naut92.cl_store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}