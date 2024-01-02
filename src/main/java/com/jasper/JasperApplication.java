package com.jasper;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class JasperApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(JasperApplication.class, args);
	}




}
