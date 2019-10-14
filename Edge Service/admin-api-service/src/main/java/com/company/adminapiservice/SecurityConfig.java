package com.company.adminapiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

        System.out.println(authBuilder.getDefaultUserDetailsService().loadUserByUsername("adminUser"));
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers("/loggedin").authenticated()
                .mvcMatchers(HttpMethod.PUT, "/inventory/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.PUT, "/customer/{id}").hasAuthority("ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.PUT, "/product/{id}").hasAuthority("ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.PUT, "/levelup/points/{customerId}").hasAuthority("ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.POST, "/customer").hasAuthority("ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.POST, "/customerFrontEnd").hasAuthority("ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.POST, "/inventory").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/invoices").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/levelup").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/product").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/customer/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/inventory/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/levelup/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/product/{id}").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated(); //Here are all the methods for authenticated users that does not require authority

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
