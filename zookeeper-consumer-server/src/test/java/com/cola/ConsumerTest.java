package com.cola;

import com.cola.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerTest {

    @Autowired
    UserService userService;

    @Test
    void contextLoads(){
        userService.buyTicket();
    }
}
