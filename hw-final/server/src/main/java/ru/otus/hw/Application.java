package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.printf(
                "Пользователи admin: admin %n%s%n %n%s%n",
                "Чтобы перейти на страницу сайта открывай: ",
                "http://localhost:8080"
        );
    }

}
