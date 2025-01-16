package com.microservice.msofbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
@EnableWebFlux
public class MsofbankApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsofbankApplication.class, args);
		System.out.println("MsofbankApplication levantado con exito");

		//System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
		Properties envProperties = new Properties();
		String pathToEnvFile = "C:/Users/julian.huerfano/Desktop/Clases/microservicio/msofbank/msofbank/src/main/resources/.env";
		try (FileInputStream fis = new FileInputStream(pathToEnvFile)) {
			envProperties.load(fis);
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Configurar las propiedades como variables del sistema
		envProperties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
		//System.out.println(System.getProperty("MONGOCONECT"));
	}

}
