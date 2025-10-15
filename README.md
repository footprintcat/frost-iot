> [!CAUTION]
> 🚧 寒霜物联项目目前尚处于开发阶段，暂未完成开发，请过段时间再来看吧

<div align="center">

<p>
    <img src="./docs/assets/logo/frostiot.svg" width="180" height="180" alt="Frost IoT Logo" />
</p>

# 寒霜物联 Frost IoT

支持轻量化快速接入的 IoT 设备统一接入平台

<!-- https://shields.io/badges/static-badge -->
[![The 3-Clause BSD License](https://img.shields.io/badge/License-BSD--3--Clause_License-cyan?logo=bsd)](https://opensource.org/license/BSD-3-Clause) ![GitHub Release](https://img.shields.io/github/v/release/footprintcat/frost-iot)

[![GitHub commit activity](https://img.shields.io/github/commit-activity/t/footprintcat/frost-iot)](https://github.com/footprintcat/frost-iot/commits/) ![GitHub last commit](https://img.shields.io/github/last-commit/footprintcat/frost-iot)
</div>

## 项目简介

不做花里胡哨的 UI 界面，专注设备接入底层逻辑，为企业数字化转型提供底层支持。

### 项目说明

项目语言为 Java，使用 Gradle 构建，最低支持 JDK 版本为 17，推荐使用 JDK 版本为: OpenJDK 17, 21。

core 模块同时提供 Spring Boot 和 Micronaut 框架版本，两版本功能完全相同（仅框架相关代码不同）：

| 分支        | core 模块        | 适用场景                                                     | 框架生态         | 系统资源占用 | 开发体验         |
| ----------- | ---------------- | ------------------------------------------------------------ | ---------------- | ------------ | ---------------- |
| `main`      | Spring Boot 框架 | 系统性能足够期望获得更加使用体验，或有开发能力进行扩展功能或二次开发 | 强大，兼容性更好 | 较高         | 好               |
| `micronaut` | Micronaut 框架   | 性能更好，更适合端侧部署硬件资源有限场景                     | 较新，兼容性稍差 | 更小         | 类似 Spring Boot |

### 目录说明

运行 `gradlew projects` 查看项目结构

<!--
```
<root>
  |- common: Common 包
  |- design: 设计素材
```
-->

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=footprintcat/frost-iot&type=Date)](https://www.star-history.com/#footprintcat/frost-iot&Date)

## 📫 免责条款

本开源项目仅供合法合规用途使用。使用者应承诺遵守《中华人民共和国网络安全法》、《数据安全法》、《个人信息保护法》等相关法律法规。<b>严禁使用本项目从事任何违反中国法律或危害国家安全的行为。项目开发者不对任何因使用本项目而产生的法律责任或后果负责。使用者须对自身行为承担全部责任。</b>

### 许可证

[BSD-3-Clause license](LICENSE)

![](./docs/diagram/许可证说明.embed.svg)

### 镜像同步仓库

用户可以在访问 GitHub 遇到困难时，使用我们的 Gitee 官方仓库：[gitee.com/footprintcat/frost-iot](https://gitee.com/footprintcat/frost-iot)

**另外也麻烦在 Gitee 上 Star 了本项目的用户，尽量同步 Star 一下 [GitHub 仓库](https://github.com/footprintcat/frost-iot)，谢谢！**
