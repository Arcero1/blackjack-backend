package com.qa.blackjack.profile;

import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountRepository;
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
@RequestMapping("/api/profiles/")
public class UserProfileController {
    private UserProfileRepository userProfileRepository;
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("create")
    public String createProfile(@RequestBody UserProfileRequestCreate request) { // functional
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

    @GetMapping("credits")
    public String getProfileCredits(@RequestParam String name) { // functional
        Optional<UserProfile> profile = userProfileRepository.findByName(name);
        return profile.map(UserProfile::creditsToString).orElse(msgItemNotFound("PROFILE"));
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("validate")
    public String validateProfileName(@RequestParam String name) {
        return userProfileRepository.findByName(name).isPresent() ? SUCCESS_GENERIC : msgItemNotFound("PROFILE");
    }

    @GetMapping("leaderboard")
    public List<UserProfileLeaderBoard> getLeaderboard() { // functional
        List<UserProfileLeaderBoard> leaders = new ArrayList<>();
        userProfileRepository.findTop10ByOrderByCreditsDesc().get().forEach(profile -> {
            leaders.add(generateLeaderBoardEntry(profile));
                }
        );

        return leaders;
    }

    @GetMapping("myProfiles")
    public List<UserProfile> getAllProfilesOfUser(@RequestParam String email) { // functional
        return userProfileRepository.findAllByUid(userAccountRepository.findByEmail(email).get().getId()).get();
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("delete")
    public String deleteUserProfile(@RequestParam String name) { // functional
        if(userProfileRepository.findByName(name).isPresent()) {
            userProfileRepository.delete(userProfileRepository.findByName(name).get());
            return SUCCESS_GENERIC;
        }
        return msgItemNotFound("USER");
    }

    private UserProfileLeaderBoard generateLeaderBoardEntry(UserProfile profile) {
        return new UserProfileLeaderBoard(
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
