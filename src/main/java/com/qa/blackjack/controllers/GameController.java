package com.qa.blackjack.controllers;

import com.qa.blackjack.entities.UserProfile;
import com.qa.blackjack.game.Card;
import com.qa.blackjack.game.Deck;
import com.qa.blackjack.repositories.UserAccountRepository;
import com.qa.blackjack.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class GameController {
    private int playerTotal = 0;
    private int dealerTotal = 0;
    private int betAmount = 0;

    private Deck deck;
    private UserProfile profile;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping("/api/game/start")
    public String start(@RequestParam String profileName) { // should only be called at the start of a session
        if(userProfileRepository.findByName(profileName).isPresent()) {
            profile = userProfileRepository.findByName(profileName).get();
        } else {
            return "failure:[No Such User]";
        }
        deck = new Deck();
        shuffleDeck();
        return "success";
    }

    private void shuffleDeck() {
        deck.shuffle();
    }

    @GetMapping("/api/game/bet")
    public String bet(@RequestParam int betAmount) {
        if (betAmount > profile.getCredits()) {
            return "not enough credits";
        }
        this.betAmount = betAmount;
        this.resetScores();
        return "bet successful";
    }

    @GetMapping("/api/game/pollBust")
    public String checkIfPlayerIsBust() {
        return playerTotal < 22 ? "safe" : "bust";
    }

    @GetMapping("/api/game/hit")
    public String hit() {
        Card nextCard = deck.getCard();
        playerTotal += nextCard.getValue();
        return nextCard.getId();
    }

    @GetMapping("/api/game/dealer/hit")
    public String dealerHit() {
        Card nextCard = deck.getCard();
        dealerTotal += nextCard.getValue();
        return nextCard.getId();
    }

    @GetMapping("/api/game/stand")
    public String stand() {
        updateBank();
        updateGamesPlayed();

        return winCondition() ? "win" : "lose" ;
    }

    private boolean winCondition() {
        return playerTotal > dealerTotal && playerTotal < 22;
    }

    private void resetScores() {
        playerTotal = 0;
        dealerTotal = 0;
    }

    private void updateBank() {

        if(winCondition()) {
            profile.addCredits(betAmount);
        } else {
            profile.addCredits(-betAmount);
        }

        userProfileRepository.save(profile);
    }

    private void updateGamesPlayed() {
        userAccountRepository.findById(profile.getOwnerId()).ifPresent(user -> {
            user.hasPlayed(winCondition());
            userAccountRepository.save(user);
        });
    }
}
