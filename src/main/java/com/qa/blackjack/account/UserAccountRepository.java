package com.qa.blackjack.account;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);
}
