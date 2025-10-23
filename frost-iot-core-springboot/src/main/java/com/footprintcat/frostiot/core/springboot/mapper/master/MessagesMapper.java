package com.footprintcat.frostiot.core.springboot.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.footprintcat.frostiot.core.springboot.entity.test.Messages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author nieqiurong
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
    void insertXml(Messages message);

}
