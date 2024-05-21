package com.gc;

import com.gc.domain.Servicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ForohubApplication{

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ForohubApplication.class, args);

		Servicio s = context.getBean(Servicio.class);
		s.guardar();
	}

}
