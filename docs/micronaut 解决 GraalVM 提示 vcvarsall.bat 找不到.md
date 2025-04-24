## 解决 Windows 下 micronaut Graalvm 原生编译一直提示 vcvarsall.bat 找不到的问题

报错：`Error: Failed to find 'vcvarsall.bat' in a Visual Studio installation.`

原文链接：https://blog.csdn.net/weixin_43260887/article/details/137964533

亲测有用：

修改这个文件 `%JAVA_HOME%/bin/native-image.cmd`，在 `@echo off` 后面添加：

```bat
call "D:\Program\Development\IDE\Microsoft Visual Studio\2022\Enterprise\VC\Auxiliary\Build\vcvars64.bat" > nul
```
