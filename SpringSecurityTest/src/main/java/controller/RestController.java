package controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
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
public class RestController implements ServletConfigAware{

    Logger logger = LogManager.getLogger(this.getClass().getName());

    ServletConfig config;

    @PersistenceContext
    EntityManager em;

    @RequestMapping("/hello")
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


    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        config = servletConfig;
    }
}
