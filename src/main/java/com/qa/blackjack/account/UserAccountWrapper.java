package com.qa.blackjack.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is an interface between UserAccountController and UserAccountRepository
 */
@RestController
public
class UserAccountWrapper {
    private UserAccountRepository repository;

    boolean createEntry(String email, String password) {
        return createEntry(email, password, email.substring(0, email.indexOf("@")));
    }

    private boolean createEntry(String email, String password, String alias) {
        if (entryExists(email)) return false;
        repository.save(new UserAccount(email, password, alias));
        return true;
    }

    boolean entryExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    UserAccountPublicInfo getPublicInfo(@RequestParam String email) throws Exception {
        return new UserAccountPublicInfo(repository.findByEmail(email).orElseThrow(Exception::new));
    }

    boolean newAlias(String email, String alias) { // functional
        UserAccount user;
        try {
            user = repository.findByEmail(email).orElseThrow(Exception::new);
        } catch (Exception e) {
            return false;
        }

        user.setAlias(user.getAlias());
        repository.save(user);
        return true;
    }

    boolean newPassword(String email, String oldPassword, String newPassword) throws Exception {
        UserAccount user;
        user = repository
                .findByEmail(email)
                .orElseThrow(Exception::new);

        if (checkPassword(email, oldPassword)) {
            return false;
        }

        user.setPassword(newPassword);
        repository.save(user);
        return true;
    }

    boolean checkPassword(String email, String password) throws Exception {
        return repository.findByEmail(email)
                .map(userAccount -> userAccount.comparePassword(password))
                .orElseThrow(Exception::new);
    }

    boolean deleteEntry(String email, String password) { // functional
        try {
            if (!checkPassword(email, password)) {
                return false;
            }
            repository.findByEmail(email).ifPresent(repository::delete);

        } catch (Exception ignore) {
            // no such user => the result of operation was successful
        }

        return true;
    }

    public void hasPlayed(boolean hasWon, int id) {
        repository.findById(id).ifPresent(user -> {
            user.hasPlayed(hasWon);
            repository.save(user);
        });
    }

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setRepository(UserAccountRepository repository) {
        this.repository = repository;
    }
}
