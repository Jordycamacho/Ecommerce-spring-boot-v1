package com.example.tienda.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringBootSecurity {

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf().disable()
		.httpBasic()
		.and()
		.authorizeRequests()
                    .requestMatchers("/administrador/**").permitAll()
                    .requestMatchers("/productos/**").permitAll()
                    .requestMatchers("/salvarOrden").permitAll()
                    .requestMatchers("/carrito").permitAll()
                    .requestMatchers("/usuario/login").permitAll()
                    .and().formLogin().loginPage("/usuario/ingresar")
                    .permitAll().defaultSuccessUrl("/usuario/acceder")
                    .and().build();     
    }
}
