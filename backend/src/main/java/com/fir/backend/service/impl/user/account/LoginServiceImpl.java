package com.fir.backend.service.impl.user.account;

import com.fir.backend.pojo.User;
import com.fir.backend.service.impl.utils.UserDetailsImpl;
import com.fir.backend.service.user.account.LoginService;
import com.fir.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        String jwt = JwtUtil.createJWT(user.getId().toString());

        map.put("error_message", "success");
        map.put("token", jwt);
        System.out.println("USER ID: " +user.getId() +" login successfully");
        return map;
    }
}
