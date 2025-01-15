package com.microservice.msofbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsofbankApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsofbankApplication.class, args);
		System.out.println("MsofbankApplication levantado con exito");
	}

}
