package com.example.mummoomserver.domain.Dog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/secondtestdferfwefwf")
    public String test(){
        return "test";
    }
}
