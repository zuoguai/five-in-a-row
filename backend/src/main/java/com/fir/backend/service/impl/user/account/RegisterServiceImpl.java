package com.fir.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fir.backend.mapper.UserMapper;
import com.fir.backend.pojo.User;
import com.fir.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String,String> map = new HashMap<>();

        if(username == null || password == null || confirmedPassword == null){
            map.put("error_message","不能有空项");
            return map;
        }
        username = username.trim();
        if(username.length() == 0){
            map.put("error_message","用户名不能为空");
            return map;
        }
        if(username.length() > 20){
            map.put("error_message","用户名过长");
            return map;
        }
        if(password.length() > 20 || confirmedPassword.length() > 20){
            map.put("error_message","密码过长");
            return map;
        }
        if(password.length() < 6 || confirmedPassword.length() < 6){
            map.put("error_message","密码不能少于六位");
            return map;
        }
        if(!password.equals(confirmedPassword)){
            map.put("error_message","前后密码不一致");
            return map;
        }

        List<User> list = null;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        list = userMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            map.put("error_message","该用户名已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://ss0.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3498215567,1247959937&fm=253&gp=0.jpg";

        User temp = userMapper.selectById ((int)(Math.random()*4) + 1);
        if (temp != null ){
            photo = temp.getPhoto();
        }

        User user = new User(null, username,encodedPassword,photo,1500,0,0);
        userMapper.insert(user);
        map.put("error_message", "success");
        System.out.println("USER ID: "+user.getId()+" register successfully");
        return map;
    }
}
