package service;

import dao.implimentation.PropertyTypeDaoImpl;
import dao.interfaces.PropertyDao;
import entity.PropertyEntity;
import entity.PropertyTypeEntity;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import spring.MmiBoot;

import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Nick on 13/04/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MmiBoot.class)
@WebIntegrationTest
//@ContextConfiguration(classes = MmiBoot.class,
//      initializers = ConfigFileApplicationContextInitializer.class)
@TransactionConfiguration(defaultRollback = false)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestPropertiesService {

    @Autowired
    PropertyDao propertyDao;

    @Autowired
    PropertyTypeDaoImpl propertyTypeDao;



    //@Test
    @Transactional
    public void test001AddProperty() {
        PropertyEntity pe = new PropertyEntity();
        pe.setName("inpuhht");
        pe.setValueList(null);

        List<PropertyTypeEntity> list = (List<PropertyTypeEntity>) propertyTypeDao.getAllByCondition("t","t.name='STRING'", 0, 0, "");
        pe.setType(list.get(0));

        Set<String> scope = new HashSet<>();
        scope.add("XCT");
        pe.setScope(scope);

        save(pe);


    }

@Test
    public void testString() {
        String s = "govno";

       char[] cha = s.toCharArray();

List<String> list = new ArrayList<>();



        System.out.println(cha);

        StringBuilder d = new StringBuilder(s);


    }

    private void save(PropertyEntity pe) {
        propertyDao.saveEntity(pe);
    }

}
