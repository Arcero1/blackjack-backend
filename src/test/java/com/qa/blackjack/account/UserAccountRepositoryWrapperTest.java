package com.qa.blackjack.account;

import com.qa.blackjack.exceptions.IncorrectEmailFormatException;
import com.qa.blackjack.exceptions.NoSuchAccountException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountRepositoryWrapperTest {
    @InjectMocks
    private UserAccountRepositoryWrapper wrapper;
    @Mock
    UserAccountRepository repository;

    private String testEmail = "test@email";
    private String testEmailException = "test-email-exception";

    private String testEmailFail = "test@email-fail";

    private String testPassword = "test-pass";
    private UserAccount testEntry = new UserAccount(testEmail, testPassword);
    private UserAccount testEntryFail = new UserAccount(testEmailFail, testPassword);


    @Test
    public void testCreateEntry() throws IncorrectEmailFormatException {
        when(repository.save(any())).thenReturn(testEntry);
        assertTrue(wrapper.createEntry(testEmail, testPassword));
    }

    @Test (expected = IncorrectEmailFormatException.class)
    public void testCreateEntryThrowsIncorrectEmailFormatException() throws IncorrectEmailFormatException {
        wrapper.createEntry(testEmailException, testPassword);
    }

    @Test
    public void testEntryExists() throws IncorrectEmailFormatException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(testEntry));
        when(repository.findByEmail(testEmailFail)).thenReturn(Optional.empty());

        assertTrue(wrapper.entryExists(testEmail));
        assertFalse(wrapper.entryExists(testEmailFail));
    }

    @Test
    public void testGetEntry() throws NoSuchAccountException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(testEntry));
        assertEquals(testEntry, wrapper.getEntry(testEmail));
    }

    @Test
    public void testGetEntryById() throws NoSuchAccountException {
        when(repository.findById(testEntry.getId())).thenReturn(Optional.of(testEntry));
        assertEquals(testEntry, wrapper.getEntry(testEntry.getId()));
    }

    @Test (expected = NoSuchAccountException.class)
    public void testGetEntryThrowsNoSuchAccountException() throws NoSuchAccountException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.empty());
        assertEquals(testEntry, wrapper.getEntry(testEmail));
    }

    @Test
    public void testGetEntryOrRoot() {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(testEntry));
        assertEquals(testEntry, wrapper.getEntryOrRoot(testEmail));
    }

    @Test
    public void testGetEntryOrRootById() {
        when(repository.findById(testEntry.getId())).thenReturn(Optional.of(testEntry));
        assertEquals(testEntry, wrapper.getEntryOrRoot(testEntry.getId()));
    }

    @Test
    public void testGetEntryOrRootWhenUserNotExists() {
        when(repository.findByEmail(testEmailFail)).thenReturn(Optional.empty());
        when(repository.findById(1)).thenReturn(Optional.of(testEntry));
        assertEquals(testEntry, wrapper.getEntryOrRoot(testEmailFail));
    }

    @Test
    public void testUserAccountPublicInfo() throws NoSuchAccountException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(testEntry));
        assertEquals("", wrapper.getPublicInfo(testEmail).getAlias());
        assertEquals(0, wrapper.getPublicInfo(testEmail).getGamesPlayed());
        assertEquals(0, wrapper.getPublicInfo(testEmail).getGamesWon());
    }

    @Test
    public void testNewAlias() throws NoSuchAccountException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.of(testEntry));
        wrapper.newAlias(testEmail, "new-alias");
    }

    @Test(expected = NoSuchAccountException.class)
    public void testNewAliasThrowsExceptionWhenUserNotExists() throws NoSuchAccountException {
        when(repository.findByEmail(testEmail)).thenReturn(Optional.empty());
        wrapper.newAlias(testEmail, "new-alias");
    }
}