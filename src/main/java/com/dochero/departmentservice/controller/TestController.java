package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.client.DocumentRevisionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DocumentRevisionClient documentRevisionClient;

    @GetMapping("/test")
    public String checkServer() {
        return "Server is up and runing test cicd";
    }

    @GetMapping("/check-revision")
    public String checkDocumentRevisionService() {
        try {
            return documentRevisionClient.testService();
        } catch (Exception e) {
            return "Error, " + e.getMessage();
        }
    }
}
