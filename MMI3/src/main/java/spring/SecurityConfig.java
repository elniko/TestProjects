package spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by stagiaire on 16/12/2014.
 */


@Configuration
//@EnableAutoConfiguration
//@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public   class SecurityConfig extends WebSecurityConfigurerAdapter
{
   // @Autowired
    private SecurityProperties security;

    //@Resource(name="authService")
    @Autowired
    UserDetailsService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**");
//    }



   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/file/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/addAdmin").hasRole("SUPERADMIN")
                .antMatchers("/user/allAdmins").hasRole("SUPERADMIN")
                .antMatchers("/user/allByRole/admin").hasRole("SUPERADMIN")
                .antMatchers("/user/all").hasRole("SUPERADMIN")
                .antMatchers("/user/profile/**").authenticated()
                .antMatchers("/user/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .and().csrf().disable()
                .httpBasic();

//        http.authorizeRequests().antMatchers("/SpringSecurity/admin/**")
//                .hasRole("ADMIN")
//               // .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/SpringSecurity/login").failureUrl("/SpringSecurity/login?error")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and().logout().logoutSuccessUrl("/SpringSecurity/login?logout")
//                .and().csrf()
//                .and().exceptionHandling().accessDeniedPage("/SpringSecurity/403");

//        http.authorizeRequests().antMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("username")
//                .passwordParameter("password").permitAll()
//                .and().logout().logoutSuccessUrl("/login?logout")
//                .and().csrf()
//                .and().exceptionHandling().accessDeniedPage("/403");
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.userDetailsService(accountUserDetailService).passwordEncoder(passwordEncoder);
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//
//    }


    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.inMemoryAuthentication().withUser("admin").password("admin")
              .roles("ADMIN", "USER").and().withUser("user").password("user")
              .roles("USER");

      // auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
