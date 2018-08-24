package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAllClothesInStock();
}
