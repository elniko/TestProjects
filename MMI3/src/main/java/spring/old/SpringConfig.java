package spring.old;

import tools.log4j2.MyDbProvider;
import tools.log4j2.SqlQueryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by stagiaire on 16/12/2014.
 */
//@Configuration
//@EnableTransactionManagement
//@EnableScheduling
//@EnableWebMvcSecurity

//@PropertySource(value = {"classpath:db.properties"})
//@ImportResource(value = "/WEB-INF/mmi3-security.xml")
//@Import({SecurityConfig.class})
public class SpringConfig {

    @Autowired
    Environment env;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    @Bean
//    public DataSource getDataSource() {
//
//        BasicDataSource ds = new BasicDataSource();
//        //DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(env.getProperty("ds.url"));
//        ds.setUsername(env.getProperty("ds.user"));
//        ds.setPassword(env.getProperty("ds.pass"));
//        ds.setDriverClassName(env.getProperty("ds.driver"));
//        return ds;
//    }

    @Bean
    public PlatformTransactionManager getTransaction(EntityManagerFactory emf) {
        JpaTransactionManager transaction = new JpaTransactionManager(emf);
        return transaction;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("entity");
        factory.setDataSource(ds);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getProperties());
        return factory;
    }

    @Bean
    @Autowired
    public Logger getLogger(ApplicationContext ctx) {

        Logger logger = LogManager.getLogger("controller.RestController");
        org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger)logger;
        org.apache.logging.log4j.core.config.Configuration config =  coreLogger.getContext().getConfiguration();
        SqlQueryAppender appender = (SqlQueryAppender) config.getAppender("QueryAppender");
        MyDbProvider prov = new MyDbProvider();
       // prov.setContext(ctx);
       // appender.setExecutor(prov::execQuery);
       // coreLogger.addAppender(appender);
        return logger;
    }

    @Bean(name ="passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder();
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.ddl"));
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.showsql"));
        return props;
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster caster = new SimpleApplicationEventMulticaster();
        caster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return caster;
    }



}
