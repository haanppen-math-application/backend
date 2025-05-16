package com.hpmath.app.consumer.board;

import static org.junit.jupiter.api.Assertions.*;

import com.hpmath.BoardConsumerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BoardConsumerApplication.class)
class BoardConsumerTest {
    @Autowired
    private AccountController accountController;
}