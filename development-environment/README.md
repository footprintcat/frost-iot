# 开发环境配置说明

## 启动运行环境

1. 安装 Docker Desktop
2. 如果在中国大陆，记得配置 docker 镜像源
3. **打开 Docker Desktop**
4. 运行命令

```sh
docker-compose up -d
```

然后打开 Docker Desktop 可以看到环境已经在运行中了

## 停止运行环境（保留数据库数据）

```sh
docker-compose down
```

## 停止并删除运行环境

```sh
# -v: 同时删除 Volume 数据卷
docker-compose down -v
```
