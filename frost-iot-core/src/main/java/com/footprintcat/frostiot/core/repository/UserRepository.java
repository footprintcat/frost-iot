/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.repository;

import com.footprintcat.frostiot.core.pojo.test.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User 数据访问接口，提供类似 MyBatis-Plus 的 CRUD 操作
 * <p>
 * CrudRepository 提供了基本的增删改查方法，类似 MyBatis-Plus 的 BaseMapper
 * JpaRepository
 *
 * @since 2025-09-29
 */
@Repository
public interface UserRepository extends JpaRepository<@NotNull User, @NotNull Long> {

    // 根据用户名查找用户，类似 MyBatis-Plus 的 lambda 条件查询
    Optional<User> findByUsername(String username);

    // 根据创建时间范围查询用户
    List<User> findByCreatedAtBetween(OffsetDateTime startTime, OffsetDateTime endTime);

    // // 批量删除用户，类似 MyBatis-Plus 的批量删除
    // void deleteAllByIdInList(List<Long> ids);

    // 统计用户数量
    long count();

    // 自定义 SQL 查询示例，类似 MyBatis-Plus 的 @Select 注解
    @Query("SELECT u FROM User u WHERE u.username LIKE :usernamePattern")
    List<User> findByUsernameLike(String usernamePattern);

    // 分页查询示例（结合 Micronaut Data 的分页功能）
    // List<User> findAll(Pageable pageable);

    // 批量保存，CrudRepository 自带的 saveAll 方法实现了批量保存功能
    // @Override
    // List<User> saveAll(Iterable<User> entities);
}
