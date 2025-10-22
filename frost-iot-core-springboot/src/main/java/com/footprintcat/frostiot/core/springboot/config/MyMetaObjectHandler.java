package com.footprintcat.frostiot.core.springboot.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author nieqiurong
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("进入insertFill填充了");
        setFieldValByName("createUser","聂秋秋",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("进入updateFill填充了");
        setFieldValByName("updateUser","聂秋秋",metaObject);
    }

}
