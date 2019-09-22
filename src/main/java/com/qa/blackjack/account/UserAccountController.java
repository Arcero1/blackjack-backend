package com.qa.blackjack.account;

import com.qa.blackjack.error.ApiError;
import com.qa.blackjack.error.ApiResponse;
import com.qa.blackjack.error.ApiResponsePacket;
import com.qa.blackjack.error.ApiSuccess;
import com.qa.blackjack.util.ApiErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("create")
    public ApiResponse createAccount(@RequestBody UserAccount user) {
        user.setAlias(user.getEmail().substring(0, user.getEmail().indexOf("@")));
        userAccountRepository.save(user);
        return new ApiSuccess();
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("info")
    public ApiResponse getPublicAccountInfo(@RequestParam String email) {
        if (!checkIfEmailExists(email)) return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        try {
            return new ApiResponsePacket(
                    new UserAccountPublicInfo(userAccountRepository.findByEmail(email).orElseThrow(Exception::new))
            );
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    @GetMapping("validate/email")
    public ApiResponse validateEmail(@RequestParam String email) { // functional
        return checkIfEmailExists(email) ? new ApiSuccess() : new ApiError(ApiErrorMessage.NO_SUCH_USER);
    }

    private boolean checkIfEmailExists(String email) {
        return userAccountRepository.findByEmail(email).isPresent();
    }

    @PostMapping("validate/password")
    public ApiResponse validateLogin(@RequestBody UserAccount user) { // functional
        try {
            return checkIfPasswordIsCorrect(user.getEmail(), user.getPassword()) ?
                    new ApiSuccess() :
                    new ApiError(ApiErrorMessage.WRONG_PASSWORD);
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    private boolean checkIfPasswordIsCorrect(String email, String password) throws Exception {
        return userAccountRepository.findByEmail(email)
                .map(userAccount -> userAccount.comparePassword(password))
                .orElseThrow(Exception::new);
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("setAlias")
    public ApiResponse setAccountAlias(@RequestBody UserAccount user) { // functional
        Optional<UserAccount> userOptional = userAccountRepository.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
        UserAccount userPersistent = userOptional.get();

        userPersistent.setAlias(user.getAlias());
        userAccountRepository.save(userPersistent);
        return new ApiSuccess();
    }

    @PostMapping("changePassword")
    public ApiResponse changeAccountPassword(@RequestBody UserAccountRequestPasswordChange request) {
        try {
            UserAccount user = userAccountRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(Exception::new);
            if (checkIfPasswordIsCorrect(request.getEmail(), request.getOldPassword())) {
                user.setPassword(request.getNewPassword());
                userAccountRepository.save(user);
                return new ApiSuccess();
            }
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
        return new ApiError(ApiErrorMessage.WRONG_PASSWORD);
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("delete")
    public ApiResponse deleteAccount(@RequestBody UserAccount user) { // functional
        try {
            if (!checkIfPasswordIsCorrect(user.getEmail(), user.getPassword())) {
                return validateLogin(user);
            } else {
                Optional<UserAccount> usersOptional = userAccountRepository.findByEmail(user.getEmail());
                usersOptional.ifPresent(userAccountRepository::delete);
                return new ApiSuccess();
            }
        } catch (Exception e) {
            return new ApiError(ApiErrorMessage.NO_SUCH_USER);
        }
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
