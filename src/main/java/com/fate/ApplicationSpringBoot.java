package com.fate;

import com.fate.entity.Role;
import com.fate.entity.User;
import com.fate.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@AllArgsConstructor
public class ApplicationSpringBoot {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationSpringBoot.class, args);
    }

}
