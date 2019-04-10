package uk.ac.bris.celfs.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CellTest extends DatabaseTestTemplate {

    @Before
    public void createDatabase() {
        createCriteriaBeforeTest();
        createBandsBeforeTest();
    }

    @Test
    public void testCreateOneCell() {
        
    }
}
