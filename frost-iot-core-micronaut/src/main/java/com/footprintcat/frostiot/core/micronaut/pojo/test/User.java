/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.micronaut.pojo.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @since 2025-09-29
 */
@Data
@Entity
@Table(name = "users", schema = "public") // 明确指定 schema
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL 自增主键
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt; // PostgreSQL 推荐使用带时区的时间

    // PostgreSQL 数组类型支持
    @Column(name = "tags", columnDefinition = "TEXT[]")
    private List<String> tags;

    // 必须有无参构造器
    public User() {
    }

}
