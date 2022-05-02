package com.thoughtmechanix.zipkinsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/*
 * 注-该模块启动存在问题，原因如下：
 * pringboot 在2.0后就不推荐创建springboot项目启动zipkin server了,推荐自己搭建zipkin server【可直接运行官方jar包】
 * 因为你会发现spring boot 2.0 之后，你在引入依赖时必须要指定zipkin-server的版本号，而自己指定版本号会出现非常多的问题
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }
}