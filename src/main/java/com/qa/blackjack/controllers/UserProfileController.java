package com.qa.blackjack.controllers;

import com.qa.blackjack.packets.CreateProfileRequest;
import com.qa.blackjack.packets.LeaderBoardEntry;
import com.qa.blackjack.entities.UserAccount;
import com.qa.blackjack.entities.UserProfile;
import com.qa.blackjack.repositories.UserAccountRepository;
import com.qa.blackjack.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.qa.blackjack.util.MessageUtil.*;

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
    private UserProfileRepository userProfileRepository;
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(baseURL + "create")
    public String createUserProfile(@RequestBody CreateProfileRequest request) { // functional
        if(validateProfileName(request.getName()).equals(SUCCESS_GENERIC)) {
            return FAILURE_GENERIC + ":[PROFILE ALREADY EXISTS]";
        }

        // userID 1 is the id of user "root" -> foreign key for all unbound profiles
        userProfileRepository.save(new UserProfile(
                request.getName(),
                userAccountRepository.findByEmail(request.getOwner()).get().getId()
        ));
        return SUCCESS_GENERIC;
    }

    @GetMapping(baseURL + "credits")
    public String getProfileCredits(@RequestParam String name) { // functional
        Optional<UserProfile> profile = userProfileRepository.findByName(name);
        return profile.map(UserProfile::creditsToString).orElse(msgItemNotFound("PROFILE"));
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(baseURL + "validate")
    public String validateProfileName(@RequestParam String name) {
        return userProfileRepository.findByName(name).isPresent() ? SUCCESS_GENERIC : msgItemNotFound("PROFILE");
    }

    @GetMapping(baseURL + "leaderboard")
    public List<LeaderBoardEntry> getLeaderboard() { // functional
        List<LeaderBoardEntry> leaders = new ArrayList<>();
        userProfileRepository.findTop10ByOrderByCreditsDesc().get().forEach(profile -> {
            leaders.add(generateLeaderBoardEntry(profile));
                }
        );

        return leaders;
    }

    @GetMapping(baseURL + "myProfiles")
    public List<UserProfile> getAllProfilesOfUser(@RequestParam String email) { // functional
        return userProfileRepository.findAllByUid(userAccountRepository.findByEmail(email).get().getId()).get();
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void removeReference(UserProfile profile) {
        profile.setUid(1);
        userProfileRepository.save(profile);
    }

    void removeReference(String name) {
        Optional<UserProfile> profile = userProfileRepository.findByName(name);
        if(profile.isPresent()) {
            UserProfile p = profile.get();
            removeReference(p);
        }
    }

    void removeAllReferencesToUserAccount(UserAccount account) {
        Optional<List<UserProfile>> profiles = userProfileRepository.findAllByUid(account.getId());
        profiles.ifPresent(userProfiles -> userProfiles.forEach(this::removeReference));
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(baseURL + "delete")
    public String deleteUserProfile(@RequestParam String name) { // functional
        if(userProfileRepository.findByName(name).isPresent()) {
            userProfileRepository.delete(userProfileRepository.findByName(name).get());
            return SUCCESS_GENERIC;
        }
        return msgItemNotFound("USER");
    }

    private LeaderBoardEntry generateLeaderBoardEntry(UserProfile profile) {
        return new LeaderBoardEntry(
                profile,
                userAccountRepository.findById(profile.getOwnerId()).get().getAlias()
        );
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
    @Autowired
    public final void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
