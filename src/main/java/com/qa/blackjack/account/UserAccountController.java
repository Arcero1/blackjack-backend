package com.qa.blackjack.account;

import com.qa.blackjack.packet.ApiError;
import com.qa.blackjack.packet.ApiResponse;
import com.qa.blackjack.packet.ApiResponsePacket;
import com.qa.blackjack.packet.ApiSuccess;
import com.qa.blackjack.util.ApiErrorMessage;
import org.springframework.web.bind.annotation.*;

/**
 * UserProfileController class requires the implementation of the following:
 * create a user account [done: createUserAccount()]
 * delete a user account [done: deleteUserAccount()]
 * update the user account with an alias [done: setAlias()]
 * update the games played/won after finishing a game [delegated to: GameController]
 * check if email is correct [in progress: validateEmail()]
 * check if email and password match [in progress: validatePassword()]
 *
 * @author Bartek Marcysiak
 * @version 0.1
 * @since 2019-09-08
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users/")
public class UserAccountController {
    //private UserAccountRepository userAccountRepository;
    private UserAccountWrapper wrapper = new UserAccountWrapper();

    @PostMapping("create")
    public ApiResponse createAccount(@RequestBody UserAccount user) {
        return wrapper.createEntry(user.getEmail(), user.getPassword()) ?
                new ApiSuccess() :
                new ApiError(ApiErrorMessage.USER_EXISTS
                );
    }

    @GetMapping("info")
    public ApiResponse getPublicAccountInfo(@RequestParam String email) {
        if (!(wrapper.entryExists(email))) return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        try {
            return new ApiResponsePacket(wrapper.getPublicInfo(email));
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @GetMapping("validate/email")
    public ApiResponse validateEmail(@RequestParam String email) { // functional
        return wrapper.entryExists(email) ? new ApiSuccess() : new ApiError(ApiErrorMessage.NO_SUCH_USER);
    }

    @PostMapping("validate/password")
    public ApiResponse validateLogin(@RequestBody UserAccount user) { // functional
        try {
            return wrapper.checkPassword(user.getEmail(), user.getPassword()) ?
                    new ApiSuccess() :
                    new ApiError(ApiErrorMessage.WRONG_PASSWORD);
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("setAlias")
    public ApiResponse setAccountAlias(@RequestBody UserAccount user) { // functional
        return wrapper.newAlias(user.getEmail(), user.getAlias()) ? new ApiSuccess() : new ApiError(ApiErrorMessage.NO_SUCH_USER);
    }

    @PostMapping("changePassword")
    public ApiResponse changeAccountPassword(@RequestBody UserAccountRequestPasswordChange request) {
        try {
            return wrapper.newPassword(request.getEmail(), request.getOldPassword(), request.getNewPassword()) ?
                    new ApiSuccess() :
                    new ApiError(ApiErrorMessage.WRONG_PASSWORD);
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("delete")
    public ApiResponse deleteAccount(@RequestBody UserAccount user) { // functional
        return wrapper.deleteEntry(user.getEmail(), user.getPassword()) ?
                new ApiSuccess() :
                new ApiError(ApiErrorMessage.WRONG_PASSWORD);
    }
}
