package edu.uclm.esi.sqaUsers;
//en funcion de las anotaciones utilizadas se importa una libreria u otra
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication//Combina las anotaciones://@Configuration(indica el uso de anotaciones @bean o métodos que darán soporte a otros componentes de la aplicación), //@EnableAutoConfiguration(configura el classpath) y //@ComponentScan(inicializa los componentes de Sprint)
@ServletComponentScan//Inicializa los servlets para manejar las peticiones HTTP
public class AppUsers extends SpringBootServletInitializer {// La clase App extiende SpringBootServletInitializer para habilitar el despliegue en contenedores web servlet.
	// Método principal que inicia la aplicación Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(AppUsers.class, args);// Inicia la aplicación Spring Boot 
	}
	
	@Override //para indicar que el metodo "configure" sobreescribe un metodo de la clase padre o super clase SpringBootServletInitializer
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppUsers.class);// Configura el constructor de la aplicación Spring para que utilice la clase App como fuente de configuración.
	}
}