package com.github.naut92.cl_store.initdatatest;

import com.github.database.rider.core.DBUnitRule;
//import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Alimenkou Mikalai
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public abstract class AbstractInitDataTest<D> {
    private static long ID = 1;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> jdbcTemplate.getDataSource().getConnection());

    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected D dao;
}