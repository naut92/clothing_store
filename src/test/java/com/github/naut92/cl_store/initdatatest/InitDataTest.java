package com.github.naut92.cl_store.initdatatest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
//import com.google.common.collect.ImmutableMap;

import com.github.naut92.cl_store.repository.StoreAndStockRepository;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * @author Alimenkou Mikalai
 */
public class InitDataTest extends AbstractInitDataTest<StoreAndStockRepository> {

    @Test
    @DataSet("empty.xml")
    @ExpectedDataSet("expected-books.xml")
    @Commit
    public void booksMayBeStored() {
       // initdatatest.save(new ClothesInStoreOrInStockRepository(1L));
    }

    @Test
    @Sql("/data.sql")
    public void severalBooksForTheSameAuthorAreReturned() {
        /*
        ClothesInStoreOrInStockRepository clothes = new ClothesInStoreOrInStockRepository("First book", "author");
        first.setId(1L);
        Book second = new Book("Second book", "author");
        second.setId(2L);
        assertThat(initdatatest.findByAuthor("author"), hasItems(samePropertyValuesAs(first), samePropertyValuesAs(second)));
        */
    }
}