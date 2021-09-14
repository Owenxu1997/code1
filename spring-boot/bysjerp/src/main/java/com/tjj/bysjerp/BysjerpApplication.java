package com.tjj.bysjerp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.tjj.bysjerp.*.mapper"})
public class BysjerpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BysjerpApplication.class, args);
    }

}
