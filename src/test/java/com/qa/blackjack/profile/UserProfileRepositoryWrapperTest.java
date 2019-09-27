package com.qa.blackjack.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileRepositoryWrapperTest {
    @InjectMocks
    private UserProfileRepositoryWrapper wrapper = new UserProfileRepositoryWrapper();
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
