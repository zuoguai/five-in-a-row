package com.fir.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/pk/")
public class IndexController {

    @RequestMapping("/")
    public String getInfo(){

        return "<h1>作怪的伟大项目即将开工，请敬请期待。。。。</h1>";
    }

}
