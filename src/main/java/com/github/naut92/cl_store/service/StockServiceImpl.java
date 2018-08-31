package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;
import com.github.naut92.cl_store.repository.ClothesInStoreOrInStockRepository;
import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesRepository;

    public StockServiceImpl(StoreAndStockRepository storeAndStockRepository,
                            ClothesInStoreOrInStockRepository clothesRepository) {
        this.storeAndStockRepository = storeAndStockRepository;
        this.clothesRepository = clothesRepository;
    }

    @Override
    public Collection<ClothesInStoreOrInStock> getAllClothesInStock() {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();

        //sort entities related by store:
        List<StoreOrStock> storeList = collection.stream()
                .filter(store -> store.getStoreOrStock().contains("stock"))
                .collect(Collectors.toList());

        Collection<ClothesInStoreOrInStock> clothes = new ArrayList<>();
        for (StoreOrStock stock : storeList){
            for (ClothesInStoreOrInStock storeOrInStock : stock.getStoreOrStockByClothes()){
                clothes.addAll(Collections.singleton(storeOrInStock));
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

    @Override
    public ClothesInStoreOrInStock updateClothesInStock(Long id, ClothesInStoreOrInStock clothesInStock) {
        Optional<StoreOrStock> storeOrStock = storeAndStockRepository.findById(id);
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        if(storeOrStock.isPresent()){
            clothesInStock.setId(id);
            storeOrStock.get().setId(id);
            collectionClothes.add(clothesInStock);
            storeOrStock.get().setStoreOrStock("stock");
            storeOrStock.get().setStoreOrStockByClothes(collectionClothes);
            storeAndStockRepository.save(storeOrStock.get());
        }
        return clothesRepository.save(clothesInStock);
    }
}