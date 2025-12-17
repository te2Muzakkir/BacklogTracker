package io.muzakkirlabs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.muzakkirlabs.service.BackLogUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, BackLogUserDetailsService backLogUserDetailsService) throws Exception {
		httpSecurity
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/login", "/register", "/forgotPassword","/sendForgotPasswordMail", "/updatePasswordScreen",
            		 "/updatePassword", "/resources/**", "/api/public/**").permitAll()
             .requestMatchers("/**").hasRole("MANAGER")
             .anyRequest().authenticated()
         )
         .formLogin(form -> form
             .loginPage("/login")
             .loginProcessingUrl("/login")
             .defaultSuccessUrl("/home", true)
             .permitAll()
             )
         .sessionManagement(session -> session
        		 .sessionFixation().migrateSession()
        		 )
         .logout(logout -> logout
        		 .logoutUrl("/logout")
        		 .logoutSuccessUrl("/login?logout")
        		 .invalidateHttpSession(true)
        		 .permitAll()
        		 )
         .userDetailsService(backLogUserDetailsService);
		
		httpSecurity.sessionManagement(session -> session.maximumSessions(1));
     return httpSecurity.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}

}