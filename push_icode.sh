#! /bin/bash

#################################
##   @author caichuibin        ##
#################################

## windows常用追踪命令
## windows查看端口占用
## netstat -ano|findstr "8080"
## kill进程 byid
## taskkill /F /PID 123
## kill进程 byname
## taskkill /F /IM java.exe

git pull
echo " git pull "

msg=$1
pull_msg=$2
echo "git status"
git status
echo "git add ."
git add *.java

ICAFE_ID="可视化任务2期"
#CURRENT_TIME=`date +%Y-%m-%d %H:%M:%S`
CURRENT_TIME=`date`
echo "CURRENT_TIME=$CURRENT_TIME"
if [ "$msg" == "" ]; then
    msg="\"update fix, integration, $CURRENT_TIME\" $ICAFE_ID";
else
    msg="\"$msg, $CURRENT_TIME\" $ICAFE_ID";
fi
echo "git commit -m $msg"
git commit -m "$msg"

current_branch=`git branch | grep '*' | awk '{print $2}'`
echo "current_branch=$current_branch"

if [ "" == "$current_branch" ]; then
    echo "current_branch不能为空"
    exit 7
fi

echo "git push origin $current_branch:refs/for/$current_branch"
git push origin $current_branch:refs/for/$current_branch

if [ "pull" == "$pull_msg" ]; then
    echo "git pull "
    git pull
fi
