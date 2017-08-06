//package com.challenge;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
//
//import com.challenge.config.Config;
//import com.challenge.domain.Customer;
//import com.challenge.repo.CustomerRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class Challenge1ApplicationTests {
//
//	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
//	
//	CustomerRepository repo = ctx.getBean(CustomerRepository.class);
//	
//	@Test
//	public void contextLoads() {
//		Customer c1 = new Customer("John","doe");
//		Customer c2 = new Customer("Janne","doe");
//		
//		c1 = repo.save(c1);
//		c2 = repo.save(c2);
//		
//		for(Customer t:repo.findAll()) {
//			System.out.println(t.getFirstName()+ " "+ t.getLastName());
//		}
//		
//	}
//
//}
