package com.fir.backend.controller.record;

import com.alibaba.fastjson.JSONObject;
import com.fir.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRecordListController {
    @Autowired
    private GetRecordListService getRecordListService;
    @GetMapping("/api/record/getlist/")
    public JSONObject getRecordList(@RequestParam Map<String,String> data){
        Integer page = Integer.parseInt(data.get("page"));

        JSONObject jsonObject = getRecordListService.getList(page);
        return jsonObject;
    }
}
