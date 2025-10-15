/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.micronaut.api;

import com.footprintcat.frostiot.core.micronaut.pojo.test.User;
import com.footprintcat.frostiot.core.micronaut.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;

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
@Controller("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 新增用户
     * 类似 MyBatis-Plus 的 save 操作
     */
    @Post
    public HttpResponse<User> create(@Body User user) {
        User savedUser = userService.save(user);
        return HttpResponse.created(savedUser);
    }

    /**
     * 批量新增用户
     * 类似 MyBatis-Plus 的 saveBatch 操作
     */
    @Post("/batch")
    public HttpResponse<List<User>> createBatch(@Body List<User> users) {
        List<User> savedUsers = userService.saveBatch(users);
        return HttpResponse.created(savedUsers);
    }

    /**
     * 更新用户
     * 类似 MyBatis-Plus 的 updateById 操作
     */
    @Put("/{id}")
    public HttpResponse<User> update(@PathVariable Long id, @Body User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return HttpResponse.ok(updatedUser);
    }

    /**
     * 删除用户
     * 类似 MyBatis-Plus 的 removeById 操作
     */
    @Delete("/{id}")
    public HttpResponse<?> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return HttpResponse.noContent();
    }

    /**
     * 批量删除用户
     * 类似 MyBatis-Plus 的 removeByIds 操作
     */
    @Delete("/batch")
    public HttpResponse<?> deleteBatch(@Body List<Long> ids) {
        userService.deleteBatchIds(ids);
        return HttpResponse.noContent();
    }

    /**
     * 根据 ID 查询用户
     * 类似 MyBatis-Plus 的 getById 操作
     */
    @Get("/{id}")
    public HttpResponse<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return user.map(HttpResponse::ok)
            .orElseGet(HttpResponse::notFound);
    }

    /**
     * 查询所有用户
     * 类似 MyBatis-Plus 的 list 操作
     */
    @Get
    public HttpResponse<List<User>> list() {
        List<User> users = userService.list();
        return HttpResponse.ok(users);
    }

    /**
     * 根据用户名查询用户
     */
    @Get("/username/{username}")
    public HttpResponse<User> getByUsername(@PathVariable String username) {
        Optional<User> user = userService.getByUsername(username);
        return user.map(HttpResponse::ok)
            .orElseGet(HttpResponse::notFound);
    }

    /**
     * 根据创建时间范围查询用户
     */
    @Get("/created-between")
    public HttpResponse<List<User>> listByCreatedAtBetween(
        @QueryValue OffsetDateTime startTime,
        @QueryValue OffsetDateTime endTime) {
        List<User> users = userService.listByCreatedAtBetween(startTime, endTime);
        return HttpResponse.ok(users);
    }

    /**
     * 根据用户名模糊查询用户
     */
    @Get("/username-like/{pattern}")
    public HttpResponse<List<User>> listByUsernameLike(@PathVariable String pattern) {
        List<User> users = userService.listByUsernameLike("%" + pattern + "%");
        return HttpResponse.ok(users);
    }

    /**
     * 统计用户数量
     * 类似 MyBatis-Plus 的 count 操作
     */
    @Get("/count")
    public HttpResponse<Long> count() {
        return HttpResponse.ok(userService.count());
    }

    /**
     * 判断用户是否存在
     * 类似 MyBatis-Plus 的 existsById 操作
     */
    @Get("/exists/{id}")
    public HttpResponse<Boolean> existsById(@PathVariable Long id) {
        return HttpResponse.ok(userService.existsById(id));
    }
}
