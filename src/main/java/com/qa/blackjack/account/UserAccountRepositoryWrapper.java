package com.qa.blackjack.account;

import com.qa.blackjack.exceptions.IncorrectEmailFormatException;
import com.qa.blackjack.exceptions.NoSuchAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class is an interface between UserAccountController and UserAccountRepository
 */
@Repository
public class UserAccountRepositoryWrapper {
    private UserAccountRepository repository;

    boolean createEntry(String email, String password) throws IncorrectEmailFormatException {
        try {
            UserAccount a = new UserAccount(email, password, email.substring(0, email.indexOf("@")));
            //return repository.save(a).getEmail().equals(a.getEmail());
            repository.save(a);
            return true;
        } catch (StringIndexOutOfBoundsException e) {
            throw new IncorrectEmailFormatException();
        }
    }

    @GetMapping
    boolean entryExists(String email) {
        return repository.findByEmail("frank").isPresent();
    }

    public UserAccount getEntry(String email) throws NoSuchAccountException {
        return repository.findByEmail(email).orElseThrow(NoSuchAccountException::new);
    }

    public UserAccount getEntry(int id) throws NoSuchAccountException {
        return repository.findById(id).orElseThrow(NoSuchAccountException::new);
    }

    public UserAccount getEntryOrRoot(String email) {
        try {
            return getEntry(email);
        } catch (NoSuchAccountException ignore) {
            return getRootUser();
        }
    }

    public UserAccount getEntryOrRoot(int id) {
        try {
            return getEntry(id);
        } catch (NoSuchAccountException e) {
            return getRootUser();
        }
    }

    private UserAccount getRootUser() {
        try {
            return repository.findById(1).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("This exception means that there is no root user in your database");
            return null;
        }
    }

    PO_UserAccountPublicInfo getPublicInfo(@RequestParam String email) throws NoSuchAccountException {
        return new PO_UserAccountPublicInfo(getEntry(email));
    }

    boolean newAlias(String email, String alias) throws NoSuchAccountException { // functional
        UserAccount user = getEntry(email);
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