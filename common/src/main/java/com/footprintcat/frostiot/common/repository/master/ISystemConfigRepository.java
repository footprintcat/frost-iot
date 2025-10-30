/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.repository.master;

import com.footprintcat.frostiot.common.dto.master.SystemConfigDTO;

public interface ISystemConfigRepository {

    public void setConfig(String owner, String key, String value, Long expireTimestamp);

    public String getConfigValue(String key);

    public SystemConfigDTO getConfig(String key);

    public SystemConfigDTO getDTO();
}
