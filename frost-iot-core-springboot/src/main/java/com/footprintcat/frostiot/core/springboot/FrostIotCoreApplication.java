/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.footprintcat.frostiot.core.springboot.enums.MessageType;
import com.footprintcat.frostiot.core.springboot.mapper.MessagesMapper;
import com.footprintcat.frostiot.core.springboot.mapper.MyXmlMapper;
import com.footprintcat.frostiot.core.springboot.pojo.test.Message;
import com.footprintcat.frostiot.core.springboot.entity.test.Messages;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

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
    @Order(1)
    ApplicationRunner run(MyMapper mapper) {
        return args -> {
            mapper.insert(new Message(null, "Hello World on run!"));
            Message message = mapper.select(1);
            System.out.println(message);
        };
    }

    @Bean
    @Order(2)
    ApplicationRunner runWithXmlMapper(MyXmlMapper mapper) {
        return args -> {
            mapper.insert(new Message(null, "Hello World! on runWithXmlMapper"));
            Message message = mapper.select(2);
            System.out.println(message);
        };
    }

    @Bean
    @Order(2)
    ApplicationRunner runWithMessagesMapper(MessagesMapper mapper) {
        return args -> {
            mapper.insertXml(new Messages(null, "Hello World! on runWithMessagesMapper"));
        };
    }

    @Bean
    @Order(3)
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

    @Mapper
    interface MyMapper {

        @Insert("""
                  INSERT INTO messages (message)
                    VALUES (#{message})
                """)
        void insert(Message message);

        @Select("""
                  SELECT
                    id
                    ,message
                  FROM
                    messages
                  WHERE
                    id = #{id}
                """)
        Message select(Integer id);

    }
}
