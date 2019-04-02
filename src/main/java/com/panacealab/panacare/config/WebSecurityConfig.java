package com.panacealab.panacare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsUtils;

/**
 * @author loveloliii
 * @description
 * @date 2019/3/20.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter 实现了WebSecurityConfig接口

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // Allow the user with the Username user and the Password password to authenticate with form based authentication
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**").and()
                .csrf().disable().authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll().and()
                .authorizeRequests()
                .anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.permitAll()
                .and().authorizeRequests().anyRequest().anonymous();
      /*  http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/token", "/rest/**", "/api/**", "/**")
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/","/home").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();*/
    }

}
