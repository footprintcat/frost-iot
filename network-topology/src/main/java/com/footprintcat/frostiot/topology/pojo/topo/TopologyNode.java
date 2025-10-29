/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.footprintcat.frostiot.common.dto.TopologyInfoDTO;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.footprintcat.frostiot.topology.pojo.message.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TopologyNode {

    private final ISystemConfigRepository systemConfigRepository;
    private final ITopologyInfoRepository topologyInfoRepository;

    /**
     * 节点唯一标识
     */
    private String nodeId;

    /**
     * 节点类型
     */
    private NodeType nodeType;

    public TopologyNode(ISystemConfigRepository systemConfigRepository, ITopologyInfoRepository topologyInfoRepository) {
        this.systemConfigRepository = systemConfigRepository;
        this.topologyInfoRepository = topologyInfoRepository;
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
    public void init(String nodeId, String nodeType) {
        if (isInit()) {
            return;
        }

        if (Objects.isNull(nodeId) || Objects.isNull(nodeType)) {
            throw new RuntimeException("nodeId 或 nodeType 为空");
        }

        // 配置节点
        this.nodeId = nodeId;
        try {
            this.nodeType = NodeType.valueOf(nodeType.toUpperCase());
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
     * 主动连接回调
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void connected(ConnectInfo connectInfo) {
        // 是否连接过
        if (hasConnected(connectInfo.getTargetId())) {
            return;
        }

        // 保存连接信息
        // 根据连接成功的响应信息构建拓扑信息
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        topologyInfoDTO.setNodeId(nodeId);
        topologyInfoDTO.setTargetNodeId(connectInfo.getTargetId());
        topologyInfoDTO.setInterval(1);
        if (true) {
            // 正向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUP);
            System.out.println(nodeId + " 👉 " + connectInfo.getTargetId());

            // 获取连接节点的上级
            System.out.println("获取连接节点 [" + connectInfo.getTargetId() + "] 的所有上级节点 []");

            // 通知当前节点的下级节点
            List<TopologyInfoDTO> allSubNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUB.toString().toLowerCase());

            // 获取当前节点的下级节点（相邻）
            List<TopologyInfoDTO> connectedSubNodes = allSubNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（上级）自己的所有下级
            System.out.println("通知节点 " + connectInfo.getTargetId() + ", [" + nodeId + "] 节点有这些下级 " + allSubNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点的ID
            System.out.println("通知节点 " + connectedSubNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);
            System.out.println(connectInfo.getTargetId() + " 👉 " + nodeId);

            // 获取连接节点的下级
            System.out.println("获取连接节点 [" + connectInfo.getTargetId() + "] 的所有下级节点 []");

            // 通知当前节点的上级节点
            List<TopologyInfoDTO> allSupNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUP.toString().toLowerCase());

            // 获取当前节点的上级节点（相邻）
            List<TopologyInfoDTO> connectedSupNodes = allSupNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（下级）自己的所有上级
            System.out.println("通知节点 " + connectInfo.getTargetId() + ", [" + nodeId + "] 节点有这些上级 " + allSupNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点ID
            System.out.println("通知节点 " + connectedSupNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetId() + "]");

        }
        topologyInfoRepository.setTopologyInfo(topologyInfoDTO);
    }

    /**
     * 被动连接回调
     *
     * @param connectInfo TODO 成功连接响应的节点信息
     */
    public void onConnected(ConnectInfo connectInfo) {
        // 是否连接过
        if (hasConnected(connectInfo.getTargetId())) {
            return;
        }
        System.out.println("节点 [" + nodeId + "] 被动连接 [" + connectInfo.getTargetId() + "]");

        // 保存连接信息
        // 根据连接成功的响应信息构建拓扑信息
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        topologyInfoDTO.setNodeId(nodeId);
        topologyInfoDTO.setTargetNodeId(connectInfo.getTargetId());
        topologyInfoDTO.setInterval(1);
        if (true) {
            // 正向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);
            System.out.println(connectInfo.getTargetId() + " 👉 " + nodeId);

            // 获取连接节点的下级
            System.out.println("获取连接节点 [" + connectInfo.getTargetId() + "] 的所有下级节点 []");

            // 通知当前节点的上级节点
            List<TopologyInfoDTO> allSupNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUP.toString().toLowerCase());

            // 获取当前节点的上级节点（相邻）
            List<TopologyInfoDTO> connectedSupNodes = allSupNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（下级）自己的所有上级
            System.out.println("通知节点 " + connectInfo.getTargetId() + ", [" + nodeId + "] 节点有这些上级 " + allSupNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点ID
            System.out.println("通知节点 " + connectedSupNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUP);
            System.out.println(nodeId + " 👉 " + connectInfo.getTargetId());

            // 获取连接节点的上级
            System.out.println("获取连接节点 [" + connectInfo.getTargetId() + "] 的所有上级节点 []");

            // 通知当前节点的下级节点
            List<TopologyInfoDTO> allSubNodes = topologyInfoRepository.getAllSubsOrSupsByNodeId(nodeId, TopologyInfoDTO.Direction.SUB.toString().toLowerCase());

            // 获取当前节点的下级节点（相邻）
            List<TopologyInfoDTO> connectedSubNodes = allSubNodes.stream().filter(topologyInfo -> nodeId.equals(topologyInfo.getNodeId())).toList();

            // 返回给连接节点（上级）自己的所有下级
            System.out.println("通知节点 " + connectInfo.getTargetId() + ", [" + nodeId + "] 节点有这些下级 " + allSubNodes.stream().map(TopologyInfoDTO::getNodeId).toList());

            // 返回需要通知的节点的ID
            System.out.println("通知节点 " + connectedSubNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList() + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetId() + "]");

        }
        topologyInfoRepository.setTopologyInfo(topologyInfoDTO);
    }

    /**
     * 断连回调
     *
     * @param connectInfo
     */
    public void onDisconnected(ConnectInfo connectInfo) {
        // 标记暂时断连

    }

    /**
     * 消息接收回调
     */
    public void onMessageReceived(Message message, String source) {
        // 继续上报消息

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

    }

}
