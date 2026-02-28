package com.krish.demo.config;

import com.krish.demo.repository.UserInfoRepository;
import com.krish.demo.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //For Authentication
    @Bean
    public UserDetailsService userDetailsService()
    {
        /*//Hardcoded users
        UserDetails admin = User.withUsername("Krish")
                .password(passwordEncoder.encode("Krish@1234")) //Password should be encrypted
                .roles("ADMIN","USER") //Spring internally uses ROLE_ to find the role so we don't need to append it.
                .build();

        UserDetails user = User.withUsername("John")
                .password(passwordEncoder.encode("John@123"))
                .roles("USER") //Spring internally uses ROLE_ to find the role so we need to append it.
                .build();

        //Its in-memory authentication
        //InMemoryUserDetailsManager is an implementor of UserDetailsService interface.
        return new InMemoryUserDetailsManager(admin,user);*/

        return new UserInfoUserDetailsService();
    }

    //Password encryptor
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Authorisation
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/users/add").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/products/**").authenticated()
                .and()
                .httpBasic() //or formLogin()
                .and()
                .build();
        /*
        //The above syntax is depricated in the latest Spring Boot version
        //Below is the correct syntax
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/product/welcome").permitAll()
                        .requestMatchers("/product/**").authenticated()
                )
                .formLogin(login -> login.permitAll())
                .build();*/
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
