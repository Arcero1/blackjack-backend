package com.qa.blackjack.controllers;

import com.qa.blackjack.profile.UserProfileController;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileControllerTest {

    @InjectMocks
    UserProfileController controller;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;


}
