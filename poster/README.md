# 生成产品海报(利用canvas画布组成)
## 已配置pom.xml将资源文件、引用jar包隔离。
1. 生成的目录如下：
```
poster.jar
./config/
./lib/
./static/
./templates/
``` 
2. 启动时指定加载路径：
```
nohup java -jar -Dloader.path=.,./lib poster.jar > out.log 2> &