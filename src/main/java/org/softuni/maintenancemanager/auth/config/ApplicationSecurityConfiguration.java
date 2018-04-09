package org.softuni.maintenancemanager.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:8080")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public ApplicationSecurityConfiguration(
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and();
//                .authorizeRequests()
//                .antMatchers("/users/login", "/users/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .rememberMe()
//                .rememberMeParameter("remember")
//                .rememberMeCookieName("rememberMeCookie")
//                .key("70472517-6af6-470f-a054-1ae76d1b4ae0")
//                .tokenValiditySeconds(600)
//                .and()
//                .formLogin()
//                .loginPage("/users/login")
//                .loginProcessingUrl("/users/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                //.successHandler(authenticationSuccessHandler)
//                //.failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout().logoutUrl("/users/logout")
//                .permitAll();
    }

}
