/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import java.time.LocalDateTime;

public class NodeHealthInfo {
    private final String nodeId;
    private volatile boolean online;
    private volatile LocalDateTime lastSeen;
    // private volatile double loadFactor;
    private volatile int heartbeatCount;
    private volatile int offlineCount;
    private volatile long totalUptime; // 总运行时间(ms)
    private volatile LocalDateTime firstSeen;
    private volatile LocalDateTime lastOfflineTime;

    public NodeHealthInfo(String nodeId) {
        this.nodeId = nodeId;
        this.firstSeen = LocalDateTime.now();
        this.heartbeatCount = 0;
        this.offlineCount = 0;
        this.totalUptime = 0;
    }

    // Getters and Setters
    public String getNodeId() { return nodeId; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) {
        this.online = online;
        if (!online) {
            this.lastOfflineTime = LocalDateTime.now();
        }
    }
    public LocalDateTime getLastSeen() { return lastSeen; }
    public void setLastSeen(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }
    // public double getLoadFactor() { return loadFactor; }
    // public void setLoadFactor(double loadFactor) { this.loadFactor = loadFactor; }
    public int getHeartbeatCount() { return heartbeatCount; }
    public void incrementHeartbeatCount() { this.heartbeatCount++; }
    public int getOfflineCount() { return offlineCount; }
    public void incrementOfflineCount() { this.offlineCount++; }
    public long getTotalUptime() { return totalUptime; }
    public LocalDateTime getFirstSeen() { return firstSeen; }
    public LocalDateTime getLastOfflineTime() { return lastOfflineTime; }

    /**
     * 计算可用性比率
     */
    public double getAvailabilityRate() {
        if (heartbeatCount == 0) return 0.0;
        return 1.0 - ((double) offlineCount / heartbeatCount);
    }

    @Override
    public String toString() {
        return String.format("NodeHealthInfo{nodeId='%s', online=%s, availability=%.2f%%}",
            nodeId, online, getAvailabilityRate() * 100);
    }
}
