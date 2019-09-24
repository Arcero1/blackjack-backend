package com.qa.blackjack.account;

import com.qa.blackjack.exceptions.IncorrectEmailFormatException;
import com.qa.blackjack.exceptions.NoSuchAccountException;
import com.qa.blackjack.response.ApiError;
import com.qa.blackjack.response.ApiSuccess;
import com.qa.blackjack.response.ApiErrorMessage;
import com.qa.blackjack.response.ApiStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountControllerTest {

    @InjectMocks
    private UserAccountController controller = new UserAccountController();
    @Mock
    UserAccountRepositoryWrapper wrapper = new UserAccountRepositoryWrapper();

    private String testEmail = "test.email@test.com";
    private String testPassword = "test-pass";
    private UserAccount testUser = new UserAccount(testEmail, testPassword);

    @Test
    public void testCreateAccount() throws IncorrectEmailFormatException {
        // success on positive reply from wrapper
        when(wrapper.createEntry(testEmail, testPassword)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.createAccount(testUser).getClass());

        // failure on negative reply from wrapper
        when(wrapper.createEntry(testEmail, testPassword)).thenReturn(false);
        assertEquals(ApiError.class, controller.createAccount(testUser).getClass());
        assertEquals(ApiErrorMessage.USER_EXISTS.toString(), controller.createAccount(testUser).getMessage());
    }

    @Test
    public void testUserAccountPublicInfo() throws Exception {
        // success
        when(wrapper.getPublicInfo(testEmail)).thenReturn(
                new PO_UserAccountPublicInfo(
                        new UserAccount(testEmail, testPassword)
                )
        );

        assertEquals(ApiStatus.SUCCESS, controller.getPublicAccountInfo(testEmail).getStatus());
        assertEquals(PO_UserAccountPublicInfo.class, controller.getPublicAccountInfo(testEmail).getMessage().getClass());
        // difficult to confirm whether the info is correct or not - should look into it, not a priority
    }

    @Test
    public void testValidateEmail() {
        when(wrapper.entryExists(testEmail)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.validateEmail(testEmail).getClass());


        when(wrapper.entryExists(testEmail)).thenReturn(false);
        assertEquals(ApiError.class, controller.validateEmail(testEmail).getClass());
        assertEquals(ApiErrorMessage.NO_SUCH_USER.toString(), controller.validateEmail(testEmail).getMessage());
    }

    @Test
    public void testValidateLogin() throws Exception {

        when(wrapper.checkPassword(testEmail, testPassword)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.validateLogin(testUser).getClass());


        when(wrapper.checkPassword(testEmail, testPassword)).thenReturn(false);
        assertEquals(ApiError.class, controller.validateLogin(testUser).getClass());
        assertEquals(ApiErrorMessage.WRONG_PASSWORD.toString(), controller.validateLogin(testUser).getMessage());

        when(wrapper.checkPassword(testEmail, testPassword)).thenThrow(new Exception());
        assertEquals(ApiError.class, controller.validateLogin(testUser).getClass());
        assertEquals(ApiErrorMessage.NO_SUCH_USER.toString(), controller.validateLogin(testUser).getMessage());
    }

    @Test
    public void testSetAlias() throws NoSuchAccountException {
        String alias = "alias";
        testUser.setAlias(alias);

        when(wrapper.newAlias(testEmail, alias)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.setAccountAlias(testUser).getClass());

//        when(wrapper.newAlias(testEmail, alias)).thenReturn(false);
//        assertEquals(ApiError.class, controller.setAccountAlias(testUser).getClass());
//        assertEquals(ApiErrorMessage.NO_SUCH_USER.toString(), controller.setAccountAlias(testUser).getMessage());
    }

    @Test
    public void testChangePassword() throws Exception {
        String newPassword = "new-password";

        when(wrapper.newPassword(testEmail, testPassword, newPassword)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.changeAccountPassword(
                new PI_UserAccountPasswordChange(testEmail, newPassword, testPassword)
        ).getClass());

        when(wrapper.newPassword(testEmail, testPassword, newPassword)).thenReturn(false);
        assertEquals(ApiError.class, controller.changeAccountPassword(
                new PI_UserAccountPasswordChange(testEmail, newPassword, testPassword)
        ).getClass());
        assertEquals(ApiErrorMessage.WRONG_PASSWORD.toString(), controller.changeAccountPassword(
                new PI_UserAccountPasswordChange(testEmail, newPassword, testPassword)
        ).getMessage());

        when(wrapper.newPassword(testEmail, testPassword, newPassword)).thenThrow(new Exception());
        assertEquals(ApiError.class, controller.changeAccountPassword(
                new PI_UserAccountPasswordChange(testEmail, newPassword, testPassword)
        ).getClass());
        assertEquals(ApiErrorMessage.NO_SUCH_USER.toString(), controller.changeAccountPassword(
                new PI_UserAccountPasswordChange(testEmail, newPassword, testPassword)
        ).getMessage());
    }

    @Test
    public void testDelete() {
        when(wrapper.deleteEntry(testEmail, testPassword)).thenReturn(true);
        assertEquals(ApiSuccess.class, controller.deleteAccount(testUser).getClass());

        when(wrapper.deleteEntry(testEmail, testPassword)).thenReturn(false);
        assertEquals(ApiError.class, controller.deleteAccount(testUser).getClass());
        assertEquals(ApiErrorMessage.WRONG_PASSWORD.toString(), controller.deleteAccount(testUser).getMessage());
    }
}