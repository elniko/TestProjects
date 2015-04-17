package controller;


import log4j2.MyDbProvider;
import log4j2.SqlQueryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletConfig;

/**
 * Created by stagiaire on 18/12/2014.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value="/rest")
public class RestController {

    @Autowired
    Logger logger;

    ServletConfig config;

   // @Autowired
    public RestController() {

//        logger = LogManager.getLogger(this.getClass().getName());
//        org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger)logger;
//        Configuration config =  coreLogger.getContext().getConfiguration();
//
//        SqlQueryAppender appender = (SqlQueryAppender) config.getAppender("QueryAppender");
//        MyDbProvider prov = new MyDbProvider();
//        prov.setContext(ctx);
//        appender.setExecutor(prov::execQuery);
//        coreLogger.addAppender(appender);

    }

    @PersistenceContext
    EntityManager em;

    @RequestMapping("/")
    public String getHello() {
        for(int  i =0; i < 5; i++) {
            logger.info("Hello Message");
        }
        return config.getServletContext().getServletContextName();
    }

    @RequestMapping("/startLog/{id}")
    public String startLog(@PathVariable int id) {
        ThreadContext.put("process_id", id + "");
        for(int  i =0; i < 10; i++) {
            ThreadContext.put("line_count", i + "");
            logger.info("Hello Message" + i);
        }
        return "Ok";
    }



}
