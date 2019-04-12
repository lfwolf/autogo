# 生成产品海报(利用canvas画布组成)
## 已配置pom.xml将资源文件、引用jar包隔离。
0. 生成环境配置打包命令如下（默认是开放环境）

    `mvn clean package -DskipTests -Pproduct`

1. 生成的目录如下：
```
poster.jar
./config/
./lib/
./static/
./templates/
``` 
2. 启动时指定加载路径：

    `nohup java -jar -Dloader.path=.,./lib poster.jar > out.log 2>&1 &`
    
    完整bash命令如下：
    ``````
	#!/bin/sh
	file=poster.pid
	if [  -f "$file" ]; then
	        cat $file |  kill -9
	fi
	nohup java -jar -Dloader.path=.,./lib poster.jar > out.log 2>&1 &
	pid=$!
	echo "PID of this script: $pid"
	echo $pid > $file
	``````
    