/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.footprintcat.frostiot.common.dto.master.ClientInfoDTO;
import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.common.internal.ICurrentNodeInfo;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopologyNode {

    private final ISystemConfigRepository systemConfigRepository;
    private final ITopologyInfoRepository topologyInfoRepository;
    private final ICurrentNodeInfo currentNodeInfo;

    /**
     * 节点唯一标识
     *
     * @deprecated
     */
    private String nodeId;

    /**
     * 节点类型
     *
     * @deprecated
     */
    private NodeTypeEnum nodeType;

    public TopologyNode(ISystemConfigRepository systemConfigRepository, ITopologyInfoRepository topologyInfoRepository, ICurrentNodeInfo currentNodeInfo) {
        this.systemConfigRepository = systemConfigRepository;
        this.topologyInfoRepository = topologyInfoRepository;
        this.currentNodeInfo = currentNodeInfo;
    }

    /**
     * 节点是否初始化
     */
    private boolean isInit() {
        return !Objects.isNull(this.nodeId) && !Objects.isNull(this.nodeType);
    }

    /**
     * 初始化节点
     */
    public void init(String nodeId, NodeTypeEnum nodeType) {
        if (isInit()) {
            return;
        }

        if (Objects.isNull(nodeId) || Objects.isNull(nodeType)) {
            throw new RuntimeException("nodeId 或 nodeType 为空");
        }

        // 配置节点
        this.nodeId = nodeId;
        try {
            this.nodeType = nodeType;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("无效的类型: " + nodeType);
        }
    }

    /**
     * 是否连接过
     */
    private boolean hasConnected(String targetNodeId) {
        TopologyInfoDTO topologyInfo = topologyInfoRepository.getByNodeIdAndTargetNodeId(nodeId, targetNodeId);
        return !Objects.isNull(topologyInfo);
    }

    /**
     * 主动连接回调（建立连接 初始连接）
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void connected(ClientInfoDTO connectInfo) {
        // 是否连接过
        if (hasConnected(connectInfo.getTargetNodeId())) {
            return;
        }

        // 保存连接信息
        // 根据连接成功的响应信息构建拓扑信息
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        topologyInfoDTO.setNodeId(nodeId);
        topologyInfoDTO.setTargetNodeId(connectInfo.getTargetNodeId());
        topologyInfoDTO.setInterval(1);
        topologyInfoDTO.setIsConnected(true);
        if (true) {
            // 正向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUP);
            System.out.println(nodeId + " 👉 " + connectInfo.getTargetNodeId());

            // 获取连接节点的上级
            System.out.println("获取连接节点 [" + connectInfo.getTargetNodeId() + "] 的所有上级节点 []");

            // 通知当前节点的下级节点
            List<TopologyInfoDTO> allSubNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUB.getCode());

            // 获取当前节点的下级节点（相邻）
            List<TopologyInfoDTO> connectedSubNodes = allSubNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（上级）自己的所有下级
            System.out.println("通知节点 " + connectInfo.getTargetNodeId() + ", [" + nodeId + "] 节点有这些下级 " + allSubNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点的ID
            System.out.println("通知节点 " + connectedSubNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetNodeId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);
            System.out.println(connectInfo.getTargetNodeId() + " 👉 " + nodeId);

            // 获取连接节点的下级
            System.out.println("获取连接节点 [" + connectInfo.getTargetNodeId() + "] 的所有下级节点 []");

            // 通知当前节点的上级节点
            List<TopologyInfoDTO> allSupNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUP.getCode());

            // 获取当前节点的上级节点（相邻）
            List<TopologyInfoDTO> connectedSupNodes = allSupNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（下级）自己的所有上级
            System.out.println("通知节点 " + connectInfo.getTargetNodeId() + ", [" + nodeId + "] 节点有这些上级 " + allSupNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点ID
            System.out.println("通知节点 " + connectedSupNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetNodeId() + "]");

        }
        topologyInfoRepository.setTopologyInfo(topologyInfoDTO);
    }

    /**
     * 主动连接回调（建立连接 重连）
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void reconnected(ClientInfoDTO connectInfo) {
        if (!hasConnected(connectInfo.getTargetNodeId())) {
            throw new RuntimeException("没有 " + connectInfo.getTargetNodeId() + " 的连接信息，无法重连");
        }

        // 更改连接状态

        if (true) {
            // 正向连接
            // 通知当前节点的下级节点（相邻）
            System.out.println("节点 [" + nodeId + "] 重新连接到了上级节点 [" + connectInfo.getTargetNodeId() + "]");
        } else {
            // 反向连接
            // 通知当前节点的上级节点（相邻）
            System.out.println("节点 [" + nodeId + "] 重新连接到了下级节点 [" + connectInfo.getTargetNodeId() + "]");
        }
    }

    /**
     * 被动连接回调（建立连接 初始连接）
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void onConnected(ClientInfoDTO connectInfo) {
        // 是否连接过
        if (hasConnected(connectInfo.getTargetNodeId())) {
            return;
        }
        System.out.println("节点 [" + nodeId + "] 被动连接 [" + connectInfo.getTargetNodeId() + "]");

        // 保存连接信息
        // 根据连接成功的响应信息构建拓扑信息
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        topologyInfoDTO.setNodeId(nodeId);
        topologyInfoDTO.setTargetNodeId(connectInfo.getTargetNodeId());
        topologyInfoDTO.setInterval(1);
        topologyInfoDTO.setIsConnected(true);
        if (true) {
            // 正向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);
            System.out.println(connectInfo.getTargetNodeId() + " 👉 " + nodeId);

            // 获取连接节点的下级
            System.out.println("获取连接节点 [" + connectInfo.getTargetNodeId() + "] 的所有下级节点 []");

            // 通知当前节点的上级节点
            List<TopologyInfoDTO> allSupNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUP.getCode());

            // 获取当前节点的上级节点（相邻）
            List<TopologyInfoDTO> connectedSupNodes = allSupNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（下级）自己的所有上级
            System.out.println("通知节点 " + connectInfo.getTargetNodeId() + ", [" + nodeId + "] 节点有这些上级 " + allSupNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点ID
            System.out.println("通知节点 " + connectedSupNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetNodeId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUP);
            System.out.println(nodeId + " 👉 " + connectInfo.getTargetNodeId());

            // 获取连接节点的上级
            System.out.println("获取连接节点 [" + connectInfo.getTargetNodeId() + "] 的所有上级节点 []");

            // 通知当前节点的下级节点
            List<TopologyInfoDTO> allSubNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUB.getCode());

            // 获取当前节点的下级节点（相邻）
            List<TopologyInfoDTO> connectedSubNodes = allSubNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（上级）自己的所有下级
            System.out.println("通知节点 " + connectInfo.getTargetNodeId() + ", [" + nodeId + "] 节点有这些下级 " + allSubNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点的ID
            System.out.println("通知节点 " + connectedSubNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetNodeId() + "]");

        }
        topologyInfoRepository.setTopologyInfo(topologyInfoDTO);
    }

    /**
     * 被动连接回调（建立连接 重连）
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void onReconnected(ClientInfoDTO connectInfo) {
        if (!hasConnected(connectInfo.getTargetNodeId())) {
            throw new RuntimeException("没有 " + connectInfo.getTargetNodeId() + " 的连接信息，无法重连");
        }

        // 更改连接状态

        if (true) {
            // 正向连接
            // 通知当前节点的上级节点（相邻）
            System.out.println("节点 [" + nodeId + "] 重新连接到了下级节点 [" + connectInfo.getTargetNodeId() + "]");
        } else {
            // 反向连接
            // 通知当前节点的下级节点（相邻）
            System.out.println("节点 [" + nodeId + "] 重新连接到了上级节点 [" + connectInfo.getTargetNodeId() + "]");
        }
    }

    /**
     * 相邻节点暂时断连（断开连接）
     *
     * @param connectInfo 断连目标节点
     */
    public void onDisconnected(ClientInfoDTO connectInfo) {
        // 标记暂时断连

        // 通知相邻节点断连
        if (true) {
            // 如果targetNodeId是nodeId的下级
            // 通知nodeId的相邻上级
            System.out.println("节点 [" + nodeId + "] 临时断开下级节点 [" + connectInfo.getTargetNodeId() + "]");
        } else {
            // 如果targetNodeId是nodeId的上级
            // 通知nodeId的相邻下级
            System.out.println("节点 [" + nodeId + "] 临时断开上级节点 [" + connectInfo.getTargetNodeId() + "]");
        }
    }

    /**
     * 通知节点变动
     */
    public void notifyTopologyChange(TopologyInfoDTO topologyInfoDTO) {
        System.out.println("[ " + topologyInfoDTO.getNodeId() + "] 节点新增" + (Objects.equals(topologyInfoDTO.getDirection(), TopologyInfoDTO.Direction.SUP) ? "上级 [" : "下级 [" + topologyInfoDTO.getTargetNodeId() + "]"));
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
        topologyInfoDTO.setDirection(null);

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
