package com.fir.backend.controller.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.fir.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRankListController {
    @Autowired
    private GetRankListService getRankListService;
    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String,String> data){
        return getRankListService.getList(Integer.parseInt(data.get("page")));
    }
}
