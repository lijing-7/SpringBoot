package com.example.springboot.demo;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {
    //不通过认证获取连接数据库对象
    public static MongoDatabase getConnect() {
//        return null;
        //连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient("192.168.0.214", 27017);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("datacenter");

        //返回连接数据库对象
        return mongoDatabase;

    }
}
