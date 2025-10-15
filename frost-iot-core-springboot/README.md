# 寒霜物联 core 模块

## 构建项目

项目打包

```sh
cd frost-iot-core-springboot

../gradlew clean -x test
# windows
..\gradlew.bat clean -x test

# 打 jar 包
../gradlew build -x test
# windows
..\gradlew.bat build -x test
# xxx.jar 包含项目自身的 .class 文件、项目资源文件、依赖库
# xxx-plain.jar 仅包含项目自身的 .class 文件、项目资源文件，不包含依赖库
# xxx-sources.jar 包含项目的所有 .java 文件，不包括资源文件
# xxx-javadoc.jar 项目的 Javadoc。不是可执行 JAR，它是文档资源包，可以作为压缩包解压查看,maven 发布时会自动关联

# 打原生包
# 需要先确保 JAVA_HOME 指向 GraalVM JDK
# 例如: JAVA_HOME=D:\Program\Development\Environment\Java\graalvm-community-openjdk-22.0.2+9.1
../gradlew nativeCompile -x test
# windows
..\gradlew.bat nativeCompile -x test
```

运行

```sh
cd frost-iot-core-springboot

# 运行 jar 包
java -jar build/libs/frost-iot-core-0.0.1-SNAPSHOT.jar

# 运行原生包
./build/native/nativeCompile/frost-iot-core.exe
```
