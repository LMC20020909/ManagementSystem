package org.hustzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.hustzl.mapper")
public class BackManagesysApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackManagesysApplication.class, args);
    }

}
