package com.microservice.msofbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class MsofbankApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsofbankApplication.class, args);
		System.out.println("Msofbank Applicacion levantada con exito");
	}

}
