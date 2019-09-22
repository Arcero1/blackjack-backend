package com.qa.blackjack.game;

import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.game.GameController;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {//    private GameController controller;
    @InjectMocks
    GameController controller = new GameController();
    @Mock
    UserAccountRepository repository;
}
