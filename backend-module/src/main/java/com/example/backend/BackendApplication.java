package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// 1. Diz ao Spring para encontrar o Service e Controller em ambos os módulos
@ComponentScan(basePackages = {"com.example.backend", "com.example.ejb"})
// 2. Diz ao Spring para encontrar as Entidades JPA no módulo EJB
@EntityScan(basePackages = "com.example.ejb.model")
// 3. Diz ao Spring para encontrar os Repositórios (se houver) no backend
@EnableJpaRepositories(basePackages = "com.example.backend.repository")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}