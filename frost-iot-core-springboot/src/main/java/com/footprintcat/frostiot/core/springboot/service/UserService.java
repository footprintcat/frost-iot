/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.service;

import com.footprintcat.frostiot.core.springboot.pojo.test.User;
import com.footprintcat.frostiot.core.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User 服务层，实现类似 MyBatis-Plus 的业务逻辑
 * <p>
 * 提供用户的增删改查操作，类似 MyBatis-Plus 的 Service 层功能
 *
 * @since 2025-09-29
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新增用户，类似 MyBatis-Plus 的 save 方法
     */
    @Transactional
    public User save(User user) {
        // 设置创建时间
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(OffsetDateTime.now());
        }
        return userRepository.save(user);
    }

    /**
     * 批量新增用户，类似 MyBatis-Plus 的 saveBatch 方法
     */
    @Transactional
    public List<User> saveBatch(List<User> users) {
        // 为每个用户设置创建时间
        users.forEach(user -> {
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(OffsetDateTime.now());
            }
        });
        return userRepository.saveAll(users);
    }

    // /**
    //  * 更新用户，类似 MyBatis-Plus 的 updateById 方法
    //  */
    // @Transactional
    // public User update(User user) {
    //     return userRepository.update(user);
    // }

    /**
     * 根据 ID 删除用户，类似 MyBatis-Plus 的 removeById 方法
     */
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // /**
    //  * 批量删除用户，类似 MyBatis-Plus 的 removeByIds 方法
    //  */
    // @Transactional
    // public void deleteBatchIds(List<Long> ids) {
    //     userRepository.deleteAllByIdInList(ids);
    // }

    /**
     * 根据 ID 查询用户，类似 MyBatis-Plus 的 getById 方法
     */
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 查询所有用户，类似 MyBatis-Plus 的 list 方法
     */
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * 根据用户名查询用户
     */
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 根据创建时间范围查询用户
     */
    public List<User> listByCreatedAtBetween(OffsetDateTime startTime, OffsetDateTime endTime) {
        return userRepository.findByCreatedAtBetween(startTime, endTime);
    }

    /**
     * 根据用户名模糊查询用户
     */
    public List<User> listByUsernameLike(String usernamePattern) {
        return userRepository.findByUsernameLike(usernamePattern);
    }

    /**
     * 统计用户数量，类似 MyBatis-Plus 的 count 方法
     */
    public long count() {
        return userRepository.count();
    }

    /**
     * 判断用户是否存在，类似 MyBatis-Plus 的 existsById 方法
     */
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
