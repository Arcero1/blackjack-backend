package com.qa.blackjack.controllers;

import com.qa.blackjack.entities.UserAccount;
import com.qa.blackjack.packets.PasswordChangeRequest;
import com.qa.blackjack.repositories.UserAccountRepository;
import com.qa.blackjack.packets.UserAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
public class UserAccountController {
    private final String URL = "/api/users/";
    private UserAccountRepository userAccountRepository;

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(URL + "create")
    public String createUserAccountPOST(@RequestBody UserAccount user) {
        if (validateEmail(user.getEmail()).equals(SUCCESS_GENERIC)) return "failure:[ACCOUNT EXISTS]";
        user.setAlias(user.getEmail().substring(0, user.getEmail().indexOf("@")));
        userAccountRepository.save(user);
        return "success";
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(URL + "info")
    public UserAccountResponse getUserInfo(@RequestParam String email) {
        return new UserAccountResponse(userAccountRepository.findByEmail(email).get());
    }

    @GetMapping(URL + "validate/email")
    public String validateEmail(@RequestParam String email) { // functional
        return userAccountRepository.findByEmail(email).isPresent() ? "success" : "failure:[USER NOT FOUND]";
    }

    @PostMapping(URL + "validate/password")
    public String validatePassword(@RequestBody UserAccount user) { // functional
        return userAccountRepository.findByEmail(user.getEmail())
                .map(userAccount -> userAccount.comparePassword(user.getPassword()) ? "success" : "failure:[WRONG PASSWORD]")
                .orElse("failure:[USER NOT FOUND]");
    }

    // UPDATE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(URL + "setAlias")
    public String setAlias(@RequestBody UserAccount user) { // functional
        Optional<UserAccount> userOptional = userAccountRepository.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            return "failure:[USER NOT FOUND]";
        }
        UserAccount userPersistent = userOptional.get();http://localhost:8080/api/profiles/create
        if (!userPersistent.comparePassword(user.getPassword())) {
            return "failure:[INCORRECT PASSWORD]";
        }

        userPersistent.setAlias(user.getAlias());
        userAccountRepository.save(userPersistent);
        return "success";
    }

    @PostMapping(URL + "changePassword")
    public String changePassword(@RequestBody PasswordChangeRequest request) {
        UserAccount user = userAccountRepository.findByEmail(request.getEmail()).get();
        if (user.comparePassword(request.getOldPassword())) {
            user.setPassword(request.getNewPassword());
            userAccountRepository.save(user);
            return "success";
        }
        return "failure:[INCORRECT PASSWORD]";
    }

    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(URL + "delete")
    public String delete(@RequestBody UserAccount user) { // functional
        if (!validatePassword(user).equals("success")) {
            return validatePassword(user);
        } else {
            Optional<UserAccount> usersOptional = userAccountRepository.findByEmail(user.getEmail());
            usersOptional.ifPresent(userAccount -> {
                //new UserProfileController().deleteAllReferences(userAccount);
                userAccountRepository.delete(userAccount);
            });
            return "success";
        }
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
