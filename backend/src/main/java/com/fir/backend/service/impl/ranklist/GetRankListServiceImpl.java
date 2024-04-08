package com.fir.backend.service.impl.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fir.backend.mapper.UserMapper;
import com.fir.backend.pojo.Record;
import com.fir.backend.pojo.User;
import com.fir.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRankListServiceImpl implements GetRankListService {
    @Autowired
    private UserMapper userMapper;
    private final Integer PAGE = 6;
    @Override
    public JSONObject getList(Integer page) {
        IPage<User> rankIPage = new Page<>(page,PAGE);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectPage(rankIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        List<JSONObject> items = new ArrayList<>();
        for(User u : users){
            JSONObject item = new JSONObject();
            item.put("username",u.getUsername());
            item.put("photo",u.getPhoto());
            item.put("win",u.getWin());
            item.put("lose",u.getLose());
            item.put("rating",u.getRating());
            item.put("id",u.getId());
            items.add(item);
        }
        resp.put("users",items);

        resp.put("users_count",userMapper.selectCount(null));

        return resp;

    }
}
