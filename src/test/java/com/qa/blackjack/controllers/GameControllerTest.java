package com.qa.blackjack.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {//    private GameController controller;

    // JUNIT + SELENIUM + MOCKITO <- do all of em, please (fren)
    @InjectMocks
    GameController controller;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHitGivesACard() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/api/game/start?profileName=test",
                String.class
        )).contains("Name: test, Credits: 3000");
    }
}
