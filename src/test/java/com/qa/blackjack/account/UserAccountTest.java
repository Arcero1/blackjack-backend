package com.qa.blackjack.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountTest {
    @InjectMocks UserAccountController controller = new UserAccountController();
    @Mock UserAccountRepository repository;

    private String testEmail = "test.email@test.com";
    private String testPassword = "test-pass";


    @Test
    public void testCreateAccount() throws Exception {
        UserAccount note = new UserAccount(testEmail, testPassword);
        System.out.println(note.getEmail());
        when(repository.save(note)).thenReturn(note);
        controller.createAccount(note);
    }

    @Test
    public void testUserAccountPublicInfo() throws Exception {
        UserAccount note = new UserAccount(testEmail, testPassword);
        note.setAlias("frank");
        System.out.println(note.getEmail());
        when(repository.findByEmail(note.getEmail())).thenReturn(Optional.of(note));
        assertEquals("frank", controller.getPublicAccountInfo(note.getEmail()).getAlias());
    }

    @Test
    public void testValidateEmailReturnsTrueIfEmailExists() throws Exception {
        UserAccount accountWithPreExistingEmail = new UserAccount(testEmail, testPassword);
        when(repository.findByEmail(accountWithPreExistingEmail.getEmail())).thenReturn(Optional.of(accountWithPreExistingEmail));
        assertEquals("success", controller.validateEmail(accountWithPreExistingEmail.getEmail()));
    }

    @Test
    public void testValidateEmailReturnsFalseIfEmailNotExists() throws Exception {
        UserAccount accountWithNewEmail = new UserAccount(testEmail, testPassword);
        when(repository.findByEmail(accountWithNewEmail.getEmail())).thenReturn(Optional.empty());
        assertNotEquals("success", controller.validateEmail(accountWithNewEmail.getEmail()));
    }

    @Test
    public void testValidatePasswordReturnsTrueIfPasswordGivenIsSameAsStored() throws Exception {
        UserAccount accountWithCorrectPassword = new UserAccount(testEmail, testPassword);
        UserAccount accountWithIncorrectPassword = new UserAccount(testEmail, "wrong-pass");
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(accountWithCorrectPassword));
        assertEquals("success", controller.validatePassword(accountWithCorrectPassword));
        assertNotEquals("success", controller.validatePassword(accountWithIncorrectPassword));
    }
}
