/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MicroBlogController {

    @RequestMapping("/version")
    public Version greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Version("v1.1-spring-rest-mvc");
    }

}