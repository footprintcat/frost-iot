/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.dto.master;

import lombok.Data;

@Data
public class SystemConfigDTO {
    private Long id;
    private String configKey;
    private String configValue;
    private Long expireTimestamp;
    private String owner;
}
