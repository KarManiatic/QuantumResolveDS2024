package edu.uclm.esi.sqausers;
//en funcion de las anotaciones utilizadas se importa una libreria u otra
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan
public class AppUsers extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(AppUsers.class, args);
	}
	
	@Override 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppUsers.class);
	}
}