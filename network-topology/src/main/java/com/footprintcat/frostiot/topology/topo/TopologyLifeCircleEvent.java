/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.topo;

import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.common.enums.TopologyRelationEnum;
import com.footprintcat.frostiot.common.enums.TopologyStatusEnum;
import com.footprintcat.frostiot.common.internal.ICurrentNodeInfo;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;
import com.footprintcat.frostiot.topology.pojo.ConnectionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopologyLifeCircleEvent {

    private final ITopologyInfoRepository topologyInfoRepository;
    private final ICurrentNodeInfo currentNodeInfo;

    public TopologyLifeCircleEvent(ITopologyInfoRepository topologyInfoRepository, ICurrentNodeInfo currentNodeInfo) {
        this.topologyInfoRepository = topologyInfoRepository;
        this.currentNodeInfo = currentNodeInfo;
    }

    /**
     * 连接成功回调
     *
     * @param connectionInfo 连接信息
     */
    public void onConnect(ConnectionInfo connectionInfo) {
        TopologyInfoDTO topologyInfo = topologyInfoRepository.getByNodeIdAndTargetNodeId(currentNodeInfo.getNodeId(), connectionInfo.getTargetNodeId());

        if (Objects.isNull(topologyInfo)) {
            // 初次连接
            topologyInfo = new TopologyInfoDTO();
        }

        topologyInfo.setNodeId(currentNodeInfo.getNodeId());
        topologyInfo.setNodeType(NodeTypeEnum.getByCode(currentNodeInfo.getNodeType()));
        topologyInfo.setTargetNodeId(connectionInfo.getTargetNodeId());
        topologyInfo.setTargetNodeType(NodeTypeEnum.getByCode(connectionInfo.getTargetNodeType()));
        topologyInfo.setInterval(1);
        topologyInfo.setStatus(TopologyStatusEnum.CONNECTED);
        topologyInfo.setRelation(connectionInfo.getRelation());

        topologyInfoRepository.saveOrUpdate(topologyInfo);
    }

    /**
     * 暂时断连（断开连接）
     *
     * @param connectionInfo 断连目标节点
     */
    public void onDisconnect(ConnectionInfo connectionInfo) {
        // 标记连接的节点暂时断连
        topologyInfoRepository.setConnectStatus(currentNodeInfo.getNodeId(), connectionInfo.getTargetNodeId(), TopologyStatusEnum.DISCONNECTED.getCode());
        // 标记连接的节点可访问到的节点状态未知
        topologyInfoRepository.setConnectStatus(connectionInfo.getTargetNodeId(), null, TopologyStatusEnum.UNKNOWN.getCode());
    }

    /**
     * 结束连接
     */
    public void onTerminateConnect(ConnectionInfo connectionInfo) {
        // 清理连接记录
        topologyInfoRepository.delTopologyInfo(connectionInfo.getTargetNodeId());
    }

    /**
     * 通知节点变动
     */
    public void notifyTopologyChange(TopologyInfoDTO topologyInfoDTO) {
        System.out.println("[ " + topologyInfoDTO.getNodeId() + "] 节点新增" + (Objects.equals(topologyInfoDTO.getRelation(), TopologyRelationEnum.SUP) ? "上级 [" : "下级 [" + topologyInfoDTO.getTargetNodeId() + "]"));
    }

    /**
     * 某某节点连入拓扑网络（建立连接 初始连接）
     */
    public void onNotifiedConnection() {
        // 获取通知消息给的拓扑信息
        List<TopologyInfoDTO> list = new ArrayList<>();
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        // 这里list里的所有nodeId都应该是消息发送方的nodeId
        topologyInfoDTO.setNodeId(null);
        // 给的是发送方的能访问到的所有上级/下级（两者其一）
        topologyInfoDTO.setRelation(null);

        // 持久化
    }

    /**
     * 某某节点连入拓扑网络（建立连接 重连）
     */
    public void onNotifiedReconnection() {

    }

    /**
     * 某某节点从拓扑网络临时断开（断开连接）
     */
    public void onNotifiedTemporaryDisconnection() {
        // 标记暂时断连
    }

    /**
     * 某某节点结束连接结束连接（结束连接）
     */
    public void onNotifiedTerminateConnection() {

    }

    /**
     * 获取当前节点的所有上级（交换数据）
     */
    public List<TopologyInfoDTO> getAllSups() {
        List<TopologyInfoDTO> allSups = topologyInfoRepository.getAllSubsOrSupsByNodeId(currentNodeInfo.getNodeId(), TopologyRelationEnum.SUP.getCode());

        // 将nodeId都改为当前nodeId
        for (TopologyInfoDTO sup : allSups) {
            if (!Objects.equals(currentNodeInfo.getNodeId(), sup.getNodeId())) {
                sup.setNodeId(currentNodeInfo.getNodeId());
                sup.setInterval(sup.getInterval() + 1);
            }
        }
        System.out.println("通知连接节点, [" + currentNodeInfo.getNodeId() + "] 节点有这些上级 " + allSups.stream().map(TopologyInfoDTO::getTargetNodeId).toList());
        return allSups;
    }

    /**
     * 获取当前节点的所有上级（交换数据）
     */
    public List<TopologyInfoDTO> getAllSubs() {
        List<TopologyInfoDTO> allSubs = topologyInfoRepository.getAllSubsOrSupsByNodeId(currentNodeInfo.getNodeId(), TopologyRelationEnum.SUB.getCode());

        // 将nodeId都改为当前nodeId
        for (TopologyInfoDTO sub : allSubs) {
            if (!Objects.equals(currentNodeInfo.getNodeId(), sub.getNodeId())) {
                sub.setNodeId(currentNodeInfo.getNodeId());
                sub.setInterval(sub.getInterval() + 1);
            }
        }
        System.out.println("通知连接节点, [" + currentNodeInfo.getNodeId() + "] 节点有这些下级 " + allSubs.stream().map(TopologyInfoDTO::getTargetNodeId).toList());
        return allSubs;
    }

    /**
     * 拓扑结构改变
     *
     * @return 返回需要去通知的上级节点
     */
    public List<String> getConnectedSups() {
        List<TopologyInfoDTO> sups = topologyInfoRepository.getSubOrSupNodes(currentNodeInfo.getNodeId(), TopologyRelationEnum.SUP.getCode());
        return sups.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
    }

    /**
     * 拓扑结构改变
     *
     * @return 返回需要去通知的下级节点
     */
    public List<String> getConnectedSubs() {
        List<TopologyInfoDTO> subs = topologyInfoRepository.getSubOrSupNodes(currentNodeInfo.getNodeId(), TopologyRelationEnum.SUB.getCode());
        return subs.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
    }

    /**
     * 拓扑结构改变
     * 广播
     */

    /**
     * 获取整个拓扑结构
     */
    public void getEntireTopology() {

    }

    /**
     * 节点下线
     */
    public void offline() {
        // 清空运行时连接信息
        topologyInfoRepository.clear();
    }

}
