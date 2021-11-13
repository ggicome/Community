package com.ggicome.community.controller;

import com.ggicome.community.annotation.ResponseWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: community
 * @description: blog
 * @author: ggicome
 * @date: 2021-11-07
 **/
@RestController
@RequestMapping("/blog")
@ResponseWrapper
public class BlogController {
    @GetMapping("test")
    public String test() {
        return "111";
    }
}
