package com.qa.blackjack.account;

import com.qa.blackjack.exceptions.IncorrectEmailFormatException;
import com.qa.blackjack.exceptions.NoSuchAccountException;
import com.qa.blackjack.packet.PI_UserAccountPasswordChange;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiResponse;
import com.qa.blackjack.response.ApiResponsePacket;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.response.ApiErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserAccountRepositoryWrapper wrapper = new UserAccountRepositoryWrapper();

    @PostMapping("create")
    public ApiResponse createAccount(@RequestBody UserAccount user) {
        try {
            return wrapper.createEntry(user.getEmail(), user.getPassword()) ?
                    new ApiSuccess() :
                    new ApiError(ApiErrorMessage.USER_EXISTS
                    );
        } catch (IncorrectEmailFormatException e) {
            return new ApiError(ApiErrorMessage.UNEXPECTED_ERROR);
        }
    }

    @GetMapping("info")
    public ApiResponse getPublicAccountInfo(@RequestParam String email) {
        try {
            return new ApiResponsePacket(wrapper.getPublicInfo(email));
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @GetMapping("validate/email")
    public ApiResponse validateEmail(@RequestParam String email) { // functional
        System.out.println("HI, email:" + email);
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

    @PostMapping("setAlias")
    public ApiResponse setAccountAlias(@RequestBody UserAccount user) { // functional
        try {
            wrapper.newAlias(user.getEmail(), user.getAlias());
            return new ApiSuccess();
        } catch (NoSuchAccountException e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @PostMapping("changePassword")
    public ApiResponse changeAccountPassword(@RequestBody PI_UserAccountPasswordChange request) {
        try {
            return wrapper.newPassword(request.getEmail(), request.getOldPassword(), request.getNewPassword()) ?
                    new ApiSuccess() :
                    new ApiError(ApiErrorMessage.WRONG_PASSWORD);
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @PostMapping("delete")
    public ApiResponse deleteAccount(@RequestBody UserAccount user) { // functional
        return wrapper.deleteEntry(user.getEmail(), user.getPassword()) ?
                new ApiSuccess() :
                new ApiError(ApiErrorMessage.WRONG_PASSWORD);
    }
}
