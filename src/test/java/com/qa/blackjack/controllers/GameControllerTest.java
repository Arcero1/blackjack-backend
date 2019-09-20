package com.qa.blackjack.controllers;

import com.qa.blackjack.GameController;
import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountController;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.profile.UserProfileController;
import com.qa.blackjack.profile.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {//    private GameController controller;
    @InjectMocks
    UserAccountController controller = new UserAccountController();
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    UserAccountRepository repository;

    @Test
    public void testGetAllNotes() throws Exception {
        UserAccount note = new UserAccount("frank@aplace", "pass");
        when(repository.save(note)).thenReturn(note);
        controller.createAccount(note);
    }
}
