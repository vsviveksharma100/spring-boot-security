package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Setup Basic Authentication, white-list some of the end points
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(UserRole.STUDENT.name())
//		.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.ADMIN_TRAINEE.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
		
	}

	/*
	 * Build in-memory Users and Roles
	 */
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {

		UserDetails annaSmith = User.builder()
				.username("annasmith")
				.password(passwordEncoder.encode("password"))
//				.roles(UserRole.STUDENT.name())
				.authorities(UserRole.STUDENT.getGrantedAuthorities())
				.build();

		UserDetails linda = User.builder()
				.username("linda")
				.password(passwordEncoder.encode("password"))
//				.roles(UserRole.ADMIN.name())
				.authorities(UserRole.ADMIN.getGrantedAuthorities())
				.build();

		UserDetails tom = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("password"))
//				.roles(UserRole.ADMIN_TRAINEE.name())
				.authorities(UserRole.ADMIN_TRAINEE.getGrantedAuthorities())
				.build();

		return new InMemoryUserDetailsManager(annaSmith, linda, tom);
	}

}
