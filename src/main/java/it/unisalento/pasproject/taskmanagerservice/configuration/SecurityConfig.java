package it.unisalento.pasproject.taskmanagerservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method is used to configure the security settings for the application. It sets up the authorization rules,
     * session management policy, and Cross-Origin Resource Sharing (CORS) and Cross-Site Request Forgery (CSRF) settings.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object that contains the security settings for the application.
     * @throws Exception If an error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/tasks/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configurazione CORS
        http.cors(AbstractHttpConfigurer::disable); // Disabilita CORS, se necessario

        // Configurazione CSRF
        http.csrf(AbstractHttpConfigurer::disable); // Disabilita CSRF

        return http.build();
    }
}