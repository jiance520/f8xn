package com.shiro.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping("/index")
    public String index() {
        return "index";// index=/WEB-INF/jsp/index.jsp
    }
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
    @RequestMapping("/unAuth")
    public String unAuth() {
        return "unauth";
    }
}
