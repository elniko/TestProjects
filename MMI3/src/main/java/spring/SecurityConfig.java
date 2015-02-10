package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name="authService")
    //@Autowired
   UserDetailsService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/mmi/file/**").hasRole("ADMIN")
                .and().csrf().and()
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







    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
