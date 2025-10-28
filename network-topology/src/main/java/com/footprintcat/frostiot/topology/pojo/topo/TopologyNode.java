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

    /**
     * 节点是否初始化
     */
    private boolean isInit;

    public TopologyNode(ISystemConfigRepository systemConfigRepository, ITopologyInfoRepository topologyInfoRepository) {
        this.systemConfigRepository = systemConfigRepository;
        this.topologyInfoRepository = topologyInfoRepository;
        this.isInit = false;
    }

    /**
     * 初始化节点
     */
    public void init(String nodeId, String nodeType) {
        if (isInit) {
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

        this.isInit = true;
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

            // 获取连接节点的上级
            List<TopologyInfoDTO> sups = new ArrayList<>();
            TopologyInfoDTO sup = new TopologyInfoDTO();
            sup.setNodeId(connectInfo.getTargetId());
            sups.add(sup);

            // 通知当前节点的下级节点
            // 获取当前节点的下级节点
            List<TopologyInfoDTO> subNodes = topologyInfoRepository.getSubOrSupNodes(nodeId, TopologyInfoDTO.Direction.SUB.toString().toLowerCase());

            // 返回需要通知的节点的ID
            List<String> nodeIds = subNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
            System.out.println("通知节点 " + nodeIds + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);

            // 获取连接节点的下级
            List<TopologyInfoDTO> subs = new ArrayList<>();
            TopologyInfoDTO sub = new TopologyInfoDTO();
            sub.setNodeId(connectInfo.getTargetId());
            subs.add(sub);

            // 通知当前节点的上级节点
            // 获取当前节点的上级节点
            List<TopologyInfoDTO> supNodes = topologyInfoRepository.getSubOrSupNodes(nodeId, TopologyInfoDTO.Direction.SUP.toString().toLowerCase());

            // 返回需要通知的节点ID
            List<String> nodeIds = supNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
            System.out.println("通知节点 " + nodeIds + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetId() + "]");
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

        // 保存连接信息
        // 根据连接成功的响应信息构建拓扑信息
        TopologyInfoDTO topologyInfoDTO = new TopologyInfoDTO();
        topologyInfoDTO.setNodeId(nodeId);
        topologyInfoDTO.setTargetNodeId(connectInfo.getTargetId());
        topologyInfoDTO.setInterval(1);
        if (true) {
            // 正向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUB);

            // 获取连接节点的下级
            List<TopologyInfoDTO> subs = new ArrayList<>();
            TopologyInfoDTO sub = new TopologyInfoDTO();
            sub.setNodeId(connectInfo.getTargetId());
            subs.add(sub);

            // 通知当前节点的上级节点
            // 获取当前节点的上级节点
            List<TopologyInfoDTO> supNodes = topologyInfoRepository.getSubOrSupNodes(nodeId, TopologyInfoDTO.Direction.SUP.toString().toLowerCase());

            // 返回需要通知的节点ID
            List<String> nodeIds = supNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
            System.out.println("通知节点 " + nodeIds + ", [" + nodeId + "] 节点连接到了新下级 [" + connectInfo.getTargetId() + "]");

        } else {
            // 反向连接
            topologyInfoDTO.setDirection(TopologyInfoDTO.Direction.SUP);

            // 获取连接节点的上级
            List<TopologyInfoDTO> sups = new ArrayList<>();
            TopologyInfoDTO sup = new TopologyInfoDTO();
            sup.setNodeId(connectInfo.getTargetId());
            sups.add(sup);

            // 通知当前节点的下级节点
            // 获取当前节点的下级节点
            List<TopologyInfoDTO> subNodes = topologyInfoRepository.getSubOrSupNodes(nodeId, TopologyInfoDTO.Direction.SUB.toString().toLowerCase());

            // 返回需要通知的节点的ID
            List<String> nodeIds = subNodes.stream().map(TopologyInfoDTO::getTargetNodeId).toList();
            System.out.println("通知节点 " + nodeIds + ", [" + nodeId + "] 节点连接到了新上级 [" + connectInfo.getTargetId() + "]");

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
