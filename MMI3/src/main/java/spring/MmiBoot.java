package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.view.tiles2.SpringWildcardServletTilesApplicationContext;

import javax.servlet.Servlet;

/**
 * Created by Nick on 13/03/2015.
 */


@SpringBootApplication
//@Import({SpringConfig.class,SecurityConfig.class, ServletConfig.class})
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration.class, org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration.class})
public class MmiBoot  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MmiBoot.class, "--debug");
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MmiBoot.class);
    }
}
