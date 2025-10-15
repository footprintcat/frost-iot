/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.service;

import com.footprintcat.frostiot.core.pojo.test.User;
import com.footprintcat.frostiot.core.repository.UserRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * UserService 测试类，验证类似 MyBatis-Plus 的业务逻辑
 *
 * @since 2025-09-29
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;

    @Resource
    UserRepository userRepository;

    @Test
    void testSave() {
        // 清空所有数据
        userRepository.deleteAll();

        // 创建用户
        User user = new User();
        user.setUsername("serviceuser1");
        // 不设置创建时间，测试自动设置

        // 保存用户
        User savedUser = userService.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("serviceuser1", savedUser.getUsername());
        assertNotNull(savedUser.getCreatedAt());

        // 清理测试数据
        userService.deleteById(savedUser.getId());
    }

    @Test
    void testSaveBatch() {
        // 清空所有数据
        userRepository.deleteAll();

        // 创建多个用户
        User user1 = new User();
        user1.setUsername("batchserviceuser1");

        User user2 = new User();
        user2.setUsername("batchserviceuser2");

        // 批量保存用户
        List<User> users = Arrays.asList(user1, user2);
        List<User> savedUsers = userService.saveBatch(users);
        assertEquals(2, savedUsers.size());

        // 验证每个用户都有创建时间
        for (User savedUser : savedUsers) {
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreatedAt());
        }

        // 清理测试数据
        List<Long> ids = savedUsers.stream().map(User::getId).toList();
        // userService.deleteBatchIds(ids);
    }

    @Test
    void testUpdate() {
        // 创建并保存用户
        User user = new User();
        user.setUsername("updateserviceuser");
        user.setCreatedAt(OffsetDateTime.now());
        User savedUser = userRepository.save(user);

        // // 更新用户信息
        // savedUser.setUsername("updatedserviceuser");
        // User updatedUser = userService.update(savedUser);

        // // 验证更新结果
        // assertEquals(savedUser.getId(), updatedUser.getId());
        // assertEquals("updatedserviceuser", updatedUser.getUsername());

        // 清理测试数据
        userService.deleteById(savedUser.getId());
    }

    @Test
    void testGetById() {
        // 创建并保存用户
        User user = new User();
        user.setUsername("getbyidserviceuser");
        user.setCreatedAt(OffsetDateTime.now());
        User savedUser = userRepository.save(user);

        // 根据 ID 查询用户
        Optional<User> foundUser = userService.getById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("getbyidserviceuser", foundUser.get().getUsername());

        // 清理测试数据
        userService.deleteById(savedUser.getId());
    }

    @Test
    void testList() {
        // 清空所有数据
        userRepository.deleteAll();

        // 创建并保存多个用户
        User user1 = new User();
        user1.setUsername("listserviceuser1");
        user1.setCreatedAt(OffsetDateTime.now());

        User user2 = new User();
        user2.setUsername("listserviceuser2");
        user2.setCreatedAt(OffsetDateTime.now());

        userRepository.saveAll(Arrays.asList(user1, user2));

        // 查询所有用户
        List<User> users = userService.list();
        assertEquals(2, users.size());

        // 清理测试数据
        userRepository.deleteAll();
    }

    @Test
    void testListByCreatedAtBetween() {
        // 清空所有数据
        userRepository.deleteAll();

        // 创建并保存多个用户，有不同的创建时间
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime earlier = now.minusHours(2);
        OffsetDateTime later = now.plusHours(2);

        User user1 = new User();
        user1.setUsername("timeuserservice1");
        user1.setCreatedAt(earlier);

        User user2 = new User();
        user2.setUsername("timeuserservice2");
        user2.setCreatedAt(now);

        User user3 = new User();
        user3.setUsername("timeuserservice3");
        user3.setCreatedAt(later);

        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        // 根据时间范围查询用户
        OffsetDateTime startTime = now.minusHours(1);
        OffsetDateTime endTime = now.plusHours(1);
        List<User> users = userService.listByCreatedAtBetween(startTime, endTime);

        // 应该只查询到中间的用户
        assertEquals(1, users.size());
        assertEquals("timeuserservice2", users.get(0).getUsername());

        // 清理测试数据
        userRepository.deleteAll();
    }

    @Test
    void testCountAndExistsById() {
        // 清空所有数据
        userRepository.deleteAll();
        assertEquals(0, userService.count());

        // 创建并保存用户
        User user = new User();
        user.setUsername("countserviceuser");
        user.setCreatedAt(OffsetDateTime.now());
        User savedUser = userRepository.save(user);

        // 验证计数和存在性
        assertEquals(1, userService.count());
        assertTrue(userService.existsById(savedUser.getId()));
        assertFalse(userService.existsById(9999L));

        // 清理测试数据
        userService.deleteById(savedUser.getId());
    }
}
