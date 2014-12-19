package log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Component
public class MyDbProvider implements DBaseProvider {


    String queryString = "call addLog(?,?,?)";


    public void setQuery(String query) {
        queryString = query;
    }

    @Override

    public void execQuery(LogEvent event) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);

        EntityManagerFactory factory =  (EntityManagerFactory) ctx.getBean("entytyManagerFactory");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery(queryString);
        query.setParameter(1,1);

        query.setParameter(2, event.getMessage().getFormattedMessage());
        query.setParameter(3, 2);
        query.executeUpdate();
        em.getTransaction().commit();
        em.clear();
    }
}
