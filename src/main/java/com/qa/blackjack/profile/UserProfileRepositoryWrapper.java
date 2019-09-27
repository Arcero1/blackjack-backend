package com.qa.blackjack.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserProfileRepositoryWrapper {
    private UserProfileRepository repository;

    public void save(UserProfile profile) {
        repository.save(profile);
    }

    public List<UserProfile> getTopTen() throws Exception {
        return repository.findTop10ByOrderByCreditsDesc().orElseThrow(Exception::new);
    }

    public String getCredits(String name) throws Exception {
        return repository.findByName(name)
                .map(UserProfile::creditsToString)
                .orElseThrow(Exception::new);
    }

    public UserProfile getProfile(String name) {
        return repository.findByName(name).orElse(new UserProfile());
    }

    public boolean checkEntry(String name) {
        return repository.findByName(name).isPresent();
    }

    public boolean deleteEntry(String name) {
        repository.deleteByName(name);
        return repository.findByName(name).isPresent();
    }

    public List<UserProfile> getAllProfilesOf(int uid) {
        return repository.findAllByUid(uid).orElse(new ArrayList<>());
    }

    @Autowired
    public final void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.repository = userProfileRepository;
    }

}
