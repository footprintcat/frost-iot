# 寒霜物联 core 模块

## 构建项目

项目打包

```sh
./gradlew clean

# 只执行组装（打包）任务。适用于：在开发过程中快速验证打包配置
./gradlew assemble
# 执行完整的构建生命周期。适用于：正式构建
#./gradlew build

# 只构建 ...-all.jar
./gradlew shadowJar

# 停止守护进程，再重新构建 ...-all.jar
#./gradlew --stop # 注意不能通过 idea 旁边的绿色小三角运行，要复制到命令行运行
#./gradlew clean shadowJar
```

运行 jar 包

```sh
java -jar build/libs/frost-iot-core-0.0.1-SNAPSHOT-all.jar
```
或
```sh
java -jar build/libs/frost-iot-core-0.0.1-SNAPSHOT-all-optimized.jar
```

在 Micronaut 项目中，Gradle 构建会生成多种不同类型的 JAR 文件，每种都有特定的用途。以下是这些文件的区别说明：

### `build/libs` 主要 JAR 文件类型及区别

frost-iot-core-0.0.1-SNAPSHOT-all.jar
- 标准 Fat JAR：包含所有依赖项
- 使用标准 Java 模式运行（JVM 解释执行）
- 可以直接用 java -jar 运行
- 文件较大（45MB），因为包含所有依赖

frost-iot-core-0.0.1-SNAPSHOT-all-optimized.jar
- 优化版 Fat JAR：Micronaut 对依赖做了额外优化
- 可能包含类路径优化、反射配置预生成等
- 启动速度可能比标准版稍快
- 大小与标准版相近

frost-iot-core-0.0.1-SNAPSHOT.jar
- 普通 JAR：只包含你的项目代码
- 不包含任何依赖
- 文件很小（2MB）
- 需要手动管理依赖才能运行

frost-iot-core-0.0.1-SNAPSHOT-jit.jar
- JIT 模式 JAR：为即时编译优化的版本
- 适合传统 JVM 运行
- 比原生镜像版大，但比 Fat JAR 小

frost-iot-core-0.0.1-SNAPSHOT-native.jar
- 原生镜像 JAR：为 GraalVM Native Image 准备的版本
- 需要配合 GraalVM 使用
- 可以编译为原生可执行文件

frost-iot-core-0.0.1-SNAPSHOT-runner.jar
- 基础运行器：用于构建 Docker 镜像的基础
- 需要配合 Dockerfile 使用

frost-iot-core-0.0.1-SNAPSHOT-optimized-runner.jar
- 优化版运行器：对 Docker 运行做了额外优化
- 启动更快，占用资源更少

frost-iot-core-0.0.1-SNAPSHOT-sources.jar
- 源代码包：包含项目所有源代码
- 用于文档或调试目的

frost-iot-core-0.0.1-SNAPSHOT-javadoc.jar
- JavaDoc 文档包：包含生成的 API 文档
- 用于文档发布

### 如何选择使用哪个 JAR？

使用场景 推荐使用的 JAR

普通运行 -all.jar 或 -all-optimized.jar
容器化部署 -runner.jar 或 -optimized-runner.jar
GraalVM 原生编译 -native.jar
作为库被其他项目依赖 普通的 -0.0.1-SNAPSHOT.jar
发布文档 -javadoc.jar 和 -sources.jar

### 最佳实践

- 对于生产部署，使用 -all-optimized.jar 或 -optimized-runner.jar（它们大小更小）
- 对于开发，可以使用标准的 -all.jar
- 如果使用 GraalVM 原生镜像，使用 -native.jar

## Micronaut 4.8.2 Documentation

- [User Guide](https://docs.micronaut.io/4.8.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.8.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.8.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Shadow Gradle Plugin](https://gradleup.com/shadow/)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


