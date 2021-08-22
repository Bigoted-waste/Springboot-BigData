package com.cola.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service    //放到spring容器中
public class UserService {

    //想拿到provider-server的票,要去注册中心拿到服务
    @Reference //引用, pom坐标 ,可以定义路径相同的接口名
    TicketService ticketService;

    public void buyTicket(){
        String ticket = ticketService.getTicket();
        System.out.println("在注册中心拿到=>"+ticket);
    }
}
