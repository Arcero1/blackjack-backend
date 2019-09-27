package com.qa.blackjack.game;

import com.qa.blackjack.account.UserAccountRepositoryWrapper;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.profile.UserProfileRepositoryWrapper;
import com.qa.blackjack.response.ApiSuccess;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.InvalidNameException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerStandTest {
    @InjectMocks
    private static GameController controller = new GameController();
    @Mock
    UserProfile profile;
    @Mock
    UserProfileRepositoryWrapper profileWrapper;
    @Mock
    UserAccountRepositoryWrapper accountWrapper;

    private static Card card5;
    private static Card card6;
    private static Card card10;
    private static Card card11;

    @BeforeClass
    public static void setUp() throws InvalidNameException {
        card5 = new  Card("5", "Hearts");
        card6 = new  Card("6", "Hearts");
        card10 = new  Card("King", "Hearts");
        card11 = new Ace("Hearts");
    }


    @Before
    public void setUpTest() {
        controller.player.cards.clear();
        controller.dealer.cards.clear();
    }

    @Test
    public void testPlayerHasStrongerBlackjack() {
        controller.player.cards.add(card11);
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card5);
        controller.dealer.cards.add(card6);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());
    }

    @Test
    public void testDealerIsBust() {
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());
    }

    @Test
    public void testPlayerIsNotBustDueToAces() {
        controller.player.cards.add(card11);
        controller.player.cards.add(card11);

        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());
    }

    @Test
    public void testDealerHasMoreThanPlayer() {
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card11);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());
    }

    @Test
    public void testPlayerIsBust() {
        controller.player.cards.add(card10);
        controller.player.cards.add(card10);
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());
    }

    @Test
    public void testDealerHasStrongerBlackjack() {
        controller.player.cards.add(card11);
        controller.player.cards.add(card5);
        controller.player.cards.add(card5);

        controller.dealer.cards.add(card11);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());
    }

    @Test
    public void testPlayerHasLessThanDealer() {
        controller.player.cards.add(card11);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("win", controller.stand().getMessage());
    }

    @Test
    public void testPlayerAndDealerAreSame() {
        controller.player.cards.add(card10);
        controller.dealer.cards.add(card10);
        assertEquals(ApiSuccess.class, controller.stand().getClass());
        assertEquals("lose", controller.stand().getMessage());
    }
}
