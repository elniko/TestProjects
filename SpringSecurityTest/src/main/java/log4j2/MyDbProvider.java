package log4j2;

import entity.LogEntity;
import entity.ProcessEntity;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import service.ILogService;
import service.LogService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Component
public class MyDbProvider implements DBaseProvider, ApplicationContextAware {


       ApplicationContext ctx;

    @Override

    public void execQuery(LogEvent event) {
        //event.get
      //  ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
        ILogService logService = ctx.getBean(ILogService.class);
        LogEntity e = new LogEntity();
        e.setMessage(event.getMessage().getFormattedMessage());
        e.setLineCount(Long.valueOf(event.getContextMap().get("line_count")));
        ProcessEntity pe = new ProcessEntity();
        pe.setId(Long.valueOf(event.getContextMap().get("process_id")));
        e.setProcess(pe);
        logService.addLogMessage(e);
        //EntityManagerFactory factory =  (EntityManagerFactory) ctx.getBean("entytyManagerFactory");
        //EntityManager em = factory.createEntityManager();
        //em.getTransaction().begin();
        //Query query = em.createNativeQuery(queryString);
        //query.setParameter(1,1);

//        query.setParameter(2, event.getMessage().getFormattedMessage());
//        query.setParameter(3, 2);
//        query.executeUpdate();
//        em.getTransaction().commit();
//        em.clear();
    }


    public void setContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
