package com.ripple.trustline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrustLineAppApplication {

	public static void main(String[] args) {
		System.out.println("Welcome to the Trustline");
		System.out.println("Trustline balance is: 0");
		SpringApplication.run(TrustLineAppApplication.class, args);
		
		
	}
	
	

}

