package com.example.mummoomserver.login.config;

import com.example.mummoomserver.login.service.OAuth2Service;
import com.example.mummoomserver.login.token.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final OAuth2Service oAuth2Service;
    /**
     * 현석 - 해당 userDetailService를 이용할 필요가 없어서 주석 처리함
     */
//    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationSuccessHandler OAuth2AuthenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**").disable()
                .headers().frameOptions().disable()// csrf 토큰을 매번 받지 않아도 된다.
                .and()
                .cors().disable()
                .formLogin().disable() //폼 로그인 방식을 사용하지 않는다.
                .logout().disable() // 로그아웃 uri를 사용하기 위한 설정 (점검 필요)
                .httpBasic().disable() // http basic auth기반 로그인창 사용 X
                .authorizeRequests()//요청에 대한 권한을 지정한다.
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/authorize", "/users").anonymous()
                .antMatchers(HttpMethod.POST, "/oauth2/unlink").authenticated()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers("/login/oauth2/code/google").permitAll()
                .antMatchers("/login/oauth2/code/kakao").permitAll()
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**" ,
                        /*Probably not needed*/ "/swagger.json").permitAll()
                .anyRequest().authenticated() // 그 이외에는 인증된 사용자만 접근 가능하다
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2Service);
//                .and()
//                .successHandler(OAuth2AuthenticationSuccessHandler);

        //로그인 인증을 진행하는 필터 이전에 jwtAuthenticationFilter 가 실행되도록 설정
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    /*PasswordEncoder를 BCryptPasswordEncoder로 사용하도록 Bean 등록*/
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html");
    }
}