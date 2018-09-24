# kylin 
`我要发·518` 福彩`3D`预测服务作为一款开源的软件预测软件，用户可以免费使用，但作者不对使用者的任何行为负责。

软件基于SpringMVC开发，在使用过程中有任何问题，欢迎通过gitcoment沟通。


# 在Docker中运行

## 安装环境

如果还没有安装好`docker`环境，请前往[Docker](https://www.docker.com/) 安装。
安装 `docker-compose` :

```aidl
curl -L https://github.com/docker/compose/releases/download/1.19.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose
```

命令行下切换到工程目录, 构造`docker`镜像

```aidl
sudo docker build -t shallotsh/kylin .
```


## swarm集群方式
初始化`swarm`环境

```
sudo docker swarm init
```

运行服务，可以将服务名`kylin`修改为你喜欢的名字

```
sudo docker stack deploy -c docker-compose.yml kylin
```

如果命令执行成功，则可以通过浏览器访问：[http://localhost:9090](http://localhost:9090)，查询首页预测页面。

查看现在已经启动服务

```$xslt
sudo docker service ls
```

查看服务实例

```$xslt
sudo docker service ps kylin_web
```


退出应用程序

```$xslt
sudo docker stack rm kylin
```

退出swarm

```$xslt
sudo docker swarm leave --force
```






