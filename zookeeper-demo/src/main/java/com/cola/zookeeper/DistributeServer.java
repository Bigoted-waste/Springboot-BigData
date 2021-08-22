package com.cola.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {
    private static String zookeeperAddress="192.168.56.31:2181,192.168.56.32:2181,192.168.56.33:2181";
    private static int sessionTimeout=2000;
    private ZooKeeper zk=null;
    private String parentNode="/servers";

    //创建到zk的客户端连接
    public void getConnect() throws IOException {
         zk = new ZooKeeper(zookeeperAddress, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    //注册到服务器
    public void registServer(String hostname) throws Exception {
        String create=zk.create(parentNode+"/Server"
                ,hostname.getBytes()
                , ZooDefs.Ids.OPEN_ACL_UNSAFE
                , CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname+"is online "+ create);
    }

    //业务功能
    public void business(String hostname) throws InterruptedException {
        System.out.println(hostname+"is working ....");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 1 获取 zk 连接
        DistributeServer server=new DistributeServer();
        server.getConnect();

        // 2 利用 zk连接注册服务器信息
        server.registServer(args[0]);

        // 3 启动业务功能
        server.business(args[0]);
    }
}
