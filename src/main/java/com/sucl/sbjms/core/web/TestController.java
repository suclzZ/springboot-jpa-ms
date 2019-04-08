package com.sucl.sbjms.core.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/8
 */
@RestController
public class TestController {

    @GetMapping("/a")
    public String a(){
        return "hello";
    }

    @GetMapping("/b")
    public List b(){
        return Arrays.asList("a","b","c");
    }

    @GetMapping("/c")
    public void c(){
        System.err.println("void");
    }
}
