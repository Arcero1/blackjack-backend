package com.qa.blackjack;

import com.qa.blackjack.error.ApiError;
import com.qa.blackjack.error.ApiResponse;
import com.qa.blackjack.error.ApiSuccess;
import com.qa.blackjack.profile.UserProfile;
import com.qa.blackjack.game.Card;
import com.qa.blackjack.game.Pack;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.profile.UserProfileRepository;
import com.qa.blackjack.util.ApiErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.qa.blackjack.util.MessageUtil.*;

@CrossOrigin
@RestController
public class GameController {
    private int playerTotal = 0;
    private int dealerTotal = 0;
    private int betAmount = 0;

    private Pack deck;
    private UserProfile profile;

    private UserAccountRepository userAccountRepository;
    private UserProfileRepository userProfileRepository;

    @GetMapping("/api/game/start")
    public ApiResponse start(@RequestParam String profileName) { // should only be called at the start of a session

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
            playerTotal += nextCard.getValue();
            return new ApiSuccess(nextCard.toString());
        } catch (NullPointerException e) {
            return new ApiError(ApiErrorMessage.OUT_OF_CARDS);
        }
    }

    @GetMapping("/api/game/dealer/hit")
    public ApiResponse dealerHit() {
        try {
            Card nextCard = deck.getCard();
            dealerTotal += nextCard.getValue();
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
        return new ApiSuccess(playerTotal < 22 ? "safe" : "bust");
    }

    // UTILITY METHODS /////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean hasPlayerWon() {
        return playerTotal > dealerTotal && playerTotal < 22;
    }

    private void resetScores() {
        playerTotal = 0;
        dealerTotal = 0;
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
