package com.corechan.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;


@Controller
@ApiIgnore
@RequestMapping("")
public class RootController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String welcome() {
        return "redirect:/3d/welcome.html";
    }
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "redirect:/index.html";
    }
}
