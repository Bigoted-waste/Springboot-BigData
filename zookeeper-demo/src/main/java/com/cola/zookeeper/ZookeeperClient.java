package com.cola.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZookeeperClient {
    private static String zookeeperAddress="192.168.56.31:2181,192.168.56.32:2181,192.168.56.33:2181";
    private static int sessionTimeout=2000;
    private ZooKeeper zkClient=null;

    @Before
    public void init() throws Exception{
        zkClient = new ZooKeeper(zookeeperAddress, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //收到事件通知后的回调函数(用户的业务逻辑)
                System.out.println(event.getType() + "--" + event.getPath());

                try {
                    zkClient.getChildren("/",true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建子节点
     * @throws Exception
     */
    @Test
    public void create() throws Exception{

        String nodeCreated=zkClient.create(
                "/cola"     //要创建的节点路径;
                ,"jinlian".getBytes()   //节点数据
                , ZooDefs.Ids.OPEN_ACL_UNSAFE     //节点权限
                ,CreateMode.PERSISTENT);    //节点类型

    }

    /**
     * 获取子节点并监听节点变化
     * @throws Exception
     */
    @Test
    public void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断Znode是否存在
     * @throws Exception
     */
    @Test
    public void exist() throws Exception{
        Stat stat = zkClient.exists("/cola", false);
        System.out.println(stat==null?"not exist":"exist");
    }
}
