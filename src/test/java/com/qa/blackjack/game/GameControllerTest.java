package com.qa.blackjack.game;

import com.qa.blackjack.account.UserAccountRepositoryWrapper;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.profile.UserProfileRepositoryWrapper;
import com.qa.blackjack.response.ApiErrorMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.InvalidNameException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {
    @InjectMocks
    GameController controller = new GameController();
    @Mock
    UserProfileRepositoryWrapper profileWrapper;
    @Mock
    UserAccountRepositoryWrapper accountWrapper;

    private String testName = "profile-name";
    private UserProfile testProfile = new UserProfile(testName);

    @Before
    @Test
    public void testStart() throws Exception {
        String testIncorrectName = "incorrect-name";

        when(profileWrapper.getProfile(testName)).thenReturn(testProfile);
        assertEquals(ApiSuccess.class, controller.start(testName).getClass());

//        when(profileWrapper.getProfile(testIncorrectName)).thenThrow(new Exception());
//        assertEquals(ApiError.class, controller.start(testIncorrectName).getClass());
//        assertEquals(ApiErrorMessage.NO_SUCH_USER.toString(), controller.start(testIncorrectName).getMessage());
    }

    @Test
    public void testBet() {
        testProfile.addCredits(1000 - controller.profile.getCredits());

        assertEquals(ApiSuccess.class, controller.bet(500).getClass());

        assertEquals(ApiSuccess.class, controller.bet(1000).getClass());

        assertEquals(ApiError.class, controller.bet(2000).getClass());
        assertEquals(ApiErrorMessage.NOT_ENOUGH_CREDITS.toString(), controller.bet(2000).getMessage());
    }

    @Test
    public void testHit() {
        int s = controller.player.getScore();
        int c = controller.player.getNumCards();

        assertEquals(ApiSuccess.class, controller.hit().getClass());
        assertTrue(s < controller.player.getScore());
        assertTrue(c < controller.player.getNumCards());


        controller.deck.cards.clear();
        assertEquals(ApiError.class, controller.hit().getClass());
        assertEquals(ApiErrorMessage.OUT_OF_CARDS.toString(), controller.hit().getMessage());
    }

    @Test
    public void testDealerHit() {
        int s = controller.dealer.getScore();
        int c = controller.dealer.getNumCards();
        controller.dealerHit();

        assertEquals(ApiSuccess.class, controller.dealerHit().getClass());
        assertTrue(s < controller.dealer.getScore());
        assertTrue(c < controller.dealer.getNumCards());


        controller.deck.cards.clear();
        assertEquals(ApiError.class, controller.dealerHit().getClass());
        assertEquals(ApiErrorMessage.OUT_OF_CARDS.toString(), controller.dealerHit().getMessage());
    }

    @Test
    public void testStand() throws InvalidNameException {
        Card card10 = new  Card("King", "Hearts");
        Card card5 = new  Card("5", "Hearts");
        Card card6 = new  Card("6", "Hearts");
        Card card11 = new Ace("Hearts");

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player has more than dealer
        controller.player.cards.add(card11);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player and dealer both have blackjack - but player's blackjack is stronger
        controller.player.cards.add(card11);
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card5);
        controller.dealer.cards.add(card6);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // dealer is bust
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player would normally be bust but isn't due to aces
        controller.player.cards.add(card11);
        controller.player.cards.add(card11);

        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // dealer has more than player
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card11);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player is bust
        controller.player.cards.add(card10);
        controller.player.cards.add(card10);
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());

        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player and dealer both have blackjack - but dealer's blackjack is stronger
        controller.player.cards.add(card11);
        controller.player.cards.add(card5);
        controller.player.cards.add(card5);

        controller.dealer.cards.add(card11);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());


        controller.player.cards.clear();
        controller.dealer.cards.clear();

        // player and dealer have the same totals
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());
    }

}
