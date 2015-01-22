package spring;

import log4j2.MyDbProvider;
import log4j2.SqlQueryAppender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.security.SecurityConfig;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao","service","log4j2" })
@PropertySource(value = "classpath:db.properties")
//@Import({ SecurityConfig.class })
public class SpringConfig {

    @Autowired
    Environment env;

    //@Value()
    String url;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("ds.url"));
        ds.setUsername(env.getProperty("ds.user"));
        ds.setPassword(env.getProperty("ds.pass"));
        ds.setDriverClassName(env.getProperty("ds.driver"));
        return ds;
    }

    @Bean
    public PlatformTransactionManager getTransaction(EntityManagerFactory emf) {
        JpaTransactionManager transaction = new JpaTransactionManager(emf);
        return transaction;
    }

    @Bean(name = "entytyManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("entity");
        factory.setDataSource(getDataSource());
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
        prov.setContext(ctx);
        appender.setExecutor(prov::execQuery);
        coreLogger.addAppender(appender);
        return logger;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.ddl"));
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.showsql"));
        return props;
    }

}
