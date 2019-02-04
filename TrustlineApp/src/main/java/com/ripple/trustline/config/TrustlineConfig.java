package com.ripple.trustline.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages="com.ripple.trustline")
@PropertySource("classpath:application.properties")
public class TrustlineConfig {
		
}
