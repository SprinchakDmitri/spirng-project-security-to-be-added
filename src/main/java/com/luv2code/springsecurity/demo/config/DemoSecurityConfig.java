package com.luv2code.springsecurity.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;


@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test").roles("EMPLOYEE"))
                .withUser(users.username("mary").password("test").roles("MANAGER"))
                .withUser(users.username("susan").password("test").roles("ADMIN","MANAGER","EMPLOYEE"))
                .withUser(users.username("dsprinceac").password("test").roles("EMPLOYEE","ADMIN","MANAGER","DIRECTOR"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .hasAnyRole("DIRECTOR","EMPLOYEE","MANAGER","ADMIN")
                .antMatchers("/directors/**")
                .hasAnyRole("DIRECTOR")
                .antMatchers("/systems/**")
                .hasRole("ADMIN")
                .antMatchers("/leaders/**")
                .hasRole("MANAGER")
                .antMatchers("/resources/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");}
}



