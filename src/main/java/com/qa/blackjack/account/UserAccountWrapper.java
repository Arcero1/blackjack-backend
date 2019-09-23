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

    public UserAccount getEntryOrRoot(int id) {
        try {
            return repository.findById(id).orElse(repository.findById(1).orElseThrow(Exception::new));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("This exception means that there is no root user in your database");
            return null;
        }
    }

    public UserAccount getEntry(String email) throws Exception {
        return repository.findByEmail(email).orElseThrow(Exception::new);
    }

    public UserAccount getEntryOrRoot(String email) {
        try {
            return repository.findByEmail(email).orElse(repository.findById(1).orElseThrow(Exception::new));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("This exception means that there is no root user in your database");
            return null;
        }
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

        user.setAlias(alias);
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
            repository.deleteByEmail(email);

        } catch (Exception ignore) {
            // no such user => the result is correct anyway
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
