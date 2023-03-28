package com.example.myproject.config;

import javax.activation.DataSource;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class Config {
	@Bean
	public DataSource dataSource() {
	    return Mockito.mock(DataSource.class);
	}
}

