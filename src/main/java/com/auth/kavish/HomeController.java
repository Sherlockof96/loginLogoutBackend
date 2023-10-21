package com.auth.kavish;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    String message = "This is a war file from Azure DevOps pipelines!";

    @RequestMapping("/")
    public String index() {
        return message;
    }
}