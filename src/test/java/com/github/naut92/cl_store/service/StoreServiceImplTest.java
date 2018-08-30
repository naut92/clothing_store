package com.github.naut92.cl_store.service;

import com.github.naut92.cl_store.model.ClothesInStoreOrInStock;
import com.github.naut92.cl_store.model.StoreOrStock;
import com.github.naut92.cl_store.repository.ClothesInStoreOrInStockRepository;
import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

public class StoreServiceImplTest {

    @Mock
    private StoreAndStockRepository storeAndStockRepository;

    @Mock
    private ClothesInStoreOrInStockRepository clothesInStoreOrInStockRepository;

    private StoreService storeService;

    @Before
    public void init() {
       // storeService = new StoreServiceImpl(storeAndStockRepository);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllClothesInStore() {
    }

    @Test
    public void findByIdInStore() {
    }

    @Test
    public void saveInStore() {
    }
/*
    @Test
    public void forEveryPairOfTitleAndAuthorBookIsCreatedAndStored() {
        Book first = new Book("The first", "author");
        Book second = new Book("The second", "another author");
        when(dao.save(notNull(Book.class))).thenReturn(first).thenReturn(second);

        Map<String, String> books = new HashMap<>();
        books.put("The first", "author");
        books.put("The second", "another author");
        assertThat(bookService.addBooks(books), hasItems(first, second));
    }
    */
    @Test
    public void updateInStore() {
        StoreOrStock storeOrStock = new StoreOrStock();
        List<ClothesInStoreOrInStock> collectionClothes = new ArrayList<>();
        List<StoreOrStock> collectionStore = new ArrayList<>();
        storeOrStock.setStoreOrStock("store");
        collectionStore.add(storeOrStock);
        ClothesInStoreOrInStock first = new ClothesInStoreOrInStock(1l,"first",
                44l, "1руб.", "black", "pants", "bla-bla-bla", collectionStore);
        ClothesInStoreOrInStock second = new ClothesInStoreOrInStock(2l,"second",
                52l, "3333руб.", "white", "dress", "mu-mu-mu-mu", collectionStore);
        when(clothesInStoreOrInStockRepository.save(notNull(ClothesInStoreOrInStock.class))).thenReturn(first).thenReturn(second);


        collectionClothes.add(first);
        storeOrStock.setStoreOrStockByClothes(collectionClothes);
        when(storeAndStockRepository.save(notNull(StoreOrStock.class))).thenReturn(storeOrStock);
        //assertEquals(storeService.updateClothesInStore(1L, storeOrStock), hasItems(storeOrStock.getStoreOrStockByClothes().equals(first)));
    }

    @Test
    public void deleteById() {
    }
}