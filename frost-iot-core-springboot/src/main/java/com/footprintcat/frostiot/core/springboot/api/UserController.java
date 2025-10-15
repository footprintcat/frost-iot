/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.api;

import com.footprintcat.frostiot.core.springboot.pojo.test.User;
import com.footprintcat.frostiot.core.springboot.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User 控制器，提供类似 MyBatis-Plus 的 RESTful API 接口
 * <p>
 * 实现用户的增删改查操作的 HTTP 接口
 *
 * @since 2025-09-29
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 新增用户
     * 类似 MyBatis-Plus 的 save 操作
     */
    @PostMapping
    public User create(@RequestBody User user) {
        User savedUser = userService.save(user);
        return savedUser;
    }

    /**
     * 批量新增用户
     * 类似 MyBatis-Plus 的 saveBatch 操作
     */
    @PostMapping("/batch")
    public List<User> createBatch(@RequestBody List<User> users) {
        List<User> savedUsers = userService.saveBatch(users);
        return savedUsers;
    }

    /**
     * 更新用户
     * 类似 MyBatis-Plus 的 updateById 操作
     */
    @PostMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return updatedUser;
    }

    /**
     * 删除用户
     * 类似 MyBatis-Plus 的 removeById 操作
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    /**
     * 批量删除用户
     * 类似 MyBatis-Plus 的 removeByIds 操作
     */
    @DeleteMapping("/batch")
    public void deleteBatch(@RequestBody List<Long> ids) {
        userService.deleteBatchIds(ids);
    }

    /**
     * 根据 ID 查询用户
     * 类似 MyBatis-Plus 的 getById 操作
     */
    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return user;
    }

    /**
     * 查询所有用户
     * 类似 MyBatis-Plus 的 list 操作
     */
    @GetMapping
    public List<User> list() {
        List<User> users = userService.list();
        return users;
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public Optional<User> getByUsername(@PathVariable String username) {
        Optional<User> user = userService.getByUsername(username);
        return user;
    }

    /**
     * 根据创建时间范围查询用户
     */
    @GetMapping("/created-between")
    public List<User> listByCreatedAtBetween(
        @RequestParam OffsetDateTime startTime,
        @RequestParam OffsetDateTime endTime) {
        List<User> users = userService.listByCreatedAtBetween(startTime, endTime);
        return users;
    }

    /**
     * 根据用户名模糊查询用户
     */
    @GetMapping("/username-like/{pattern}")
    public List<User> listByUsernameLike(@PathVariable String pattern) {
        List<User> users = userService.listByUsernameLike("%" + pattern + "%");
        return users;
    }

    /**
     * 统计用户数量
     * 类似 MyBatis-Plus 的 count 操作
     */
    @GetMapping("/count")
    public Long count() {
        return userService.count();
    }

    /**
     * 判断用户是否存在
     * 类似 MyBatis-Plus 的 existsById 操作
     */
    @GetMapping("/exists/{id}")
    public Boolean existsById(@PathVariable Long id) {
        return userService.existsById(id);
    }
}
