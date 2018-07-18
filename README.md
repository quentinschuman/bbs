#beetl-bbs

在社区被黑掉好，决定用java做一个社区，技术使用springboot+beetl+beetsql

## 安装

* jdk8,maven3,mysql

* git clone 

* create database bbs; install mysql from intall-mysql.sql

* import as maven project

* run BbsMain

* access  http://127.0.0.1:8080/bbs/bbs/index/1.html,login as admin/123456 or register new user

* maven install  生成的war包部署到服务器上

* 必须安装elastic search 作为全文搜索：下载 https://www.elastic.co/downloads/past-releases/elasticsearch-5-4-3 (更高级的版本目前暂时未验证通过),进入bin目录，调用elasticsearch 启动

* 安装elastic search 分词：进入bin目录，运行 ./elasticsearch-plugin install  analysis-smartcn。然后重新启动elasticsearch






## 技术

Spring Boot2，Spring Cache，Elastic Search,Beetl,BeetlSQL,Java8

## 合作开发

开发之前必须先写个issue，然后指定给自己，也可以只提issue，然后各位自己领取


当前为2.0

![Alt text](show.png)
![Alt text](show2.png)

## 作者

闲大赋，Darren,马丁的早晨
