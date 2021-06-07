package com.oraclejava.project.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import com.oraclejava.project.service.KakaoOAuth2AuthorizedClientService;
import com.oraclejava.project.service.UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	  @Autowired
	  private UserService userService; // 3
	  
	  private AuthFailureHandler authFailureHandler;
	  	  
	  // 로그를 남겨줌
	  private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	  
	  @Override
	  public void configure(WebSecurity web) { // 4
	    web.ignoring()
	    	.antMatchers("/resources/**", "/static/**",
	    			"/css/**", "/js/**", "/img/**");
	  }
	  
	  @Override
	  protected void configure(HttpSecurity http) throws Exception { // 5
	    http
	          .authorizeRequests() // 6
	          	.antMatchers("/admin/**").hasRole("ADMIN") // ADMIN만 접근 가능
	            .antMatchers("/user/**").hasAnyRole("USER","SELLER","ADMIN") // USER, ADMIN만 접근 가능
	            .antMatchers("/home/**","/login", "/oauth2/**","/**").permitAll()// 누구나 접근 허용
	            .and()
				.exceptionHandling().accessDeniedPage("/403");
	            
	    		
	             // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
			    
	    http.csrf().disable();
	    
	    http
	          .formLogin()
	          .loginPage("/login") 	// 로그인 페이지 url
	          .usernameParameter("userid")  // 로그인 요청시 id용 파라미터 (메소드 이름이 usernameParameter로 무조건 써야하지만, 파라미터는 email이든 id이든 name이든 상관없다.)
	          .passwordParameter("passwd")
	          .and()
	          .oauth2Login()
	          .loginPage("/login")// 로그인 요청시 password용 파라미터	
	          //.failureHandler(authFailureHandler)
	          .defaultSuccessUrl("/") ; // 로그인 성공시
	          // 로그인 성공시 보던 페이지가 있으면 그쪽으로 넘어감
	    	  //.successHandler(successHandler());
	             
	    
	    // 3. 로그아웃 설정
	    http
	              .logout()
	              .logoutSuccessUrl("/") // 로그아웃 성공시
	              .deleteCookies("JSESSIONID");
	              
	  }
	  
	  
	  // 로그인 성공 처리를 위한 Handler
		/*
		 * @Bean public AuthenticationSuccessHandler successHandler() {
		 * logger.info("[ BEAN ] : AuthenticationSuccessHandler"); // loginIdname,
		 * defaultUrl return new CustomAuthenticationSuccessHandler("username", "/"); }
		 */
		
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception {
	  auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); }
	  
	  @Bean public PasswordEncoder passwordEncoder() { return new
	  BCryptPasswordEncoder(); }
	  
	  @Bean
	  public OAuth2AuthorizedClientService authorizedClientService() {
	      return new KakaoOAuth2AuthorizedClientService();
	  }
	  
	  
}
