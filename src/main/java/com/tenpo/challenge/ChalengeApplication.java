package com.tenpo.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.controllers.filters.AuthorizationFilter;
import com.tenpo.challenge.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChalengeApplication {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtUtil jwtUtil;

	public static void main(String[] args) {
		SpringApplication.run(ChalengeApplication.class, args);
	}

	@Bean
	FilterRegistrationBean<AuthorizationFilter> authorizationFilterFilterRegistrationBean(){
		final FilterRegistrationBean<AuthorizationFilter> filterFilterRegistrationBean = new FilterRegistrationBean<AuthorizationFilter>();
		filterFilterRegistrationBean.setFilter(new AuthorizationFilter(objectMapper, jwtUtil));
		filterFilterRegistrationBean.addUrlPatterns("/tenpo/calculate", "/tenpo/history");

		return  filterFilterRegistrationBean;
	}

}
