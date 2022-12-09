package com.example.jpa_test.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// SpringSecurity 5.7.0 버전 부터는 WebSecurityConfigureAdapter 가 deprecated 되어 새로운 설정 방법 적용
/**
 * @see <a href="https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter">Link</a>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PATHS = {"/signup", "/login", "/h2-console/**", "/ping"};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authRequest -> authRequest.antMatchers(PATHS).permitAll()
						.anyRequest().authenticated())
				.csrf().disable()
				.formLogin()
				.usernameParameter("memberId")
				.passwordParameter("password")
				.loginProcessingUrl("/login")
				.successHandler(customAuthenticationSuccessHandler())
				.failureHandler(customAuthenticationFailureHandler())
				.and()
				.logout()
		;

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers("/h2-console/**");
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}
