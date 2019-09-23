package com.qa.blackjack.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class is an interface between UserAccountController and UserAccountRepository
 */
@Component
class UserAccountWrapper {
    private UserAccountRepository repository;

    boolean createEntry(String email, String password) {
        return createEntry(email, password, email.substring(0, email.indexOf("@")));
    }

    boolean createEntry(String email, String password, String alias) {
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

    public boolean newAlias(String email, String alias) { // functional
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

    public boolean newPassword(String email, String oldPassword, String newPassword) {
        UserAccount user;
        try {
            user = repository
                    .findByEmail(email)
                    .orElseThrow(Exception::new);

            if (checkPassword(email, oldPassword)) {
                return false;
            }

            user.setPassword(newPassword);
            repository.save(user);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkPassword(String email, String password) throws Exception {
        return repository.findByEmail(email)
                .map(userAccount -> userAccount.comparePassword(password))
                .orElseThrow(Exception::new);
    }

    public boolean deleteEntry(String email, String password) { // functional
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

    // SETTER BASED DEPENDENCY INJECTION FOR REPOSITORIES //////////////////////////////////////////////////////////////
    @Autowired
    public final void setRepository(UserAccountRepository repository) {
        this.repository = repository;
    }
}
