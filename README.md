# kylin 
`我要发·518` 福彩`3D`预测服务作为一款开源的软件预测软件，用户可以免费使用，但作者不对使用者的任何行为负责。

软件基于SpringMVC开发，在使用过程中有任何问题，欢迎通过gitcoment沟通。


# 运行方式

命令行下切换到工程目录

## swarm集群方式
初始化swarm

```
sudo docker swarm init
```

运行服务，kylin可以更改为你喜欢的名字

```
sudo docker stack deploy -c docker-compose.yml kylin
```

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






