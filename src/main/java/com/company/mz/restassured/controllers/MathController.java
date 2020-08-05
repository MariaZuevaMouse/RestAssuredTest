package com.company.mz.restassured.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/math")
public class MathController {
    @GetMapping("/sum")
    public String getSum(@RequestParam Integer a, @RequestParam Integer b){
        System.out.println(a+b);
        return String.valueOf(a+b);
    }
    @GetMapping("/multiply")
    public String getMultiply(@RequestParam Integer a, @RequestParam Integer b){
        return  String.valueOf(a*b);
    }
}
