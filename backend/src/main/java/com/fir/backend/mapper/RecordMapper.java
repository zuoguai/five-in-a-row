package com.fir.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fir.backend.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
