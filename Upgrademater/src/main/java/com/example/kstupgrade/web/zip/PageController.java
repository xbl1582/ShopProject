package com.example.kstupgrade.web.zip;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

//    @RequestMapping("/viewer2")
//    public String viewer2(){
//        return "viewer2";
//    }


}
