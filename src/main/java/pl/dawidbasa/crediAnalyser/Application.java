package pl.dawidbasa.crediAnalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		// first argument - class name , second argument name of the method/
		// basic run of spring boot aplication
		SpringApplication.run(Application.class, args);

	}

}
