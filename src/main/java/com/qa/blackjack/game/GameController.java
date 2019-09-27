package com.qa.blackjack.game;

import com.qa.blackjack.account.UserAccountRepositoryWrapper;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiResponse;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.profile.UserProfileRepositoryWrapper;
import com.qa.blackjack.response.ApiErrorMessage;
import com.qa.blackjack.response.ApiStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class GameController {
    Hand player = new Hand();
    Hand dealer = new Hand();
    private int betAmount = 0;

    Pack deck;
    UserProfile profile;

    private UserProfileRepositoryWrapper profileWrapper = new UserProfileRepositoryWrapper();
    private UserAccountRepositoryWrapper accountWrapper = new UserAccountRepositoryWrapper();

    @GetMapping("/api/game/start")
    public ApiResponse start(@RequestParam String profileName) { // should only be called at the start of a session

        this.resetScores();

        try {
            profile = profileWrapper.getProfile(profileName);
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }

        deck = new Pack(4);
        deck.shuffle();
        return new ApiSuccess();
    }

    @GetMapping("/api/game/bet")
    public ApiResponse bet(@RequestParam int betAmount) {
        if (betAmount < 0 || betAmount > profile.getCredits()) return new ApiError(ApiErrorMessage.NOT_ENOUGH_CREDITS);
        this.betAmount = betAmount;
        this.resetScores();
        return new ApiSuccess();
    }

    @GetMapping("/api/game/hit")
    public ApiResponse hit() {
        try {
            Card nextCard = deck.getCard();
            player.addCard(nextCard);
            return new ApiSuccess(nextCard.toString());
        } catch (NullPointerException e) {
            return new ApiError(ApiErrorMessage.OUT_OF_CARDS);
        }
    }

    @GetMapping("/api/game/dealer/hit")
    public ApiResponse dealerHit() {
        try {
            Card nextCard = deck.getCard();
            dealer.addCard(nextCard);

            int dealerTotal = dealer.getScore();

            if (dealerTotal > 17) {
                return new ApiSuccess(ApiStatus.DEALER_DONE, nextCard.toString());
            }

            return new ApiSuccess(nextCard.toString());

        } catch (NullPointerException e) {
            return new ApiError(ApiErrorMessage.OUT_OF_CARDS);
        }
    }

    @GetMapping("/api/game/stand")
    public ApiResponse stand() {
        updateBank();
        boolean hasWon = hasPlayerWon();
        accountWrapper.hasPlayed(hasWon, profile.getOwnerId());
        return new ApiSuccess(hasWon ? "win" : "lose");
    }

    private boolean hasPlayerWon() {
        int playerTotal = player.getScore();
        int dealerTotal = dealer.getScore();

        boolean winCondition1 = playerTotal > dealerTotal
                && playerTotal < 22;
        boolean winCondition2 = playerTotal == 21
                && dealerTotal == 21
                && player.getNumCards() == 2
                && dealer.getNumCards() != 2;
        boolean winCondition3 = playerTotal < 22 && dealerTotal > 21;
        return winCondition1 || winCondition2 || winCondition3;
    }

    private void resetScores() {

        player = new Hand();
        dealer = new Hand();
    }

    private void updateBank() {

        if (hasPlayerWon()) {
            profile.addCredits(betAmount);
        } else {
            profile.addCredits(-betAmount);
        }

        profileWrapper.save(profile);
    }
}
