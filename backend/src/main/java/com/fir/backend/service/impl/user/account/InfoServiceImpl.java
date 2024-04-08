package com.fir.backend.service.impl.user.account;

import com.fir.backend.pojo.User;
import com.fir.backend.service.impl.utils.UserDetailsImpl;
import com.fir.backend.service.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String>map = new HashMap<>();
        map.put("error_message","success");
        map.put("id",user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", user.getPhoto());
        map.put("rating",user.getRating().toString());
        map.put("win",user.getWin().toString());
        map.put("lose",user.getLose().toString());


        return map;
    }
}
