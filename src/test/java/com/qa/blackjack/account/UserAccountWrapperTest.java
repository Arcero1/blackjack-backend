//package com.qa.blackjack.account;
//
//import com.qa.blackjack.packet.ApiSuccess;
//import com.qa.blackjack.util.ApiStatus;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserAccountWrapperTest {
//    @InjectMocks
//    private UserAccountWrapper wrapper;
//    @Mock
//    UserAccountRepository repository;
//
//    private String testEmail = "test.email@test.com";
//    private String testPassword = "test-pass";
//    private UserAccount testEntry = new UserAccount(testEmail, testPassword);
//
//
//    @Test
//    public void testCreateAccount() {
//        when(repository.findByEmail(note.getEmail())).thenReturn(Optional.of(note));
//        when(repository.save(note)).thenReturn(note);
//        assertTrue(wrapper.createEntry(testEmail, testPassword));
//        assertFalse(wrapper.createEntry(testEmail, testPassword));
//    }
//
//    @Test
//    public void testUserAccountPublicInfo() {
//        UserAccount note = new UserAccount(testEmail, testPassword);
//        note.setAlias("frank");
//        System.out.println(note.getEmail());
//        when(repository.findByEmail(note.getEmail())).thenReturn(Optional.of(note));
//        assertEquals(ApiStatus.SUCCESS, controller.getPublicAccountInfo(note.getEmail()).getStatus());
//    }
//
//    @Test
//    public void testValidateEmailReturnsTrueIfEmailExists() {
//        UserAccount accountWithPreExistingEmail = new UserAccount(testEmail, testPassword);
//        when(repository.findByEmail(accountWithPreExistingEmail.getEmail())).thenReturn(Optional.of(accountWithPreExistingEmail));
//        assertEquals(ApiStatus.SUCCESS, controller.validateEmail(accountWithPreExistingEmail.getEmail()).getStatus());
//    }
//
//    @Test
//    public void testValidateEmailReturnsFalseIfEmailNotExists() {
//        UserAccount accountWithNewEmail = new UserAccount(testEmail, testPassword);
//        //when(repository.findByEmail(accountWithNewEmail.getEmail())).thenReturn(Optional.empty());
//        assertEquals(ApiStatus.FAILURE, controller.validateEmail(accountWithNewEmail.getEmail()).getStatus());
//    }
//
//    @Test
//    public void testValidatePasswordReturnsTrueIfPasswordGivenIsSameAsStored() {
//        UserAccount accountWithCorrectPassword = new UserAccount(testEmail, testPassword);
//        UserAccount accountWithIncorrectPassword = new UserAccount(testEmail, "wrong-pass");
//        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(accountWithCorrectPassword));
//        assertEquals(ApiStatus.SUCCESS, controller.validateLogin(accountWithCorrectPassword).getStatus());
//        assertEquals(ApiStatus.FAILURE, controller.validateLogin(accountWithIncorrectPassword).getStatus());
//    }
//}