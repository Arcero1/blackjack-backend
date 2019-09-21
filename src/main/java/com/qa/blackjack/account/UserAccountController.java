package com.qa.blackjack.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;
import java.util.Optional;

import static com.qa.blackjack.util.MessageUtil.SUCCESS_GENERIC;

/**
 * UserProfileController class requires the implementation of the following:
 * create a user account [done: createUserAccount()]
 * delete a user account [done: deleteUserAccount()]
 * update the user account with an alias [done: setAlias()]
 * update the games played/won after finishing a game [delegated to: GameController]
 * check if email is correct [in progress: validateEmail()]
 * check if email and password match [in progress: validatePassword()]
 *
 *
 * @author  Bartek Marcysiak
 * @version 0.1
 * @since   2019-09-08
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users/")
public class UserAccountController {
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("create")
    public String createAccount(@RequestBody UserAccount user) {
        user.setAlias(user.getEmail().substring(0, user.getEmail().indexOf("@")));
        userAccountRepository.save(user);
        return "success";
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("info")
    public UserAccountPublicInfo getPublicAccountInfo(@RequestParam String email) throws Exception {
        return new UserAccountPublicInfo(userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("DAMN")));
    }

    @GetMapping("validate/email")
    public String validateEmail(@RequestParam String email) { // functional
        return userAccountRepository.findByEmail(email).isPresent() ? "success" : "failure:[USER NOT FOUND]";
    }

    @PostMapping("validate/password")
    public String validatePassword(@RequestBody UserAccount user) { // functional
        return userAccountRepository.findByEmail(user.getEmail())
                .map(userAccount -> userAccount.comparePassword(user.getPassword()) ? "success" : "failure:[WRONG PASSWORD]")
                .orElse("failure:[USER NOT FOUND]");
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("setAlias")
    public String setAccountAlias(@RequestBody UserAccount user) { // functional
        Optional<UserAccount> userOptional = userAccountRepository.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            return "failure:[USER NOT FOUND]";
        }
        UserAccount userPersistent = userOptional.get();

        userPersistent.setAlias(user.getAlias());
        userAccountRepository.save(userPersistent);
        return "success";
    }

    @PostMapping("changePassword")
    public String changeAccountPassword(@RequestBody UserAccountRequestPasswordChange request) {
        UserAccount user = userAccountRepository.findByEmail(request.getEmail()).get();
        if (user.comparePassword(request.getOldPassword())) {
            user.setPassword(request.getNewPassword());
            userAccountRepository.save(user);
            return "success";
        }
        return "failure:[INCORRECT PASSWORD]";
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("delete")
    public String deleteAccount(@RequestBody UserAccount user) { // functional
        if (!validatePassword(user).equals("success")) {
            return validatePassword(user);
        } else {
            Optional<UserAccount> usersOptional = userAccountRepository.findByEmail(user.getEmail());
            usersOptional.ifPresent(userAccountRepository::delete);
            return "success";
        }
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
