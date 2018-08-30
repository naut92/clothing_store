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
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository;

    public StoreServiceImpl(StoreAndStockRepository storeAndStockRepository,
                            ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository) {
        this.storeAndStockRepository = storeAndStockRepository;
        this.clothesInStoreOrInStockRepository = clothesInStoreOrInStockRepository;
    }


    @Override
    public List<ClothesInStoreOrInStock> getAllClothesInStore() {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();
        List<StoreOrStock> storeList = new ArrayList<>();
        //sort entities related by store:
        for (StoreOrStock storeOrStock : collection){
            if (!storeOrStock.getStoreOrStock().isEmpty() && storeOrStock.getStoreOrStock().equals("store")) {
                storeList.add(storeOrStock);
            }
        }

        List<ClothesInStoreOrInStock> clothes = new ArrayList<>();
        for (StoreOrStock store : storeList){
            for (ClothesInStoreOrInStock storeOrInStock : store.getStoreOrStockByClothes()){
                clothes.add(storeOrInStock);
            }
        }
        return clothes;
    }

    @Override
    public Optional<ClothesInStoreOrInStock> findByIdInStore(Long id) {
        Optional<ClothesInStoreOrInStock> clothesInStore = clothesInStoreOrInStockRepository.findById(id);
        if(clothesInStore.isPresent()){
            for (StoreOrStock storeOrStock : clothesInStore.get().getClothesByStoreOrStock()){
                if(storeOrStock.getStoreOrStock().equals("store")){
                    return clothesInStore;
                }
            }
        } else throw new EntityNotFoundException();
        return Optional.of(new ClothesInStoreOrInStock());
    }

    @Override
    public ClothesInStoreOrInStock createClothesInStore(ClothesInStoreOrInStock clothesInStore) {
        StoreOrStock storeOrStock = new StoreOrStock();
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        collectionClothes.add(clothesInStore);
        storeOrStock.setStoreOrStock("store");
        storeOrStock.setStoreOrStockByClothes(collectionClothes);
        storeAndStockRepository.save(storeOrStock);
        return clothesInStoreOrInStockRepository.save(clothesInStore);
    }

    @Override
    public ClothesInStoreOrInStock updateClothesInStore(ClothesInStoreOrInStock clothesInStore) {
        StoreOrStock storeOrStock = new StoreOrStock();
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        collectionClothes.add(clothesInStore);
        storeOrStock.setStoreOrStock("store");
        storeOrStock.setStoreOrStockByClothes(collectionClothes);
        storeAndStockRepository.save(storeOrStock);
        return clothesInStoreOrInStockRepository.save(clothesInStore);
    }
/*
    @Override
    public Optional<StoreOrStock> updateInStore(Long id, ClothesInStoreOrInStock clothesInStore) {
        Optional<StoreOrStock> oPstoreOrStock = storeAndStockRepository.findById(id);
        ClothesInStoreOrInStock clothes;
        StoreOrStock storeOrStock = new StoreOrStock();
        StoreOrStock uPdatestoreOrStock;
        if(oPstoreOrStock.isPresent()){
            clothes = clothesInStoreOrInStockRepository.save(clothesInStore);
            storeOrStock.getStoreOrStockByClothes().add(clothes);
            uPdatestoreOrStock = storeAndStockRepository.save(storeOrStock);
            return Optional.ofNullable(uPdatestoreOrStock);
        }else {
            return Optional.of(new StoreOrStock());
        }
    }
    */
}