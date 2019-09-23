package com.qa.blackjack.profile;

import com.qa.blackjack.packet.ApiError;
import com.qa.blackjack.util.ApiErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProfileWrapper {
    private UserProfileRepository repository;

    public void save(UserProfile profile) {
        repository.save(profile);
    }

    public UserProfile getProfile(String name) throws Exception {
        return repository.findByName(name).orElseThrow(Exception::new);
    }

    @Autowired
    public final void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.repository = userProfileRepository;
    }

}
