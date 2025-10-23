# 寒霜物联 core 模块

## 构建项目

本地临时设置 GraalVM 的环境变量 (默认 GraalVM 所在位置已经配置到 JAVA_HOME_GRAALVM 环境变量中)

```bash
cmd
echo %JAVA_HOME_GRAALVM%
set JAVA_HOME=%JAVA_HOME_GRAALVM%
set PATH=%JAVA_HOME%\bin;%PATH%
echo %JAVA_HOME%
echo %PATH%
java -version
```

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
../gradlew clean nativeCompile -x test
# Windows
..\gradlew.bat nativeCompile -x test
..\gradlew.bat clean nativeCompile -x test
```

运行

```sh
cd frost-iot-core-springboot

# 运行 jar 包
java -jar build/libs/frost-iot-core-0.0.1-SNAPSHOT.jar

# 运行原生包
./build/native/nativeCompile/frost-iot-core.exe
# Windows
.\build\native\nativeCompile\frost-iot-core.exe
```

## 参考文档

GraalVM native 打包相关
- https://docs.spring.io/spring-boot/reference/packaging/native-image/advanced-topics.html
- https://www.graalvm.org/latest/reference-manual/native-image/metadata/
- https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-GraalVM
