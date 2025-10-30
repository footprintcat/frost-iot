/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.footprintcat.frostiot.core.springboot.entity.test.Messages;
import com.footprintcat.frostiot.core.springboot.enums.MessageType;
import com.footprintcat.frostiot.core.springboot.mapper.data.TDengineMapper;
import com.footprintcat.frostiot.core.springboot.mapper.master.MessagesMapper;
import com.footprintcat.frostiot.core.springboot.mapper.master.MyMapper;
import com.footprintcat.frostiot.core.springboot.mapper.master.MyXmlMapper;
import com.footprintcat.frostiot.core.springboot.pojo.test.Message;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.Date;

@OpenAPIDefinition(
    info = @Info(
        title = "Frost IoT 开放接口",
        version = "${frost-iot.api.version:0.0}",
        description = "Frost IoT Core 模块开放接口",
        license = @License(name = "BSD 3-Clause License", url = "https://opensource.org/license/BSD-3-clause"),
        contact = @Contact(url = "https://iot.footprintcat.com", name = "Frost IoT Project (Footprintcat Open Source)", email = "contact@footprintcat.com")
    )
)
@SpringBootApplication
public class FrostIotCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrostIotCoreApplication.class, args);
    }

    @Bean
    @Order(10)
    ApplicationRunner tdengine(TDengineMapper mapper) {
        return args -> {
            System.out.println("TDengineMapper - XML注解形式");
            var localDateTime = mapper.selectNow();
            System.out.println(localDateTime);
        };
    }

    @Bean
    @Order(21)
    ApplicationRunner run(MyMapper mapper) {
        return args -> {
            System.out.println("Mapper - SQL注解形式");
            mapper.insert(new Message(null, "Hello World on run!"));
            Message message = mapper.select(1);
            System.out.println(message);
        };
    }

    @Bean
    @Order(22)
    ApplicationRunner runWithXmlMapper(MyXmlMapper mapper) {
        return args -> {
            System.out.println("Mapper - XML注解形式");
            mapper.insert(new Message(null, "Hello World! on runWithXmlMapper"));
            Message message = mapper.select(2);
            System.out.println(message);
        };
    }

    @Bean
    @Order(23)
    ApplicationRunner runWithMessagesMapper(MessagesMapper mapper) {
        return args -> {
            System.out.println("Mapper extends BaseMapper - XML注解形式");
            mapper.insertXml(new Messages(null, "Hello World! on runWithMessagesMapper"));
        };
    }

    @Bean
    @Order(24)
    ApplicationRunner runWithMybatisPlus(MessagesMapper messagesMapper) {
        return args -> {
            System.out.println("------------演示insert-----------------");
            Messages messages = new Messages(null, "Hello MybatisPlus", MessageType.VOICE);
            messagesMapper.insert(messages);
            System.out.println("------------演示select-----------------");
            System.out.println("simple query:" + messagesMapper.selectById(messages.getId()));
            System.out.println("query wrapper:" + messagesMapper.selectOne(Wrappers.<Messages>query().eq("id", 1)));
            System.out.println("query wrapper:" + messagesMapper.selectOne(Wrappers.<Messages>query().eq("id", messages.getId())));
            System.out.println("------------演示update-----------------");
            messages.setMessageType(MessageType.TEXT);
            messagesMapper.updateById(messages);
            messagesMapper.update(messages, Wrappers.<Messages>update().eq("id", 1L));
            System.out.println("------------演示delete-----------------");
            messagesMapper.delete(Wrappers.<Messages>update().eq("id", 0L));
            messagesMapper.deleteById(messages);
            System.out.println("------------演示page-----------------");
            Page<Messages> page = messagesMapper.selectPage(new Page<>(1, 2), Wrappers.emptyWrapper());
            System.out.println("total:" + page.getTotal() + ",records" + page.getRecords());
            System.out.println("------------演示lambda-----------------");
            System.out.println("lambda query:" + messagesMapper.selectOne(Wrappers.<Messages>lambdaQuery().select(Messages::getMessage).eq(Messages::getId, messages.getId())));
            System.out.println("lambda update:" + messagesMapper.update(messages, Wrappers.<Messages>lambdaUpdate().eq(Messages::getId, 1L)));
            System.out.println("lambda delete:" + messagesMapper.delete(Wrappers.<Messages>lambdaUpdate().eq(Messages::getId, 1L)));
        };
    }

    @PostConstruct
    void testFastJSON() {
        JSONObject object = new JSONObject();
        object.put("id", "1");
        object.put("type", "apple");
        object.put("snowflakeId", IdWorker.getIdStr());
        object.put("date", new Date());

        String jsonString = JSON.toJSONString(object);
        System.out.println(jsonString);

        JSONObject parse = JSON.parseObject(jsonString);
        System.out.println(parse.getInteger("id"));
        System.out.println(parse.getLong("id"));
        System.out.println(parse.getString("id"));
        System.out.println(parse.getDate("date"));
        System.out.println(parse.getString("snowflakeId"));
        System.out.println(parse.getLong("snowflakeId"));
        System.out.println(parse);
    }

}
