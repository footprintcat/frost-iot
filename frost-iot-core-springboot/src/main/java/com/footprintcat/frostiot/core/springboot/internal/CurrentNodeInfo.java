/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.internal;

import com.footprintcat.frostiot.common.internal.ICurrentNodeInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CurrentNodeInfo implements ICurrentNodeInfo, Serializable {

    String nodeId;
    String nodeType;

    private CurrentNodeInfo() {
    }

    public CurrentNodeInfo(String nodeId, String nodeType) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
    }
}
