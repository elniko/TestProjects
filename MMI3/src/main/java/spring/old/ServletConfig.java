package spring.old;

import org.apache.catalina.connector.Connector;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nick on 16/12/2014.
 */
//@Configuration
//@EnableWebMvc
//@EnableScheduling
//@ComponentScan("controller")
//@Import({SpringConfig.class,SecurityConfig.class})
//@ComponentScan(basePackages = {"dao","service","log4j2", "tools", "controller" })
public class ServletConfig extends WebMvcConfigurationSupport  {


    @Bean 
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver   viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping r = super.requestMappingHandlerMapping();
        r.setRemoveSemicolonContent(false);
        return r;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver mResolver = new CommonsMultipartResolver();
        mResolver.setMaxUploadSize(500000);
        return mResolver;
    }



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/SpringSecurity/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }



   // @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);

        executor.setThreadFactory(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("th");
                return t;
            }
        });
        return executor;
    }



    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }



    @Bean
     public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        //  factory.setPort(9000);
        factory.setContextPath("");
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html"));
        return factory;
    }

//    @Bean
//    EmbeddedServletContainerCustomizer containerCustomizer(
//            @Value("${keystore.file}") Resource keystoreFile,
//            @Value("${keystore.pass}") String keystorePass) throws Exception {
//
//        String absoluteKeystoreFile = keystoreFile.getFile().getAbsolutePath();
//        return (ConfigurableEmbeddedServletContainer container) -> {
//
//            if (container instanceof TomcatEmbeddedServletContainerFactory) {
//
//                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//                tomcat.addConnectorCustomizers(
//                        (connector) -> {
//
//                           Connector con = new Connector();
//
//
//                            connector.setPort(8443);
//                            connector.setSecure(true);
//                            connector.setScheme("https");
//                            Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
//                            proto.setSSLEnabled(true);
//                            proto.setKeystoreFile(absoluteKeystoreFile);
//                            proto.setKeystorePass(keystorePass);
//                            proto.setKeystoreType("PKCS12");
//                            proto.setKeyAlias("tomcat");
//                        }
//                );
//            }
//        };
//    }



//    @Bean
//    public SecurityConfig applicationSecurity() {
//        return new SecurityConfig();
//    }



   //@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    @EnableWebSecurity
//    protected static  class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//
//
//        //@Resource(name="authService")
//        @Autowired
//        UserDetailsService userService;
//
//        @Autowired
//        PasswordEncoder passwordEncoder;
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().antMatchers("/resources/**");
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .antMatchers("/file/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers("/user/addAdmin").hasRole("SUPERADMIN")
//                    .antMatchers("/user/allAdmins").hasRole("SUPERADMIN")
//                    .antMatchers("/user/allByRole/admin").hasRole("SUPERADMIN")
//                    .antMatchers("/user/all").hasRole("SUPERADMIN")
//                    .antMatchers("/user/profile/**").authenticated()
//                    .antMatchers("/user/**").hasAnyRole("ADMIN", "SUPERADMIN")
//                    .and().csrf().disable()
//                    .httpBasic();
//
//        }
//
//        @Override
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//        }
 //   }


//    @Bean
//    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    public SecurityConfig applicationSecurity() {
//        return new SecurityConfig();
//    }



}
