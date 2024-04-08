package com.fir.backend.controller.user.account;

import com.fir.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @PostMapping("/api/user/account/login/")
    public Map<String, String> login(@RequestParam String username,
                                     @RequestParam String password){

        return loginService.getToken(username, password);
    }

}
