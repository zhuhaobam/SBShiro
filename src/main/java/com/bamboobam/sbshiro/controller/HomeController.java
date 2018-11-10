package com.bamboobam.sbshiro.controller;


import com.bamboobam.sbshiro.config.reposeconfig.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping(value = "/")
    public ResultBean home() {
        String swagger2url = "http://localhost:8080/swagger-ui.html";
        return new ResultBean(swagger2url);
    }

    @GetMapping(value = "/csrf")
    public ResultBean csrf() {
        String csrfmessage = "CSRF(Cross-site request forgery)跨站请求伪造";
        return new ResultBean(csrfmessage);
    }
}
