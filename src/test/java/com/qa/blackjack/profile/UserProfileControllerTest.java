package com.qa.blackjack.profile;

import com.qa.blackjack.account.UserAccountController;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.profile.UserProfileController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileControllerTest {
    @InjectMocks
    UserProfileController controller = new UserProfileController();
    @Mock
    UserProfileRepository repository;

    @Test
    public void testCreate() {

    }
}
