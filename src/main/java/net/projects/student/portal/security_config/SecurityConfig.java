package net.projects.student.portal.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.projects.student.portal.security_config.jwt_services.AuthTokenFilter;
import net.projects.student.portal.security_config.jwt_services.AuthenticationEntryPointJwtImpl;
import net.projects.student.portal.security_config.services.UserDetailsServiceImpl;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationEntryPointJwtImpl unauthorizedHandler;

	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeHttpRequests().requestMatchers("/auth/**").permitAll().requestMatchers("/api/test/**")
				.permitAll().requestMatchers("/student/**").permitAll().anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
