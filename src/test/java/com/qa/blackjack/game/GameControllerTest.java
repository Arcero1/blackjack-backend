package com.qa.blackjack.game;

import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountController;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.packet.ApiSuccess;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.profile.UserProfileRepository;
import com.qa.blackjack.util.ApiErrorMessage;
import com.qa.blackjack.util.ApiStatus;
import org.junit.Before;
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
    @InjectMocks static GameController controller = new GameController();
    @Mock static UserAccountRepository UARepository;
    @Mock static UserProfileRepository UPRepository;
    @Mock
    UserAccountController c2;


    private static UserProfile existingProfile = new UserProfile("existing-profile");
    private static UserProfile notExistingProfile = new UserProfile("not-existing-profile");

    @Before
    @Test
    public void testStart() {
        when(UPRepository.findByName(existingProfile.getName())).thenReturn(Optional.of(existingProfile));
        when(UPRepository.findByName(notExistingProfile.getName())).thenReturn(Optional.empty());

        assertEquals(ApiStatus.SUCCESS, controller.start(existingProfile.getName()).getStatus());
        assertEquals(ApiStatus.FAILURE, controller.start(notExistingProfile.getName()).getStatus());
    }

    @Test
    public void testBetWithSufficientFunds() {
        assertEquals(ApiSuccess.class, controller.bet(100).getClass());
        assertEquals(100, controller.betAmount);
    }

    @Test
    public void testBetWithInsufficientFunds() {
        assertEquals(ApiStatus.FAILURE, controller.bet(1000).getStatus());
        assertEquals(ApiErrorMessage.NOT_ENOUGH_CREDITS.toString(), controller.bet(1000).getMessage());
    }

    @Test
    public void testHit() {
        int s = controller.player.getScore();
        int c = controller.player.getNumCards();
        controller.hit();
        assertTrue(s < controller.player.getScore());
        assertTrue(c < controller.player.getNumCards());
    }

    @Test
    public void testDealerHit() {
        int s = controller.dealer.getScore();
        int c = controller.dealer.getNumCards();
        controller.dealerHit();
        assertTrue(s < controller.dealer.getScore());
        assertTrue(c < controller.dealer.getNumCards());
    }

    @Test
    public void testStand() {
        when(UPRepository.save(existingProfile)).thenReturn(existingProfile);

    }
}
