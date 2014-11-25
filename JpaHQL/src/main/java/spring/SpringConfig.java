package spring;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by stagiaire on 14/11/2014.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao", "service"})
public class SpringConfig {

    @Bean
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/lst_dev");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factroyBean = new LocalContainerEntityManagerFactoryBean();

        factroyBean.setDataSource(getDataSource());
        factroyBean.setPackagesToScan("entities");
        //factroyBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factroyBean.setPersistenceProvider(new HibernatePersistenceProvider());
        factroyBean.setJpaProperties(getProperties());
        return factroyBean;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "create");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.setProperty("hibernate.show_sql", "true");

        return props;
    }

}
