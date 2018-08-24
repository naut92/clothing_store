package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.Stock;
import com.github.naut92.cl_store.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockRepository stockRepository;

    @Override
    public List<Stock> getAllClothesInStock() {
        return stockRepository.findAll();
    }
}
