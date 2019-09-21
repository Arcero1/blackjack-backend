package com.qa.blackjack;

import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountController;
import com.qa.blackjack.account.UserAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {//    private GameController controller;
    @InjectMocks
    GameController controller = new GameController();
    @Mock
    UserAccountRepository repository;
}
