#!/bin/sh

jarname=poster.jar
file=poster.pid
if [  -f "$file" ]; then
        pid=$(cat $file)
        kill -9 $pid
        if [ $? -ne 0 ]; then
                echo "kill job[$pid] failed!"
                echo "please remove $file file and try again!"
                exit -1
        fi
fi
#nohup java -jar -Dloader.path=.,./lib wxmall-task-1.0.0.jar > out.log 2>&1 &
nohup java -jar -Dloader.path=lib/ $jarname > out.log 2>&1 &
pid=$!
echo "PID of this script: $pid"
echo $pid > $file