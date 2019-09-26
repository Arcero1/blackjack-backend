package com.qa.blackjack.profile;

import com.qa.blackjack.account.UserAccountRepositoryWrapper;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiResponse;
import com.qa.blackjack.response.ApiResponsePacket;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.response.ApiErrorMessage;
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
    @Autowired
    private UserProfileRepositoryWrapper profileWrapper = new UserProfileRepositoryWrapper();
    @Autowired
    private UserAccountRepositoryWrapper accountWrapper = new UserAccountRepositoryWrapper();

    @PostMapping("create")
    public ApiResponse createProfile(@RequestBody UserProfileRequestCreate request) { // functional
        if (profileWrapper.checkEntry(request.getName())) {
            return new ApiError(ApiErrorMessage.PROFILE_EXISTS);
        }

        try {
            profileWrapper.save(
                    new UserProfile(request.getName(), accountWrapper.getEntryOrRoot(request.getOwner()).getId()));

            return new ApiSuccess();

        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @GetMapping("credits")
    public ApiResponse getProfileCredits(@RequestParam String name) { // functional
        try {
            return new ApiSuccess(profileWrapper.getCredits(name));
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
        }
    }

    @GetMapping("validate")
    public ApiResponse validateProfileName(@RequestParam String name) {
        return profileWrapper.checkEntry(name) ? new ApiSuccess() : new ApiError(ApiErrorMessage.NO_SUCH_PROFILE);
    }

    @GetMapping("leaderboard")
    public ApiResponse getLeaderboard() { // functional
        List<PO_UserProfileLeaderBoardEntry> leaders = new ArrayList<>();
        try {
            profileWrapper.getTopTen()
                    .forEach(profile -> leaders.add(new PO_UserProfileLeaderBoardEntry(
                            profile,
                            accountWrapper.getEntryOrRoot(profile.getOwnerId()).getAlias())
                    ));
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_AVAILABLE_PROFILES);
        }
        return new ApiResponsePacket(leaders);
    }

    @GetMapping("myProfiles")
    public ApiResponse getAllProfilesOfUser(@RequestParam String email) { // functional
        List<UserProfile> profiles;
        try {
            profiles = profileWrapper.getAllProfilesOf(
                    accountWrapper.getEntry(email).getId()
            );
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }

        return profiles.size() > 0 ?
                new ApiResponsePacket(profiles) :
                new ApiError(ApiErrorMessage.NO_AVAILABLE_PROFILES);
    }

    @GetMapping("delete")
    public ApiResponse deleteUserProfile(@RequestParam String name) { // functional
        return profileWrapper.deleteEntry(name) ? new ApiSuccess() : new ApiError(ApiErrorMessage.UNEXPECTED_ERROR);
    }
}
