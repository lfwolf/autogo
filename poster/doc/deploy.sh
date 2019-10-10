#!/bin/sh


srcpath=$(dirname `pwd`)
taskpath=/root/poster
jarname=poster.jar

for arg in "$*"
do
	echo $arg
done 

if  [ x$1 != x ]
then
    echo #...有参数
else 
    echo #...没有参数
fi

cd $srcpath
mvn clean package
scp $srcpath/doc/run.sh root@wxapp02:$taskpath/run.sh
ssh root@wxapp02 "cd $taskpath; chmod +x run.sh"
scp $srcpath/target/$jarname  root@wxapp02:$taskpath/$jarname
ssh root@wxapp02 "cd $taskpath; sh run.sh"