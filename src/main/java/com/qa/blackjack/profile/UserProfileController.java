package com.qa.blackjack.profile;

import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountRepository;
import com.qa.blackjack.error.ApiError;
import com.qa.blackjack.error.ApiResponse;
import com.qa.blackjack.error.ApiResponsePacket;
import com.qa.blackjack.error.ApiSuccess;
import com.qa.blackjack.util.ApiErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * UserProfileController class requires the implementation of the following:
 * create a user profile for a user account [done: createUserProfile()]
 * delete a user profile [done: deleteUserAccount()]
 * get all the user profiles for a particular user account [done: getAllProfilesOfUser()]
 * update the bank of the user [delegated to: GameController]
 * get the leaderboard of ten highest rated players [done: getLeaderboard()]
 *
 * @author Bartek Marcysiak
 * @version 0.1
 * @since 2019-09-08
 */
@CrossOrigin
@RestController
@RequestMapping("/api/profiles/")
public class UserProfileController {
    private UserProfileRepository userProfileRepository;
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("create")
    public ApiResponse createProfile(@RequestBody UserProfileRequestCreate request) { // functional
        if (checkIfProfileExists(request.getName())) {
            return new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
        }

        try {
            userProfileRepository.save(
                    new UserProfile(request.getName(), userAccountRepository.findByEmail(request.getOwner())
                            .map(UserAccount::getId)
                            .orElseThrow(Exception::new)));

            return new ApiSuccess();

        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @GetMapping("credits")
    public ApiResponse getProfileCredits(@RequestParam String name) { // functional
        try {
            return new ApiSuccess(userProfileRepository.findByName(name)
                    .map(UserProfile::creditsToString)
                    .orElseThrow(Exception::new)
            );
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
        }
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("validate")
    public ApiResponse validateProfileName(@RequestParam String name) {
        return checkIfProfileExists(name) ? new ApiSuccess() : new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
    }

    private boolean checkIfProfileExists(String name) {
        return userProfileRepository.findByName(name).isPresent();
    }

    @GetMapping("leaderboard")
    public ApiResponse getLeaderboard() { // functional
        List<UserProfileLeaderBoard> leaders = new ArrayList<>();
        try {
            userProfileRepository.findTop10ByOrderByCreditsDesc()
                    .orElseThrow(Exception::new)
                    .forEach(profile -> leaders.add(generateLeaderBoardEntry(profile))
                    );
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_AVAILABLE_PROFILES);
        }
        return new ApiResponsePacket(leaders);
    }

    @GetMapping("myProfiles")
    public ApiResponse getAllProfilesOfUser(@RequestParam String email) { // functional
        try {
            return new ApiResponsePacket(userProfileRepository.findAllByUid(
                    userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("USER")).getId()
            )
                    .orElseThrow(Exception::new));
        } catch (Exception e) {
            return e.toString().equals("USER") ?
                    new ApiError(ApiErrorMessage.NO_SUCH_USER) :
                    new ApiError(ApiErrorMessage.NO_AVAILABLE_PROFILES);
        }
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("delete")
    public ApiResponse deleteUserProfile(@RequestParam String name) { // functional
        try {
            userProfileRepository.delete(userProfileRepository.findByName(name).orElseThrow(Exception::new));
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
        }
        return new ApiSuccess();
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
