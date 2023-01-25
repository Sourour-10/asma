package tn.esprit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAspectJAutoProxy
//@EnableWebMvc
//@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class ViwellApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViwellApplication.class, args);
	}

}
