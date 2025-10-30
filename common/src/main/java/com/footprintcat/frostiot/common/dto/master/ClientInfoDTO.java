/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.dto.master;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientInfoDTO {
    private Long id;
    private String targetNodeId;
    private String protocol;
    private Boolean useSsl;
    private String host;
    private Integer port;
    private String direction;

    private Date lastConnTime;
    private Date lastOfflineTime;

    private Date createTime;
    private Date updateTime;
}
