package com.qa.blackjack.profile;

import com.qa.blackjack.account.UserAccount;
import com.qa.blackjack.account.UserAccountRepositoryWrapper;
import com.qa.blackjack.packet.PI_UserProfileRequestCreate;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiResponsePacket;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.response.ApiErrorMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileControllerTest {
    @InjectMocks
    UserProfileController controller = new UserProfileController();
    @Mock
    UserProfileRepositoryWrapper profileWrapper;
    @Mock
    UserAccountRepositoryWrapper accountWrapper;
    private String testName = "name";
    private String testAccountName = "account-name";
    private UserAccount testAccount = new UserAccount(testAccountName, "test");

    private String testNameFail = "name-existing";
    private UserProfile testProfile = new UserProfile(testName, testAccount.getId());


    @Test
    public void testCreate() {
        when(profileWrapper.checkEntry(testName)).thenReturn(false);
        when(profileWrapper.checkEntry(testNameFail)).thenReturn(true);
        when(accountWrapper.getEntryOrRoot(testAccountName)).thenReturn(testAccount);

        assertEquals(ApiSuccess.class, controller.createProfile(new PI_UserProfileRequestCreate(testName, testAccountName)).getClass());
        assertEquals(ApiError.class, controller.createProfile(new PI_UserProfileRequestCreate(testNameFail, testAccountName)).getClass());
        assertEquals(ApiErrorMessage.PROFILE_EXISTS.toString(), controller.createProfile(new PI_UserProfileRequestCreate(testNameFail, testAccountName)).getMessage());
    }

    @Test
    public void testGetProfileCredits() throws Exception {
        when(profileWrapper.getCredits(testName)).thenReturn(String.valueOf(1000));

        assertEquals(ApiSuccess.class, controller.getProfileCredits(testName).getClass());
        assertEquals("1000", controller.getProfileCredits(testName).getMessage());


        when(profileWrapper.getCredits(testNameFail)).thenThrow(new Exception());

        assertEquals(ApiError.class, controller.getProfileCredits(testNameFail).getClass());
        assertEquals(ApiErrorMessage.NO_SUCH_PROFILE.toString(), controller.getProfileCredits(testNameFail).getMessage());
    }

    @Test
    public void testGetLeaderBoard() throws Exception {
//        when(profileWrapper.getTopTen()).thenThrow(new Exception());
//        assertEquals(ApiError.class, controller.getLeaderboard().getClass());
//        assertEquals(ApiErrorMessage.NO_AVAILABLE_PROFILES.toString(), controller.getLeaderboard().getMessage());

        when(profileWrapper.getTopTen()).thenReturn(Collections.singletonList(testProfile));
        when(accountWrapper.getEntryOrRoot(testProfile.getOwnerId())).thenReturn(testAccount);
        assertEquals(ApiResponsePacket.class, controller.getLeaderboard().getClass());
    }

    @Test
    public void testGetAllProfilesOfUser() throws Exception {
        when(profileWrapper.getAllProfilesOf(testAccount.getId())).thenReturn(new ArrayList<>());
        when(accountWrapper.getEntry(testAccountName)).thenReturn(testAccount);
        assertEquals(ApiError.class, controller.getAllProfilesOfUser(testAccount.getEmail()).getClass());
        assertEquals(ApiErrorMessage.NO_AVAILABLE_PROFILES.toString(), controller.getAllProfilesOfUser(testAccountName).getMessage());

        when(profileWrapper.getAllProfilesOf(testAccount.getId())).thenReturn(Collections.singletonList(testProfile));
        assertEquals(ApiResponsePacket.class, controller.getAllProfilesOfUser(testAccount.getEmail()).getClass());
    }

    @Test
    public void testDelete() {
        when(profileWrapper.deleteEntry(testName)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.deleteUserProfile(testName).getClass());
    }
}
