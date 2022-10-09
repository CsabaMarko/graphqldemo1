package com.csabamarko.springboot.jpa.graphqldemo1;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class Graphqldemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Graphqldemo1Application.class, args);
	}

}
