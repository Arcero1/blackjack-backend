package com.qa.blackjack.repositories;

import com.qa.blackjack.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);
}
