package com.qa.blackjack.profile;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserProfileWrapperTest {
    @InjectMocks
    private UserProfileRepositoryWrapper wrapper;
    @Mock
    UserProfileRepository repository;

    private String testName = "name";
    private UserProfile testUser = new UserProfile(testName);

    @Test
    public void testGetTopTen() throws Exception {
        when(repository.findTop10ByOrderByCreditsDesc()).thenReturn(Optional.of(Collections.singletonList(testUser)));
        assertTrue(wrapper.getTopTen().contains(testUser));
    }

    @Test
    public void testGetCredits() throws Exception {
        when(repository.findByName(testName)).thenReturn(Optional.of(testUser));
        assertEquals("300", wrapper.getCredits(testName));
    }

    @Test
    public void testGetProfile() throws Exception {
        when(repository.findByName(testName)).thenReturn(Optional.of(testUser));
        assertEquals(testUser, wrapper.getProfile(testName));
    }

    @Test
    public void testCheckEntry() {
        when(repository.findByName(testName)).thenReturn(Optional.of(testUser));
        assertTrue(wrapper.checkEntry(testName));
    }

    @Test
    public void testGetAllProfilesOf() {
        when(repository.findAllByUid(1)).thenReturn(Optional.of(Collections.singletonList(testUser)));
        assertTrue(wrapper.getAllProfilesOf(1).contains(testUser));
    }
}
