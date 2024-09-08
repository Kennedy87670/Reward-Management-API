package com.rewardManagement.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		info = @Info(
				title = "Reward Reward-Management-API",
				description = "Reward REST API DOCUMENTATION",
				version = "v1.0",
				contact = @Contact(
						name = "Nwaizugbe Chukwuemeka Johnkennedy",
						email = "chukwuemekanwaizugbe@gmail.com"
				)
		)
)
@SpringBootApplication
public class RewardsManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsManagementApiApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
