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
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "ServerInfo", description = "当前节点需启动的服务信息")
@TableName("topology_server_info")
public class ServerInfo {

    @Schema(description = "雪花id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "服务类型")
    @TableField("server_type")
    private String serverType;

    // @Schema(description = "是否采用安全协议")
    // @TableField("use_ssl")
    // private Boolean useSsl;

    @Schema(description = "主机地址")
    @TableField("host")
    private String host;

    @Schema(description = "端口号")
    @TableField("port")
    private Integer port;

}
