package com.qa.blackjack.game;

import com.qa.blackjack.error.ApiError;
import com.qa.blackjack.error.ApiResponse;
import com.qa.blackjack.error.ApiSuccess;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.profile.UserProfileRepository;
import com.qa.blackjack.util.ApiErrorMessage;
import com.qa.blackjack.util.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class GameController {
    private Hand player = new Hand();
    private Hand dealer = new Hand();
    private int betAmount = 0;

    private Pack deck;
    private UserProfile profile;

    private UserAccountRepository userAccountRepository;
    private UserProfileRepository userProfileRepository;

    @GetMapping("/api/game/start")
    public ApiResponse start(@RequestParam String profileName) { // should only be called at the start of a session

        this.resetScores();

        if (userProfileRepository.findByName(profileName).isPresent()) {
            profile = userProfileRepository.findByName(profileName).get();
        } else {
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
        updateGamesPlayed();

        return new ApiSuccess(hasPlayerWon() ? "win" : "lose");
    }

    // CHECKS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/api/game/pollBust")
    public ApiResponse checkIfPlayerIsBust() {
        return new ApiSuccess(player.getScore() < 22 ? "safe" : "bust");
    }

    private boolean hasPlayerWon() {
        int playerTotal = player.getScore();
        int dealerTotal = dealer.getScore();

        boolean winCondition1 = playerTotal > dealerTotal && playerTotal < 22;
        boolean winCondition2 = playerTotal == 21 && dealerTotal == 21 && player.getNumCards() == 2 && dealer.getNumCards() != 2;
        boolean winCondition3 = playerTotal < 22 & dealerTotal > 21;
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

        userProfileRepository.save(profile);
    }

    private void updateGamesPlayed() {
        userAccountRepository.findById(profile.getOwnerId()).ifPresent(user -> {
            user.hasPlayed(hasPlayerWon());
            userAccountRepository.save(user);
        });
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Autowired
    public final void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
}
