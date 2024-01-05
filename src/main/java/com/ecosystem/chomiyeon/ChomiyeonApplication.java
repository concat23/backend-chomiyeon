package com.ecosystem.chomiyeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ChomiyeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChomiyeonApplication.class, args);
	}

}
