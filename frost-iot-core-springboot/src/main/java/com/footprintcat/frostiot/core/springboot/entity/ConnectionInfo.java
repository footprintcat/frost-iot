/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ConnectionInfo {

    @Schema(description = "雪花id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "目标节点")
    @TableField("target_node_id")
    private Long targetNodeId;

    @Schema(description = "传输协议")
    @TableField("protocol")
    private String protocol;

    @Schema(description = "是否采用安全协议")
    @TableField("use_ssl")
    private Boolean useSsl;

    @Schema(description = "主机地址")
    @TableField("host")
    private String host;

    @Schema(description = "端口号")
    @TableField("port")
    private Integer port;

    @Schema(description = "传输方向（positive：正向；reverse：反向）")
    @TableField("direction")
    private String direction;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "最近一次更新时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "最新连接时间")
    @TableField("last_conn_time")
    private Date lastConnTime;

    @Schema(description = "最新离线时间")
    @TableField("last_offline_time")
    private Date lastOfflineTime;

}
