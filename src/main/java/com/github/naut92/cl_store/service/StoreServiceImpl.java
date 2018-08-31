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
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreAndStockRepository storeAndStockRepository;
    @Autowired
    ClothesInStoreOrInStockRepository clothesRepository;

    public StoreServiceImpl(StoreAndStockRepository storeAndStockRepository,
                            ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository) {
        this.storeAndStockRepository = storeAndStockRepository;
        this.clothesRepository = clothesInStoreOrInStockRepository;
    }


    @Override
    public Collection<ClothesInStoreOrInStock> getAllClothesInStore() {
        Collection<StoreOrStock> collection = storeAndStockRepository.findAll();

        //sort entities related by store:
        List<StoreOrStock> storeList = collection.stream()
                .filter(store -> store.getStoreOrStock().contains("store"))
                .collect(Collectors.toList());

        Collection<ClothesInStoreOrInStock> clothes = new ArrayList<>();
        for (StoreOrStock store : storeList){
            for (ClothesInStoreOrInStock storeOrInStock : store.getStoreOrStockByClothes()){
                clothes.addAll(Collections.singleton(storeOrInStock));
            }
        }
        return clothes;
    }

    @Override
    public Optional<ClothesInStoreOrInStock> findByIdInStore(Long id) {
        Optional<ClothesInStoreOrInStock> clothesInStore = clothesRepository.findById(id);
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
        return clothesRepository.save(clothesInStore);
    }

    @Override
    public ClothesInStoreOrInStock updateClothesInStore(Long id, ClothesInStoreOrInStock clothesInStore) {
        Optional<StoreOrStock> storeOrStock = storeAndStockRepository.findById(id);
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        if(storeOrStock.isPresent()){
            clothesInStore.setId(id);
            storeOrStock.get().setId(id);
            collectionClothes.add(clothesInStore);
            storeOrStock.get().setStoreOrStock("store");
            storeOrStock.get().setStoreOrStockByClothes(collectionClothes);
            storeAndStockRepository.save(storeOrStock.get());
        }
        return clothesRepository.save(clothesInStore);
    }

    @Override
    public ClothesInStoreOrInStock moveClothesInStoreToStock(Long id) {
        Optional<StoreOrStock> storeOrStock = storeAndStockRepository.findById(id);
        Optional <ClothesInStoreOrInStock> clothesToMove = clothesRepository.findById(id);
        Collection<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        if(storeOrStock.isPresent() && clothesToMove.isPresent()){
            clothesToMove.get().setId(id);
            storeOrStock.get().setId(id);
            collectionClothes.add(clothesToMove.get());
            storeOrStock.get().setStoreOrStock("stock");
            storeOrStock.get().setStoreOrStockByClothes(collectionClothes);
            storeAndStockRepository.save(storeOrStock.get());
        }
        return clothesRepository.save(clothesToMove.get());
    }
}