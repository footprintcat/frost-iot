/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.repository;

import com.footprintcat.frostiot.core.springboot.pojo.test.User;
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
 * UserRepository 测试类，验证类似 MyBatis-Plus 的 CRUD 操作
 *
 * @since 2025-09-29
 */
@SpringBootTest
public class UserRepositoryTest {

    @Resource
    UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        // 创建用户
        User user = new User();
        user.setUsername("testuser1");
        user.setCreatedAt(OffsetDateTime.now());
        user.setTags(Arrays.asList("tag1", "tag2"));

        // 保存用户
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("testuser1", savedUser.getUsername());

        // 根据 ID 查询用户
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("testuser1", foundUser.get().getUsername());
        assertEquals(2, foundUser.get().getTags().size());

        // 清理测试数据
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void testFindByUsername() {
        // 创建用户
        User user = new User();
        user.setUsername("testuser2");
        user.setCreatedAt(OffsetDateTime.now());

        // 保存用户
        User savedUser = userRepository.save(user);

        // 根据用户名查询用户
        Optional<User> foundUser = userRepository.findByUsername("testuser2");
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("testuser2", foundUser.get().getUsername());

        // 清理测试数据
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void testSaveAllAndDeleteAllByIdInList() {
        // 创建多个用户
        User user1 = new User();
        user1.setUsername("batchuser1");
        user1.setCreatedAt(OffsetDateTime.now());

        User user2 = new User();
        user2.setUsername("batchuser2");
        user2.setCreatedAt(OffsetDateTime.now());

        // 批量保存用户
        List<User> users = Arrays.asList(user1, user2);
        List<User> savedUsers = userRepository.saveAll(users);
        assertEquals(2, savedUsers.size());

        // 提取 ID 列表
        List<Long> ids = savedUsers.stream().map(User::getId).toList();

        // // 批量删除用户
        // userRepository.deleteAllByIdInList(ids);

        // 验证删除成功
        for (Long id : ids) {
            assertFalse(userRepository.existsById(id));
        }
    }

    @Test
    void testFindByUsernameLike() {
        // 创建用户
        User user1 = new User();
        user1.setUsername("prefixuser1");
        user1.setCreatedAt(OffsetDateTime.now());

        User user2 = new User();
        user2.setUsername("prefixuser2");
        user2.setCreatedAt(OffsetDateTime.now());

        User user3 = new User();
        user3.setUsername("otheruser");
        user3.setCreatedAt(OffsetDateTime.now());

        // 保存用户
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        // 模糊查询
        List<User> foundUsers = userRepository.findByUsernameLike("%prefix%");
        assertEquals(2, foundUsers.size());

        // 清理测试数据
        userRepository.deleteAll();
    }

    @Test
    void testCount() {
        // 清空所有数据
        userRepository.deleteAll();
        assertEquals(0, userRepository.count());

        // 创建用户
        User user = new User();
        user.setUsername("countuser");
        user.setCreatedAt(OffsetDateTime.now());

        // 保存用户
        userRepository.save(user);
        assertEquals(1, userRepository.count());

        // 清理测试数据
        userRepository.deleteAll();
    }
}
