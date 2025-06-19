package com.ucc.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //Configurar las reglas de seguridad para las solicitudes http
        http
                .csrf(crsf -> crsf.disable()) //Deshabilitar la proteccion CSRF
                .authorizeHttpRequests(aurhz -> aurhz
                        .requestMatchers(HttpMethod.GET,"/api/orders/**").permitAll() //Permitir todas las solicitudes GET
                        .requestMatchers(HttpMethod.POST,"/api/orders/**").authenticated() //Permitir solo los autenticados a las solicitudes POST
                        .requestMatchers(HttpMethod.PUT,"/api/orders/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/orders/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); //Usar autenticacion básica para las solicitudes http

        return http.build();

    }

    //Configuracion de la autenticacion en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1234"))
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
