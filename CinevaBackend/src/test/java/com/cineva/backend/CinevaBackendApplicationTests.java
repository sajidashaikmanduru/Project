package com.cineva.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
class CinevaBackendApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        // Verify that the application context loads successfully
        System.out.println("Application context loaded successfully with beans: " + context.getBeanDefinitionCount());
    }
}