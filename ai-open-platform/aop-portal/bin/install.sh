#!/bin/sh
set -e

user=face 
group=face
  
#create group if not exists  
egrep "^$group" /etc/group >& /dev/null  
if [ $? -ne 0 ]  
then  
    groupadd $group  
fi  
  
#create user if not exists  
egrep "^$user" /etc/passwd >& /dev/null  
if [ $? -ne 0 ]  
then
	touch /usr/sbin/nologin
    useradd -g $group -s /usr/sbin/nologin $user  
fi

chattr -i ../*.jar
chown -R $user:$group ..
chmod 500 ../*.jar
chmod 400 ../config/*
chattr +i ../*.jar

rm -rf /etc/init.d/aop-portal

pwd="$PWD"
home="${pwd%/bin}"

ln -bs $home/aop-portal-1.0.0-SNAPSHOT.jar /etc/init.d/aop-portal
chkconfig --add aop-portal
chkconfig aop-portal on

echo -e "\033[36m service aop-portal installed successfully \033[0m"
