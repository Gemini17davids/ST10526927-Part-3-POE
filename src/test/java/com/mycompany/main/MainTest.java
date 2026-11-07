package com.mycompany.main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit tests for the QuickChat application (Parts 1, 2 and 3)
public class MainTest {

    // Sample data used by the Part 3 tests
    private static final String NUMBER_ONE = "+27831234567";
    private static final String NUMBER_TWO = "+27839876543";

    // Start every test with an empty message list and no leftover file
    @BeforeEach
    public void setUp() {
        Main.messages.clear();
        Main.populateArrays();
        new File(Main.JSON_FILE).delete();
    }

    // Remove the file the tests may have created
    @AfterEach
    public void tearDown() {
        new File(Main.JSON_FILE).delete();
    }

    // Adds three test messages: the second one is the longest
    private void addTestMessages() {
        Main.messages.add(new Main.Message("1111111111", 1, NUMBER_ONE, "Hi there", "11:1:HITHERE"));
        Main.messages.add(new Main.Message("2222222222", 2, NUMBER_TWO,
                "This is the longest message of them all", "22:2:THISALL"));
        Main.messages.add(new Main.Message("3333333333", 3, NUMBER_ONE, "See you soon", "33:3:SEESOON"));
    }

    // ---------- Parts 1 and 2 ----------

    @Test
    public void usernameWithFiveCharactersAndUnderscoreIsValid() {
        assertTrue(Main.checkUserName("ky_g1"));
    }

    @Test
    public void usernameWithoutUnderscoreIsInvalid() {
        assertFalse(Main.checkUserName("kyle1"));
    }

    @Test
    public void passwordMeetingAllRulesIsValid() {
        assertTrue(Main.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void passwordWithoutSpecialCharacterIsInvalid() {
        assertFalse(Main.checkPasswordComplexity("Password1"));
    }

    @Test
    public void cellPhoneWithCountryCodeIsValid() {
        assertTrue(Main.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void cellPhoneWithoutCountryCodeIsInvalid() {
        assertFalse(Main.checkCellPhoneNumber("0838968976"));
    }

    @Test
    public void messageIdHasTenDigits() {
        String id = Main.createMessageId();
        assertEquals(10, id.length());
        assertTrue(id.matches("[0-9]{10}"));
    }

    @Test
    public void messageHashUsesIdNumberFirstAndLastWord() {
        assertEquals("00:1:HITONIGHT", Main.createMessageHash("0012345678", 1, "Hi tonight"));
    }

    // ---------- Part 3 ----------

    @Test
    public void arraysArePopulatedWithTheStoredMessages() {
        addTestMessages();
        Main.populateArrays();

        assertEquals(3, Main.messageIdArray.length);
        assertEquals("2222222222", Main.messageIdArray[1]);
        assertEquals("33:3:SEESOON", Main.messageHashArray[2]);
        assertEquals(NUMBER_TWO, Main.recipientArray[1]);
        assertEquals("Hi there", Main.messageTextArray[0]);
    }

    @Test
    public void arraysAreEmptyWhenThereAreNoMessages() {
        Main.populateArrays();
        assertEquals(0, Main.messageTextArray.length);
    }

    @Test
    public void longestMessageIsFound() {
        addTestMessages();
        int index = Main.findLongestMessageIndex();

        assertEquals(1, index);
        assertEquals("This is the longest message of them all", Main.messageTextArray[index]);
    }

    @Test
    public void longestMessageReturnsMinusOneWhenEmpty() {
        assertEquals(-1, Main.findLongestMessageIndex());
    }

    @Test
    public void searchReturnsAllMessagesForARecipient() {
        addTestMessages();
        ArrayList<String> found = Main.searchByRecipient(NUMBER_ONE);

        assertEquals(2, found.size());
        assertTrue(found.contains("Hi there"));
        assertTrue(found.contains("See you soon"));
    }

    @Test
    public void searchReturnsNothingForUnknownRecipient() {
        addTestMessages();
        assertTrue(Main.searchByRecipient("+27820000000").isEmpty());
    }

    @Test
    public void deleteRemovesTheMessageWithThatHash() {
        addTestMessages();

        assertTrue(Main.deleteMessageByHash("22:2:THISALL"));
        assertEquals(2, Main.messages.size());
        assertEquals(2, Main.messageHashArray.length);
        assertEquals("11:1:HITHERE", Main.messageHashArray[0]);
        assertEquals("33:3:SEESOON", Main.messageHashArray[1]);
    }

    @Test
    public void deleteIgnoresCapitalLettersAndSpaces() {
        addTestMessages();
        assertTrue(Main.deleteMessageByHash("  11:1:hithere "));
        assertEquals(2, Main.messages.size());
    }

    @Test
    public void deleteReturnsFalseForUnknownHash() {
        addTestMessages();

        assertFalse(Main.deleteMessageByHash("99:9:NOPE"));
        assertEquals(3, Main.messages.size());
    }

    @Test
    public void messagesAreReadBackFromTheJsonFile() {
        Main.messages.add(new Main.Message("1111111111", 1, NUMBER_ONE,
                "He said \"hello\" to me", "11:1:HEME"));
        Main.messages.add(new Main.Message("2222222222", 2, NUMBER_TWO, "Second message", "22:2:SECONDMESSAGE"));
        Main.saveMessagesToJson();

        Main.Message[] loaded = Main.readMessagesFromJson(Main.JSON_FILE);

        assertEquals(2, loaded.length);
        assertEquals("1111111111", loaded[0].messageId);
        assertEquals(1, loaded[0].messageNumber);
        assertEquals("He said \"hello\" to me", loaded[0].messageText);
        assertEquals(NUMBER_TWO, loaded[1].recipient);
        assertEquals("22:2:SECONDMESSAGE", loaded[1].messageHash);
    }

    @Test
    public void readingAMissingJsonFileGivesAnEmptyArray() {
        assertEquals(0, Main.readMessagesFromJson("does_not_exist.json").length);
    }
}
