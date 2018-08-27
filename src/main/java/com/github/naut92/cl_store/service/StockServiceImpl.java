package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;
import com.github.naut92.cl_store.repository.ClothesInStoreOrInStockRepository;
import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository;

    @Override
    public Collection<ClothesInStoreOrInStock> getAllClothesInStock() {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> stockList = new ArrayList<>();
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("stock")) {
                stockList.add(storeOrStock);
            }
        }
        //return stockList.get(0);
        return stockList.get(0).getStoreOrStockByClothes();
    }

    @Override
    public Optional<ClothesInStoreOrInStock> findByIdInStock(Long id) {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> stockList = new ArrayList<>();
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("stock")) {
                stockList.add(storeOrStock);
            }
        }
        ClothesInStoreOrInStock storeOrInStock = new ClothesInStoreOrInStock();
        for (ClothesInStoreOrInStock inStock : stockList.get(0).getStoreOrStockByClothes()){
            if(id.equals(inStock.getId())){
                storeOrInStock = inStock;

            }
        }
        return Optional.ofNullable(storeOrInStock);
    }

    @Override
    public ClothesInStoreOrInStock saveInStock(ClothesInStoreOrInStock clothesInStock) {
        return clothesInStoreOrInStockRepository.save(clothesInStock);
    }

    @Override
    public void deleteById(Long id) {
        storeAndStockRepository.deleteById(id);
    }
}
