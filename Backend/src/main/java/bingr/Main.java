package bingr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import bingr.Users.User;
import bingr.Users.UserRepository;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
//@EnableJpaRepositories
class Main {
	
	
	
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    /**
     * 
     * @param userRepository repository for the User entity
     * Creates a commandLine runner to enter dummy data into the database
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
        	
        };
    }
    
    /**
     * Bean creates the documentation for all the APIs
     * @return some docket idk what that is
     */
    @Bean
    public Docket getApiDocs() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }

}
