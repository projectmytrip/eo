package com.eni.enhop.be.shifthandoverlogbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.eni.enhop")
public class ShiftHandoverLogbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiftHandoverLogbookApplication.class, args);
	}

}
