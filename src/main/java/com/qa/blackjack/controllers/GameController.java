package com.qa.blackjack.controllers;

import com.qa.blackjack.entities.UserProfile;
import com.qa.blackjack.game.Card;
import com.qa.blackjack.game.Pack;
import com.qa.blackjack.repositories.UserAccountRepository;
import com.qa.blackjack.repositories.UserProfileRepository;
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
    public String start(@RequestParam String profileName) { // should only be called at the start of a session

        if (userProfileRepository.findByName(profileName).isPresent()) {
            profile = userProfileRepository.findByName(profileName).get();
        } else {
            return msgItemNotFound("PROFILE");
        }
        deck = new Pack(4);
        deck.shuffle();
        return SUCCESS_GENERIC;
    }

    @GetMapping("/api/game/bet")
    public String bet(@RequestParam int betAmount) {
        if (betAmount >= 0 && betAmount > profile.getCredits()) {
            return msgNotEnough("CREDITS");
        }
        this.betAmount = betAmount;
        this.resetScores();
        return SUCCESS_GENERIC;
    }

    @GetMapping("/api/game/hit")
    public String hit() {
        Card nextCard = deck.getCard();
        playerTotal += nextCard.getValue();
        return nextCard.toString();
    }

    @GetMapping("/api/game/dealer/hit")
    public String dealerHit() {
        try {
            Card nextCard = deck.getCard();
            dealerTotal += nextCard.getValue();
            return nextCard.toString();
        } catch (NullPointerException e) {
            return SUCCESS_GENERIC;
        }
    }

    @GetMapping("/api/game/stand")
    public String stand() {
        updateBank();
        updateGamesPlayed();

        return hasPlayerWon() ? "win" : "lose";
    }

    // CHECKS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/api/game/pollBust")
    public String checkIfPlayerIsBust() {
        return playerTotal < 22 ? "safe" : "bust";
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
