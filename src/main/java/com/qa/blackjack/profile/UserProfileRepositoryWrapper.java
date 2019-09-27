package com.qa.blackjack.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserProfileRepositoryWrapper {
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
    public UserProfile getProfile(String name) {
        return repository.findByName(name).orElse(new UserProfile());
    }

    @Transactional
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
