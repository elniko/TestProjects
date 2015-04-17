package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Nick on 13/03/2015.
 */



@SpringBootApplication
@ComponentScan(basePackages = {"spring","tools","service", "dao", "controller", "tools.log4j2"})
@EntityScan("entity")
@EnableScheduling
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
//@Import({SpringConfig.class,SecurityConfig.class, ServletConfig.class})
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration.class, org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration.class})
public class MmiBoot   {
    public static void main(String[] args) {
        SpringApplication.run(MmiBoot.class, args);
    }

}
