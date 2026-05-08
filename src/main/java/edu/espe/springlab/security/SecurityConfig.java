package edu.espe.springlab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitimos el login y la consola de H2
                        .requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
                        // 2. IMPORTANTE: Permitimos el acceso a la raíz para evitar el error 403 al entrar a la URL de Render
                        .requestMatchers("/").permitAll()
                        // 3. Permitimos acceso total a los estudiantes para probar sin Token temporalmente
                        .requestMatchers("/api/students/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Esto es necesario para que la consola de H2 se vea correctamente en el navegador
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}