package com.qa.blackjack.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps UserProfileRepository
 * purpose: decreasing test effort
 *
 * @author BMarcysiak
 * @version 0.1
 */
@Repository
public class UserProfileWrapper {
    private UserProfileRepository repository;

    @Transactional
    public void save(UserProfile profile) {
        repository.save(profile);
    }

    @Transactional
    public List<UserProfile> getTopTen() throws Exception {
        return repository.findTop10ByOrderByCreditsDesc().orElseThrow(Exception::new);
    }

    @Transactional
    public String getCredits(String name) throws Exception {
        return repository.findByName(name)
                .map(UserProfile::creditsToString)
                .orElseThrow(Exception::new);
    }

    @Transactional
    public UserProfile getProfile(String name) throws Exception {
        return repository.findByName(name).orElseThrow(Exception::new);
    }

    public boolean checkEntry(String name) {
        return repository.findByName(name).isPresent();
    }

    @Transactional
    public boolean deleteEntry(String name) {
        repository.deleteByName(name);
        return repository.findByName(name).isPresent();
    }

    @Transactional
    public List<UserProfile> getAllProfilesOf(int uid) {
        return repository.findAllByUid(uid).orElse(new ArrayList<>());
    }

    @Autowired
    public final void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.repository = userProfileRepository;
    }

}
