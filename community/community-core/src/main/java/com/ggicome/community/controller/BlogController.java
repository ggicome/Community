package com.ggicome.community.controller;

import com.ggicome.community.annotation.ResponseWrapper;
import com.ggicome.community.domain.Blog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/test")
    public String test(@Validated @RequestBody Blog blog) {
        return "111";
    }
}
