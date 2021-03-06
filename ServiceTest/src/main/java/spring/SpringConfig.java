package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.activation.DataSource;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by nike on 27/11/14.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao","service", "testdao"})
@PropertySource(value = "classpath:db.properties")
public class SpringConfig {

    @Autowired
    Environment env;


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

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("entities");
        factory.setDataSource(getDataSource());
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getProperties());
        return factory;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.ddl"));
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.showsql"));
        return props;
    }


}
