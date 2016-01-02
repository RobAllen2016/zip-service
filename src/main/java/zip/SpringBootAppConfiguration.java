package zip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The core class of the spring boot application.  Run this main method to run the app.
 */
@SpringBootApplication
@ComponentScan("zip")
public class SpringBootAppConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppConfiguration.class, args);
    }

} 
