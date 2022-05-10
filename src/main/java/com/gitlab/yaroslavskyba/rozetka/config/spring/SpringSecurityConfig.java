package com.gitlab.yaroslavskyba.rozetka.config.spring;

import com.gitlab.yaroslavskyba.rozetka.security.JwtTokenFilter;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.gitlab.yaroslavskyba.rozetka.util.RoleName.ADMIN;
import static com.gitlab.yaroslavskyba.rozetka.util.RoleName.USER;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenFilter jwtTokenFilter;
    private final UserDetailsService userDetailsService;

    public SpringSecurityConfig(JwtTokenFilter jwtTokenFilter, UserDetailsService userDetailsService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            .antMatchers(HttpMethod.POST, ControllerPath.PRODUCTS, ControllerPath.ROLE).hasAuthority(ADMIN)
            .antMatchers(HttpMethod.GET, ControllerPath.USERS).hasAuthority(ADMIN)
            .antMatchers(HttpMethod.PUT, ControllerPath.PRODUCTS, ControllerPath.ROLE).hasAuthority(ADMIN)
            .antMatchers(HttpMethod.DELETE, ControllerPath.PRODUCT, ControllerPath.USER_BY_UUID, ControllerPath.ROLE).hasAuthority(ADMIN)
            .antMatchers(ControllerPath.ROLES).hasAuthority(ADMIN)
            .antMatchers(HttpMethod.POST, ControllerPath.ORDERS, ControllerPath.REVIEWS).hasAnyAuthority(ADMIN, USER)
            .antMatchers(HttpMethod.GET, ControllerPath.USER_BY_LOGIN, ControllerPath.ROLE).hasAnyAuthority(ADMIN, USER)
            .antMatchers(HttpMethod.PUT, ControllerPath.USERS, ControllerPath.REVIEWS).hasAnyAuthority(ADMIN, USER)
            .antMatchers(HttpMethod.DELETE, ControllerPath.REVIEW).hasAnyAuthority(ADMIN, USER)
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/"))
            .and().csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
