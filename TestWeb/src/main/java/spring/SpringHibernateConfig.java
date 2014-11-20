package spring;

import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by stagiaire on 20/11/2014.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao", "service"})
public class SpringHibernateConfig {


    Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.setProperty("hibernate.show_sql", "true");
        return props;
    }

    @Bean
    DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:mysql://192.168.1.44:3306/lst_dev");
        ds.setUsername("lst_dev");
        ds.setPassword("L2tdb!");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }

    @Bean
    LocalSessionFactoryBean getLocalSessionFactoryBean() {
        LocalSessionFactoryBean fb = new LocalSessionFactoryBean();
        fb.setDataSource(getDataSource());
        fb.setPackagesToScan("entities");
        fb.setHibernateProperties(getProperties());
        return fb;
    }

    @Bean
    SessionFactory getSessionFactory(DataSource ds) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(ds);
        builder.addPackages("entities");
        builder.setProperties(getProperties());
        return builder.buildSessionFactory();
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }


}
