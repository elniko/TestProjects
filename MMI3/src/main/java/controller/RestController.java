package controller;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tools.annotations.LoggerConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * Created by stagiaire on 18/12/2014.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value="/rest")
public class RestController {

    @LoggerConfig
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
