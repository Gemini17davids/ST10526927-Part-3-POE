
package com.mycompany.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @BeforeEach
    public void setUp() {

        // Reset registration details
        Main.registeredUsername = null;
        Main.registeredPassword = null;
        Main.registeredCellPhone = null;

        // Reset message collections
        Main.messages.clear();
        Main.sentList.clear();
        Main.disregardedList.clear();
        Main.storedList.clear();

        // Reset message counter
        Main.messageCounter = 0;

        // Reset arrays
        Main.populateArrays();
        Main.populateFlagArrays();
        Main.populateStoredArrays();
    }

    // =================================
    // USERNAME TESTS
    // =================================

    @Test
    public void testValidUsername() {
        assertTrue(Main.checkUserName("abc_d"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(Main.checkUserName("abcd"));
    }

    @Test
    public void testUsernameTooLong() {
        assertFalse(Main.checkUserName("abc_def"));
    }

    @Test
    public void testUsernameWithoutUnderscore() {
        assertFalse(Main.checkUserName("abcde"));
    }

    // =================================
    // PASSWORD TESTS
    // =================================

    @Test
    public void testValidPassword() {
        assertTrue(Main.checkPasswordComplexity("Password1@"));
    }

    @Test
    public void testPasswordTooShort() {
        assertFalse(Main.checkPasswordComplexity("Pa1@"));
    }

    @Test
    public void testPasswordWithoutCapitalLetter() {
        assertFalse(Main.checkPasswordComplexity("password1@"));
    }

    @Test
    public void testPasswordWithoutNumber() {
        assertFalse(Main.checkPasswordComplexity("Password@"));
    }

    @Test
    public void testPasswordWithoutSpecialCharacter() {
        assertFalse(Main.checkPasswordComplexity("Password123"));
    }

    // =================================
    // CELLPHONE NUMBER TESTS
    // =================================

    @Test
    public void testValidCellphoneNumber() {
        assertTrue(Main.checkCellPhoneNumber("+27812345678"));
    }

    @Test
    public void testInvalidCellphoneNumber() {
        assertFalse(Main.checkCellPhoneNumber("0812345678"));
    }

    @Test
    public void testCellphoneWithoutCountryCode() {
        assertFalse(Main.checkCellPhoneNumber("27812345678"));
    }

    @Test
    public void testCellphoneWithIncorrectLength() {
        assertFalse(Main.checkCellPhoneNumber("+2781234567"));
    }

    // =================================
    // MESSAGE ID TESTS
    // =================================

    @Test
    public void testMessageIdLength() {

        String id = Main.createMessageId();

        assertEquals(10, id.length());
    }

    @Test
    public void testMessageIdContainsOnlyDigits() {

        String id = Main.createMessageId();

        assertTrue(id.matches("[0-9]{10}"));
    }

    // =================================
    // MESSAGE HASH TESTS
    // =================================

    @Test
    public void testCreateMessageHash() {

        String hash = Main.createMessageHash(
                "1234567890",
                1,
                "Hello world"
        );

        assertEquals("12:1:HELLOWORLD", hash);
    }

    @Test
    public void testMessageHashUsesFirstTwoIdDigits() {

        String hash = Main.createMessageHash(
                "9876543210",
                2,
                "Java Testing"
        );

        assertEquals("98:2:JAVATESTING", hash);
    }

    // =================================
    // MESSAGE CREATION TESTS
    // =================================

    @Test
    public void testCreateMessage() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Hello there"
        );

        assertNotNull(message);
        assertEquals(1, message.messageNumber);
        assertEquals("+27812345678", message.recipient);
        assertEquals("Hello there", message.messageText);
        assertEquals(10, message.messageId.length());
    }

    // =================================
    // MESSAGE CHOICE TESTS
    // =================================

    @Test
    public void testSendMessage() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Hello world"
        );

        String result = Main.applyMessageChoice(message, "1");

        assertEquals("Message successfully sent.", result);
        assertEquals(1, Main.sentList.size());
    }

    @Test
    public void testDisregardMessage() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Test message"
        );

        String result = Main.applyMessageChoice(message, "2");

        assertEquals("Press 0 to delete the message.", result);
        assertEquals(1, Main.disregardedList.size());
    }

    @Test
    public void testInvalidMessageChoice() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Test message"
        );

        String result = Main.applyMessageChoice(message, "5");

        assertEquals("Invalid option.", result);
    }

    // =================================
    // MESSAGE ARRAY TESTS
    // =================================

    @Test
    public void testPopulateArrays() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Testing arrays"
        );

        Main.messages.add(message);

        Main.populateArrays();

        assertEquals(1, Main.messageIdArray.length);
        assertEquals("Testing arrays", Main.messageTextArray[0]);
    }

    // =================================
    // SEARCH MESSAGE TESTS
    // =================================

    @Test
    public void testSearchByRecipient() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Hello recipient"
        );

        Main.messages.add(message);

        ArrayList<String> result =
                Main.searchByRecipient("+27812345678");

        assertEquals(1, result.size());
        assertEquals("Hello recipient", result.get(0));
    }

    @Test
    public void testSearchByUnknownRecipient() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Hello recipient"
        );

        Main.messages.add(message);

        ArrayList<String> result =
                Main.searchByRecipient("+27999999999");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByMessageId() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Find this message"
        );

        Main.messages.add(message);

        String[] result =
                Main.searchByMessageId(message.messageId);

        assertNotNull(result);
        assertEquals("+27812345678", result[0]);
        assertEquals("Find this message", result[1]);
    }

    // =================================
    // LONGEST MESSAGE TESTS
    // =================================

    @Test
    public void testFindLongestMessage() {

        Main.messages.add(Main.createMessage(
                "+27812345678", "Short"
        ));

        Main.messages.add(Main.createMessage(
                "+27812345678", "This is the longest message"
        ));

        Main.messages.add(Main.createMessage(
                "+27812345678", "Medium message"
        ));

        int index = Main.findLongestMessageIndex();

        assertEquals(1, index);
    }

    @Test
    public void testLongestMessageWhenEmpty() {

        assertEquals(-1, Main.findLongestMessageIndex());
    }

    // =================================
    // DELETE MESSAGE TESTS
    // =================================

    @Test
    public void testDeleteMessageByHash() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Delete this message"
        );

        Main.messages.add(message);

        boolean result =
                Main.deleteMessageByHash(message.messageHash);

        assertTrue(result);
        assertEquals(0, Main.messages.size());
    }

    @Test
    public void testDeleteMessageWithInvalidHash() {

        boolean result =
                Main.deleteMessageByHash("INVALID_HASH");

        assertFalse(result);
    }

    // =================================
    // JSON TESTS
    // =================================

    @Test
    public void testEscapeJson() {

        String result = Main.escapeJson("Hello \"World\"");

        assertEquals("Hello \\\"World\\\"", result);
    }

    @Test
    public void testCleanJsonValue() {

        String result = Main.cleanJsonValue("\"Hello World\",");

        assertEquals("Hello World", result);
    }

    // =================================
    // SENDER TESTS
    // =================================

    @Test
    public void testGetSender() {

        Main.registeredUsername = "abc_d";
        Main.registeredCellPhone = "+27812345678";

        String result = Main.getSender();

        assertEquals("abc_d (+27812345678)", result);
    }

    // =================================
    // STORED MESSAGE TESTS
    // =================================

    @Test
    public void testPopulateStoredArrays() {

        Main.Message message = Main.createMessage(
                "+27812345678",
                "Stored message"
        );

        Main.storedList.add(message);

        Main.registeredUsername = "abc_d";
        Main.registeredCellPhone = "+27812345678";

        Main.populateStoredArrays();

        assertEquals(1, Main.storedMessageArray.length);
        assertEquals("Stored message", Main.storedMessageArray[0]);
    }

    @Test
    public void testGetLongestStoredMessage() {

        Main.storedMessageArray = new String[]{
            "Short",
            "This is the longest stored message",
            "Medium"
        };

        String result = Main.getLongestStoredMessage();

        assertEquals("This is the longest stored message", result);
    }

    @Test
    public void testBuildStoredReportWhenEmpty() {

        Main.populateStoredArrays();

        String result = Main.buildStoredReport();

        assertEquals(
                "No stored messages have been found.",
                result
        );
    }
}
