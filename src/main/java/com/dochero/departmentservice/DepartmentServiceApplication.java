package com.dochero.departmentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient
public class DepartmentServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceApplication.class);
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DepartmentServiceApplication.class, args);

        Environment env = ctx.getEnvironment();
        String port = env.getProperty("server.port");
        String[] activeProfiles = env.getActiveProfiles();
        String name = env.getProperty("spring.application.name");

        String version = env.getProperty("info.build.version", "SNAPSHOT");
        LOGGER.info(
                "\n************************************************************************\n"
                        + "\t{}:{}\n"
                        + "\tListening on port: {}\n"
                        + "\tActive profiles  : {}\n"
                        + "************************************************************************",
                name,
                version,
                port,
                activeProfiles);
    }

}
