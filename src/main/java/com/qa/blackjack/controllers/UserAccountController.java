package com.qa.blackjack.controllers;

import com.qa.blackjack.entities.UserAccount;
import com.qa.blackjack.repositories.UserAccountRepository;
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
        userAccountRepository.save(user);
        return "success";
    }

    // READ ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(URL + "info")
    public String getUserInfo(@RequestParam String email) {
        Optional<UserAccount> user = userAccountRepository.findByEmail(email);
        return user.isPresent() ? user.get().toPublicJSON().toString() : "failure:[USER NOT FOUND]";
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
    @GetMapping(URL + "setAlias")
    public String setAlias(@RequestParam int id, @RequestParam String alias) { // functional
        Optional<UserAccount> userOptional = userAccountRepository.findById(id);
        if (!userOptional.isPresent()) {
            return "failure:[USER NOT FOUND]";
        }
        UserAccount user = userOptional.get();
        user.setAlias(alias);
        userAccountRepository.save(user);
        return "success";
    }

    @GetMapping(URL + "changePassword")
    public String changePassword() {
        return "";
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
