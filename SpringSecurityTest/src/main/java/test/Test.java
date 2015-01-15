package test;

import log4j2.MyDbProvider;
import log4j2.SqlQueryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by stagiaire on 16/12/2014.
 */
public class Test {
   static  org.apache.logging.log4j.Logger logger = LogManager.getLogger("Test");
    public static void main(String[] args) {


        org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger)logger;
        LoggerContext logContext = coreLogger.getContext();
        Configuration config =  logContext.getConfiguration();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
        SqlQueryAppender appender = (SqlQueryAppender) config.getAppender("QueryAppender");
        MyDbProvider prov = new MyDbProvider();
        prov.setContext(ctx);
        appender.setExecutor(prov::execQuery);
        coreLogger.addAppender(appender);


        ThreadContext.put("process_id", 5 +"");
        ThreadContext.put("line_count", 5 + "");


        logger.info("hello1");
       // coreLogger.info("hello2");
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
//        IUserService us =  ctx.getBean(IUserService.class);
//        User user = new User();
//        user.setName("admin");
//        user.setEnabled(true);
//        UserRole role = new UserRole();
//        Logger log = LoggerFactory.getLogger("Log");
//        log.info("hello");
       // us.addUser(user);

      // user = us.loadByUserName("admin");




        //LogDao logService = ctx.getBean(LogDao.class);
        //LogEntity e = new LogEntity();
        //e.setMessage("GOVNO");
        //e.setLineCount(44);
        //ProcessEntity pe = new ProcessEntity();
        //pe.setId(4);
        //e.setProcess(pe);
        //logService.addLogMessage(e);



//startLog(555);

    }


    public static String startLog(int id) {
        ThreadContext.put("process_id", id + "");
        for(int  i =0; i < 10; i++) {
            ThreadContext.put("line_count", i + "");
            logger.info("Hello Message" + i);
        }
        return "Ok";
    }
}
