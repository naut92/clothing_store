package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;
import com.github.naut92.cl_store.repository.ClothesInStoreOrInStockRepository;
import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesRepository;

    public StockServiceImpl(StoreAndStockRepository storeAndStockRepository,
                            ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository) {
        this.storeAndStockRepository = storeAndStockRepository;
        this.clothesRepository = clothesInStoreOrInStockRepository;
    }

    @Override
    public List<ClothesInStoreOrInStock> getAllClothesInStock() {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> stockList = new ArrayList<>();
        //sort entities related by store:
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("stock")) {
                stockList.add(storeOrStock);
            }
        }

        List<ClothesInStoreOrInStock> clothes = new ArrayList<>();
        for (StoreOrStock store : stockList){
            for (ClothesInStoreOrInStock storeOrInStock : store.getStoreOrStockByClothes()){
                clothes.add(storeOrInStock);
            }
        }
        return clothes;
    }

    @Override
    public Optional<ClothesInStoreOrInStock> findByIdInStock(Long id) {
        Optional<ClothesInStoreOrInStock> clothesInStock = clothesRepository.findById(id);
        if(clothesInStock.isPresent()){
            for (StoreOrStock storeOrStock : clothesInStock.get().getClothesByStoreOrStock()){
                if(storeOrStock.getStoreOrStock().equals("stock")){
                    return clothesInStock;
                }
            }
        } else throw new EntityNotFoundException();
        return Optional.of(new ClothesInStoreOrInStock());
    }

    @Override
    public ClothesInStoreOrInStock createClothesInStock(ClothesInStoreOrInStock clothesInStock) {
        StoreOrStock storeOrStock = new StoreOrStock();
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        collectionClothes.add(clothesInStock);
        storeOrStock.setStoreOrStock("stock");
        storeOrStock.setStoreOrStockByClothes(collectionClothes);
        storeAndStockRepository.save(storeOrStock);
        return clothesRepository.save(clothesInStock);
    }
}