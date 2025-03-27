package CRUD_rest_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import CRUD_rest_api.acessingdatajpa.Customer;
import CRUD_rest_api.acessingdatajpa.CustomerRepository;

@SpringBootApplication
public class CrudRestApiApplication {	
	
	private static final Logger log = LoggerFactory.getLogger(CrudRestApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudRestApiApplication.class, args);
	}
	
	
	
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		
		return (args) -> {
			
			
			//Saving a new customer
			
			repository.save(new Customer("Yuri", "Casadei"));
			repository.save(new Customer("Ingrid", "Casadei"));
			repository.save(new Customer("Igor", "Casadei"));
			repository.save(new Customer("Gustavo", "Ferrari"));
			repository.save(new Customer("Igor", "Guimaroes"));
			
			
			
			//fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			
			log.info("");
			
			
			//fetch an individual customer by ID
			Customer customer = repository.findById(1L).get();
			log.info("Customer found with findeById(1L):");
			log.info("-------------------------------");
			log.info(customer.toString());
			log.info("");
			
		    // fetch customers by last name
		    log.info("Customer found with findByLastName('Casadei'):");
		    log.info("--------------------------------------------");
			
			for (Customer casadei : repository
					.findByLastNameStartsWithIgnoreCase("Casadei")) {
				log.info(casadei.toString());
			}

		    log.info("");
			
			
			
		};
		
	}

}
