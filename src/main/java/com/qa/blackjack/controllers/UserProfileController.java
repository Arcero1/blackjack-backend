package com.qa.blackjack.controllers;

import com.google.gson.JsonObject;
import com.qa.blackjack.entities.UserAccount;
import com.qa.blackjack.entities.UserProfile;
import com.qa.blackjack.repositories.UserAccountRepository;
import com.qa.blackjack.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * UserProfileController class requires the implementation of the following:
 * create a user profile for a user account [done: createUserProfile()]
 * delete a user profile [done: deleteUserAccount()]
 * get all the user profiles for a particular user account [done: getAllProfilesOfUser()]
 * update the bank of the user [delegated to: GameController]
 * get the leaderboard of ten highest rated players [done: getLeaderboard()]
 *
 * @author  Bartek Marcysiak
 * @version 0.1
 * @since   2019-09-08
 */
@CrossOrigin
@RestController
public class UserProfileController {
    private final String baseURL = "/api/profiles/";
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(baseURL + "create")
    public String createUserProfile(@RequestParam String name, @RequestParam String userName) { // functional
        userProfileRepository.save(new UserProfile(name, userAccountRepository.findByEmail(userName).get().getId()));
        return "done";
    }

    @GetMapping(baseURL + "credits")
    public String getProfileCredits(@RequestParam String name) { // functional
        return userProfileRepository.findByName(name).isPresent() ? String.valueOf(userProfileRepository.findByName(name).get().getCredits()) : "failure:[PROFILE NOT FOUND]";
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(baseURL + "validate")
    public String validateProfileName(@RequestParam String name) {
        return userProfileRepository.findByName(name).isPresent() ? "success" : "failure:[PROFILE NOT FOUND]";
    }

    @GetMapping(baseURL + "leaderboard")
    public String getLeaderboard() { // functional
        JsonObject json = new JsonObject();

        AtomicInteger i = new AtomicInteger();
        userProfileRepository.findTop10ByOrderByCreditsDesc()
                .ifPresent(element -> element
                .forEach(el -> json.add("user" + i.getAndIncrement(), el.toJSON())));
        return json.toString();
    }

    @GetMapping(baseURL + "myProfiles")
    public String getAllProfilesOfUser(@RequestParam int uid) { // functional
        JsonObject json = new JsonObject();

        AtomicInteger i = new AtomicInteger();
        userProfileRepository.findAllByUid(uid)
                .ifPresent(element -> element
                        .forEach(el -> json.add(String.valueOf(i.getAndIncrement()), el.toJSON())));
        return json.toString();
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    void removeAllReferencesToUserAccount(UserAccount account) {

    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(baseURL + "delete")
    public String deleteUserProfile(@RequestParam String name) { // functional
        if(userProfileRepository.findByName(name).isPresent()) {
            userProfileRepository.delete(userProfileRepository.findByName(name).get());
            return "done";
        }
        return "no such profile";
    }
}
