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
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository;

    public Collection <ClothesInStoreOrInStock> getAllClothesInStore(){
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> storeList = new ArrayList<>();
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("store")) {
                storeList.add(storeOrStock);
            }
        }
        //return storeList.get(0);
        return storeList.get(0).getStoreOrStockByClothes();
    }

    @Override
    public Optional<ClothesInStoreOrInStock> findByIdInStore(Long id) {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> storeList = new ArrayList<>();
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("store")) {
                storeList.add(storeOrStock);
            }
        }
        ClothesInStoreOrInStock storeOrInStock = new ClothesInStoreOrInStock();
        for (ClothesInStoreOrInStock inStore : storeList.get(0).getStoreOrStockByClothes()){
            if(id.equals(inStore.getId())){
                storeOrInStock = inStore;

            }
        }
        return Optional.ofNullable(storeOrInStock);
    }

    @Override
    public ClothesInStoreOrInStock saveInStore(ClothesInStoreOrInStock clothesInStore) {
        return clothesInStoreOrInStockRepository.save(clothesInStore);
    }

    public void deleteById(Long id){
        storeAndStockRepository.deleteById(id);
    }
}