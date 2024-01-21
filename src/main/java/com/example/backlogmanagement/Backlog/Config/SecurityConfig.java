package com.example.backlogmanagement.Backlog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;


@Configuration
@EnableWebSecurity

public class SecurityConfig {

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            // CSRF-Konfiguration
	            .csrf(csrf -> csrf.disable()) // Deaktivieren des CSRF-Schutzes

	            // Autorisierungsanforderungen
	            .authorizeHttpRequests(authz -> authz
	                .requestMatchers("/backlog/**").hasAuthority("ROLE_PRODUCT_OWNER")
	                .requestMatchers("/sprint/**").hasAnyAuthority("ROLE_PRODUCT_OWNER", "ROLE_TEAM_MEMBER")
	                .anyRequest().authenticated()
	            )

	            // HTTP Basic Auth-Konfiguration
	            .httpBasic(Customizer.withDefaults())

	            // Form-Login-Konfiguration
	            .formLogin(form -> form
	                .loginPage("/login").permitAll()
	                .defaultSuccessUrl("/home", true)
	                .failureUrl("/login?error=true")
	            )

	            // Session-Management-Konfiguration
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            );

	        return http.build();
	    }
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

	            

}


