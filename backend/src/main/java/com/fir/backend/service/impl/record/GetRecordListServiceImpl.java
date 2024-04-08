package com.fir.backend.service.impl.record;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fir.backend.mapper.RecordMapper;
import com.fir.backend.mapper.UserMapper;
import com.fir.backend.pojo.Record;
import com.fir.backend.pojo.User;
import com.fir.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {
    private final Integer PAGE = 6;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public JSONObject getList(Integer page) {
        IPage<Record> recordIPage = new Page<>(page,PAGE);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Record> records = recordMapper.selectPage(recordIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();


        List<JSONObject> items = new ArrayList<>();

        for(Record r : records){
            JSONObject item = new JSONObject();
            User userA = userMapper.selectById(r.getAId());
            User userB = userMapper.selectById(r.getBId());
            if(userA != null && userB != null){
                item.put("a_username",userA.getUsername());
                item.put("a_photo",userA.getPhoto());
                item.put("b_username",userB.getUsername());
                item.put("b_photo",userB.getPhoto());
                String result;
                if(r.getLoser().equals("A")){
                    result = userB.getUsername() + " 胜";
                }else{
                    result = userA.getUsername() + " 胜";

                }
                item.put("result",result);
                item.put("createtime",r.getTime());

                item.put("id",r.getId());
                items.add(item);
            }

        }
        resp.put("records",items);
        resp.put("records_count",recordMapper.selectCount(null));

        return resp;
    }
}
