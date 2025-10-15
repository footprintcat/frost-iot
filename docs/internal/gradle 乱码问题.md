输出乱码时，可以观察下 charset 是否正确。UTF-8：正确，GBK：乱码
gradle 乱码问题可参考：https://blog.csdn.net/qq_39553871/article/details/136201398
Windows 系统下可尝试添加环境变量：GRADLE_OPTS=-Dfile.encoding=utf-8，并彻底重启 idea
