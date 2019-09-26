package com.qa.blackjack.profile;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
    Optional<List<UserProfile>> findTop10ByOrderByCreditsDesc();
    Optional<List<UserProfile>> findAllByUid(int uid);
    Optional<UserProfile> findByName(String name);
    void deleteByName(String name);
}
