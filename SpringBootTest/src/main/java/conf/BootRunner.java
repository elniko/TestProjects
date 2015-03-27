package conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nick on 23/03/2015.
 */

@SpringBootApplication
@ComponentScan(basePackages = {"conf","service", "dao", "controller"})
@EntityScan("entity")
public class BootRunner {
    public static void main(String[] args) {
        SpringApplication.run(BootRunner.class, "--debug");
    }
}
