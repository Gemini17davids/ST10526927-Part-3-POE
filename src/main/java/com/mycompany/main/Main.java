package com.mycompany.main;

// import Scanner so we can get a user input
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

// Create the main class
public class Main {
    
    // Create Scanner for user input
    static Scanner input = new Scanner(System.in);
    
    static String username;
    static String password;
    static String cellphone;
    
    // Stores the registered useername
    static String registeredUsername;
    
    // Stores the registered Password
    static String registeredPassword;
    
    // Stores the registered Cellphone number
    static String registeredCellPhone;
    
    public static boolean checkUserName(String username){
        
        // usernbame must have 5 Characters
       // and must contain an underscore
       if(username.length()== 5 && username.contains("_")){
         return true;
        } else {
        return false;
       }
    }
    
    // Create the password checking method
    public static boolean checkPasswordComplexity(String password){
        
        // Password must have 8 characters long, contain a capital letter
        // Contain a number and a spacial character
        if(password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*")) {
            return true;
        }else{
        
        return false;
    }
    }
    
    // Create the cellphone checking method
    public static boolean checkCellPhoneNumber(String cellphone){
        
        // Phone Number must have a country code (+27)
        if(cellphone.matches("\\+27[0-9]{9}$")){
            return true;
        } else{
        return false;
    }
    }
    
    // Create the registration method
    public static void registerUser(){
        
        // registration code will be added here
        while (true){
        System.out.print("Enter your Username: ");
        username = input.nextLine();
        // check username
        boolean usernameCorrect = checkUserName(username);
        if(usernameCorrect){
            break;
        } else{
            System.out.print("Username is incorerect formatted. Please try again");
        }
        }
        
        // infite loop for password
        while (true){
        System.out.print("Enter your Password: ");
        password = input.nextLine();
        // check username
       boolean passwordCorrect = checkPasswordComplexity(password);
        if(passwordCorrect){
            break;
        } else{
            System.out.print("Password is incorerect formatted. Please try again");
        }
        }
        
        // infite loop for cell phone number
        while (true){
        System.out.print("Enter your Cell Phone Number (+27): ");
        cellphone = input.nextLine();
        // check username
        boolean cellPhoneCorrect = checkCellPhoneNumber(cellphone);
        if(cellPhoneCorrect){
            break;
        } else{
            System.out.print("Cell Phone number is incorerect formatted. Please try again");
        }
      }
        
        // save the valid username
        registeredUsername = username;
        // save the valid password
        registeredPassword = password;
        // save the valid cellphone number
        registeredCellPhone = cellphone;
        // display success messege
        System.out.println("User registered successfully");
    }
    // Creates the log in method
    public static boolean loginUser(String username, String password){
        
        
        while (true){
        // Compare entered details with registered details
        if(username.equals(registeredUsername)
                && password.equals(registeredPassword)) {
            return true;
        } else {
            System.out.println("Username or password incorrect, please, please try again");
            // Re-promt the user for username
            System.out.print("Enter your username: ");
            username = input.nextLine();
            // Re-promt the user for password
            System.out.print("Enter your password: ");
            password = input.nextLine();
        }
        }
    }
    // Creates the login status method
    public static String returnLoginStatus(boolean loginSuccessful){
        
        // Check if login was successful
        if(loginSuccessful){
        
        return "Welcome, it is greate to see you again" + username;
        } else {
            return "Username or password incorrect, pleasew try again";
        }
    
    }
    
     // Main method where the program starts
    public static void main(String[] args) {
        
        // call registration method
        registerUser();
        
        System.out.println("");
        
        // aske user to login
        System.out.print("Enter your username to login: ");
            String loginUsername = input.nextLine();
            
        System.out.print("Enter your password to login: ");
            String loginPassword = input.nextLine();
            
            System.out.println("");
            
        // call login method
        boolean loginSuccessful = loginUser(loginUsername, loginPassword);
        // call login status method
        System.out.println(returnLoginStatus(loginSuccessful));
    
        
        //======================================
        //Part 2
        //======================================
       
        // Show the welcome message
        System.out.println("Welcome to QuickChat.");

        // Application loop: keeps running until the user chooses 3 (Quit)
        boolean running = true;
        while (running) {

            // Display the numeric menu
            System.out.println("");
            System.out.println("Please choose an option:");
            System.out.println("1) Send Message");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            // Part 3: fourth main menu option
            System.out.println("4) Stored Messages");
            System.out.print("Enter option (1-3): ");
            String option = input.nextLine();

            if (option.equals("1")) {
                // Add messages
                sendMessages();
            } else if (option.equals("2")) {
                // Part 3: open the messages menu
                messagesMenu();
            } else if (option.equals("3")) {
                System.out.println("Goodbye!");
                running = false;
            } else if (option.equals("4")) {
                // Part 3: open the Stored Messages menu
                storedMessagesMenu();
            } else {
                System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }

    // Simple class that holds the details of one message
    static class Message {
        String messageId;
        int messageNumber;
        String recipient;
        String messageText;
        String messageHash;

        Message(String messageId, int messageNumber, String recipient,
                String messageText, String messageHash) {
            this.messageId = messageId;
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.messageText = messageText;
            this.messageHash = messageHash;
        }
    }

    // ArrayList that stores all the messages
    static ArrayList<Message> messages = new ArrayList<>();

    // Counts the total number of messages entered (message number)
    static int messageCounter = 0;

    // Used to create random digits for the message ID
    static Random random = new Random();

    // Asks the user for a whole number greater than 0
    public static int askForNumber(String question) {
        while (true) {
            System.out.print(question);
            String text = input.nextLine();
            try {
                int number = Integer.parseInt(text.trim());
                if (number > 0) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // not a number, so ask again below
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }

    // Message ID: 10 random digits built one digit at a time with a loop counter
    public static String createMessageId() {
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }
        return id;
    }

    // Message Hash: first two digits of the ID + ":" + message number + ":"
    // + first word and last word of the message, all in capital letters
    public static String createMessageHash(String id, int number, String text) {
        // substring takes the first two digits of the message ID
        String firstTwo = id.substring(0, 2);

        // split the message into words
        String[] words = text.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (firstTwo + ":" + number + ":" + firstWord + lastWord).toUpperCase();
    }

    // Lets the user enter the number of messages they want to send
    public static void sendMessages() {

        int howMany = askForNumber("How many messages do you want to enter? ");

        // For loop runs once for each message the user wants to enter
        for (int i = 1; i <= howMany; i++) {

            // message number goes up by one each time the loop runs
            messageCounter++;
            System.out.println("");
            System.out.println("--- Message " + messageCounter + " ---");

            // Recipient cell number (same +27 rule used in registration)
            String recipient;
            while (true) {
                System.out.print("Enter the recipient cell phone number (+27): ");
                recipient = input.nextLine();
                if (checkCellPhoneNumber(recipient)) {
                    break;
                }
                System.out.println("Cell Phone number is incorrectly formatted. Please try again");
            }

            // Message text (1 to 250 characters)
            String text;
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                text = input.nextLine();
                if (text.trim().length() > 0 && text.length() <= 250) {
                    break;
                }
                System.out.println("Message must be between 1 and 250 characters. Please try again");
            }

            // Create the ID and hash, then store the message in the ArrayList
            String id = createMessageId();
            String hash = createMessageHash(id, messageCounter, text);
            messages.add(new Message(id, messageCounter, recipient, text, hash));

            // Part 2: let the user send, disregard or store the message
            System.out.println(sentMessage(messages.get(messages.size() - 1)));
        }

        // Display all the messages using a for-each loop
        System.out.println("");
        System.out.println("===== Message Details =====");
        for (Message m : messages) {
            System.out.println("Message ID:     " + m.messageId);
            System.out.println("Message Number: " + m.messageNumber);
            System.out.println("Message Hash:   " + m.messageHash);
            System.out.println("Recipient:      " + m.recipient);
            System.out.println("Message:        " + m.messageText);
            System.out.println("---------------------------");
        }

        saveMessagesToJson();
    }

    // Escapes quotes and backslashes so the text is valid inside JSON
    public static String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // Saves all the messages to messages.json
    // Attribution: the JSON text is built by hand with FileWriter (no library).
    // Code written with AI assistance from Claude (Anthropic, 2026).
    public static void saveMessagesToJson() {
        String json = "[\n";
        boolean firstMessage = true;

        // For-each loop goes through every stored message
        for (Message m : messages) {
            if (!firstMessage) {
                json = json + ",\n";
            }
            firstMessage = false;

            json = json + "  {\n"
                    + "    \"messageId\": \"" + escapeJson(m.messageId) + "\",\n"
                    + "    \"messageNumber\": " + m.messageNumber + ",\n"
                    + "    \"messageHash\": \"" + escapeJson(m.messageHash) + "\",\n"
                    + "    \"recipient\": \"" + escapeJson(m.recipient) + "\",\n"
                    + "    \"message\": \"" + escapeJson(m.messageText) + "\"\n"
                    + "  }";
        }
        json = json + "\n]\n";

        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(json);
            System.out.println("Messages saved to messages.json");
        } catch (IOException e) {
            System.out.println("Could not save the messages file.");
        }
    }

    //======================================
    // Part 3 
    //======================================

    // Parallel arrays: the same index in every array belongs to the same message
    static String[] messageIdArray = new String[0];
    static String[] messageHashArray = new String[0];
    static String[] recipientArray = new String[0];
    static String[] messageTextArray = new String[0];

    // Name of the file the messages are saved to (see saveMessagesToJson)
    static final String JSON_FILE = "messages.json";

    // Fills the parallel arrays from the stored messages so they are always up to date
    public static void populateArrays() {
        int size = messages.size();
        messageIdArray = new String[size];
        messageHashArray = new String[size];
        recipientArray = new String[size];
        messageTextArray = new String[size];

        for (int i = 0; i < size; i++) {
            Message m = messages.get(i);
            messageIdArray[i] = m.messageId;
            messageHashArray[i] = m.messageHash;
            recipientArray[i] = m.recipient;
            messageTextArray[i] = m.messageText;
        }
    }

    // Returns the index of the longest message, or -1 when there are no messages
    public static int findLongestMessageIndex() {
        populateArrays();
        int longestIndex = -1;

        for (int i = 0; i < messageTextArray.length; i++) {
            if (longestIndex == -1
                    || messageTextArray[i].length() > messageTextArray[longestIndex].length()) {
                longestIndex = i;
            }
        }
        return longestIndex;
    }

    // Returns the text of every message that was sent to the given recipient
    public static ArrayList<String> searchByRecipient(String recipient) {
        populateArrays();
        ArrayList<String> found = new ArrayList<>();

        for (int i = 0; i < recipientArray.length; i++) {
            if (recipientArray[i].equals(recipient.trim())) {
                found.add(messageTextArray[i]);
            }
        }
        return found;
    }

    // Deletes the message with the given hash; returns true if one was removed
    public static boolean deleteMessageByHash(String hash) {
        populateArrays();

        for (int i = 0; i < messageHashArray.length; i++) {
            if (messageHashArray[i].equalsIgnoreCase(hash.trim())) {
                messages.remove(i);
                populateArrays();
                return true;
            }
        }
        return false;
    }

    // Shows the details of the longest message
    public static void displayLongestMessage() {
        int index = findLongestMessageIndex();

        if (index == -1) {
            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("");
        System.out.println("===== Longest Message =====");
        System.out.println("Message ID:   " + messageIdArray[index]);
        System.out.println("Message Hash: " + messageHashArray[index]);
        System.out.println("Recipient:    " + recipientArray[index]);
        System.out.println("Message:      " + messageTextArray[index]);
        System.out.println("Length:       " + messageTextArray[index].length() + " characters");
    }

    // Asks for a recipient and shows every message sent to them
    public static void displayMessagesForRecipient() {
        System.out.print("Enter the recipient cell phone number (+27): ");
        String recipient = input.nextLine();

        ArrayList<String> found = searchByRecipient(recipient);

        if (found.isEmpty()) {
            System.out.println("No messages were found for " + recipient.trim() + ".");
            return;
        }

        System.out.println("");
        System.out.println("===== Messages sent to " + recipient.trim() + " =====");
        for (int i = 0; i < found.size(); i++) {
            System.out.println((i + 1) + ") " + found.get(i));
        }
    }

    // Asks for a message hash, deletes that message and updates the saved file
    public static void deleteMessageFromMenu() {
        System.out.print("Enter the message hash to delete: ");
        String hash = input.nextLine();

        if (deleteMessageByHash(hash)) {
            System.out.println("Message \"" + hash.trim().toUpperCase() + "\" was deleted.");
            // keep messages.json in step with the stored messages
            saveMessagesToJson();
        } else {
            System.out.println("No message with the hash \"" + hash.trim() + "\" was found.");
        }
    }

    // Shows a report of every sent message
    public static void displayMessageReport() {
        populateArrays();

        if (messageIdArray.length == 0) {
            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("");
        System.out.println("===== Message Report =====");
        for (int i = 0; i < messageIdArray.length; i++) {
            System.out.println("Message " + (i + 1) + " of " + messageIdArray.length);
            System.out.println("Message ID:   " + messageIdArray[i]);
            System.out.println("Message Hash: " + messageHashArray[i]);
            System.out.println("Recipient:    " + recipientArray[i]);
            System.out.println("Message:      " + messageTextArray[i]);
            System.out.println("--------------------------");
        }
        System.out.println("Total messages: " + messageIdArray.length);
    }

    // Removes the surrounding quotes and comma from a JSON value and restores
    // the characters that escapeJson() changed
    public static String cleanJsonValue(String rawValue) {
        String value = rawValue.trim();

        if (value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }
        if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        // Go through the text once so \\ and \" are restored correctly
        String result = "";
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\\' && i + 1 < value.length()) {
                i++;
                result = result + value.charAt(i);
            } else {
                result = result + c;
            }
        }
        return result;
    }

    // Reads messages.json into an array of messages
    
    public static Message[] readMessagesFromJson(String fileName) {
        ArrayList<Message> loaded = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return new Message[0];
        }

        String id = "";
        int number = 0;
        String hash = "";
        String recipient = "";
        String text = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Each line looks like:  "key": value
                int colon = line.indexOf(':');
                if (line.startsWith("\"") && colon > 0) {
                    String key = line.substring(1, line.indexOf('"', 1));
                    String value = cleanJsonValue(line.substring(colon + 1));

                    if (key.equals("messageId")) {
                        id = value;
                    } else if (key.equals("messageNumber")) {
                        number = Integer.parseInt(value.trim());
                    } else if (key.equals("messageHash")) {
                        hash = value;
                    } else if (key.equals("recipient")) {
                        recipient = value;
                    } else if (key.equals("message")) {
                        text = value;
                    }
                } else if (line.startsWith("}")) {
                    // end of one message, so store it
                    loaded.add(new Message(id, number, recipient, text, hash));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not read the messages file.");
        }

        return loaded.toArray(new Message[0]);
    }

    // Reads messages.json and displays what was in the file
    public static void displaySavedMessages() {
        Message[] saved = readMessagesFromJson(JSON_FILE);

        if (saved.length == 0) {
            System.out.println("No saved messages were found in " + JSON_FILE + ".");
            return;
        }

        System.out.println("");
        System.out.println("===== Messages read from " + JSON_FILE + " =====");
        for (Message m : saved) {
            System.out.println("Message ID:     " + m.messageId);
            System.out.println("Message Number: " + m.messageNumber);
            System.out.println("Message Hash:   " + m.messageHash);
            System.out.println("Recipient:      " + m.recipient);
            System.out.println("Message:        " + m.messageText);
            System.out.println("---------------------------");
        }
    }

    // Sub menu opened from option 2 of the main menu
    public static void messagesMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("");
            System.out.println("===== Messages Menu =====");
            System.out.println("1) Display the longest message");
            System.out.println("2) Search messages by recipient");
            System.out.println("3) Delete a message using its hash");
            System.out.println("4) Display the message report");
            System.out.println("5) Read messages from the JSON file");
            System.out.println("6) Back to the main menu");
            System.out.print("Enter option (1-6): ");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                displayLongestMessage();
            } else if (choice.equals("2")) {
                displayMessagesForRecipient();
            } else if (choice.equals("3")) {
                deleteMessageFromMenu();
            } else if (choice.equals("4")) {
                displayMessageReport();
            } else if (choice.equals("5")) {
                displaySavedMessages();
            } else if (choice.equals("6")) {
                inMenu = false;
            } else {
                System.out.println("Invalid option. Please enter a number from 1 to 6.");
            }
        }
    }

    //======================================
    // 
    //======================================

    // Lists that hold the messages by flag (they are filled while the program runs)
    static ArrayList<Message> sentList = new ArrayList<>();
    static ArrayList<Message> disregardedList = new ArrayList<>();
    static ArrayList<Message> storedList = new ArrayList<>();

    // Sent Messages array: contains all the messages sent
    static String[] sentMessageArray = new String[0];
    // Disregarded Messages array: contains all the messages that were disregarded
    static String[] disregardedMessageArray = new String[0];
    // Stored Messages arrays: filled by reading the stored JSON file
    static String[] storedMessageArray = new String[0];
    static String[] storedIdArray = new String[0];
    static String[] storedHashArray = new String[0];
    static String[] storedRecipientArray = new String[0];
    static String[] storedSenderArray = new String[0];

    // Name of the file the stored messages are saved to
    static final String STORED_JSON_FILE = "storedMessages.json";

    // The sender is the user who registered and logged in
    public static String getSender() {
        if (registeredUsername == null) {
            return "Unknown sender";
        }
        if (registeredCellPhone == null) {
            return registeredUsername;
        }
        return registeredUsername + " (" + registeredCellPhone + ")";
    }

    // Creates a message object with the next message number, ID and hash
    public static Message createMessage(String recipient, String text) {
        messageCounter++;
        String id = createMessageId();
        String hash = createMessageHash(id, messageCounter, text);
        return new Message(id, messageCounter, recipient, text, hash);
    }

    // Fills the sent and disregarded arrays from their lists (no hard-coding)
    public static void populateFlagArrays() {
        sentMessageArray = new String[sentList.size()];
        for (int i = 0; i < sentList.size(); i++) {
            sentMessageArray[i] = sentList.get(i).messageText;
        }

        disregardedMessageArray = new String[disregardedList.size()];
        for (int i = 0; i < disregardedList.size(); i++) {
            disregardedMessageArray[i] = disregardedList.get(i).messageText;
        }
    }

    // Fills the stored arrays from the stored list (which was read from the JSON file)
    public static void populateStoredArrays() {
        int size = storedList.size();
        storedMessageArray = new String[size];
        storedIdArray = new String[size];
        storedHashArray = new String[size];
        storedRecipientArray = new String[size];
        storedSenderArray = new String[size];

        for (int i = 0; i < size; i++) {
            Message m = storedList.get(i);
            storedMessageArray[i] = m.messageText;
            storedIdArray[i] = m.messageId;
            storedHashArray[i] = m.messageHash;
            storedRecipientArray[i] = m.recipient;
            storedSenderArray[i] = getSender();
        }
    }

    // Reads storedMessages.json into the stored list and fills the stored arrays
    public static void loadStoredMessages() {
        storedList = new ArrayList<>();
        Message[] fromFile = readMessagesFromJson(STORED_JSON_FILE);
        for (Message m : fromFile) {
            storedList.add(m);
        }
        populateStoredArrays();
    }

    // Saves the stored list to storedMessages.json
    // Attribution: same hand-built JSON approach as saveMessagesToJson (no library).
    public static void saveStoredMessagesToJson() {
        String json = "[\n";
        boolean firstMessage = true;

        for (Message m : storedList) {
            if (!firstMessage) {
                json = json + ",\n";
            }
            firstMessage = false;

            json = json + "  {\n"
                    + "    \"messageId\": \"" + escapeJson(m.messageId) + "\",\n"
                    + "    \"messageNumber\": " + m.messageNumber + ",\n"
                    + "    \"messageHash\": \"" + escapeJson(m.messageHash) + "\",\n"
                    + "    \"recipient\": \"" + escapeJson(m.recipient) + "\",\n"
                    + "    \"message\": \"" + escapeJson(m.messageText) + "\"\n"
                    + "  }";
        }
        json = json + "\n]\n";

        try (FileWriter writer = new FileWriter(STORED_JSON_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Could not save the stored messages file.");
        }
    }

    // storeMessage(): stores a message in the JSON file, then reads the file back into the arrays
    public static void storeMessage(Message m) {
        // read what is already stored so earlier stored messages are not lost
        loadStoredMessages();
        storedList.add(m);
        saveStoredMessagesToJson();
        loadStoredMessages();
    }

    // Records a message under its flag: "Sent", "Disregard" or "Stored"
    public static void recordMessage(Message m, String flag) {
        if (flag.equalsIgnoreCase("Sent")) {
            sentList.add(m);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedList.add(m);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storeMessage(m);
        }
        populateFlagArrays();
    }

    // Applies the user's choice (1 = send, 2 = disregard, 3 = store) and returns the message
    public static String applyMessageChoice(Message m, String choice) {
        if (choice.equals("1")) {
            recordMessage(m, "Sent");
            return "Message successfully sent.";
        } else if (choice.equals("2")) {
            recordMessage(m, "Disregard");
            return "Press 0 to delete the message.";
        } else if (choice.equals("3")) {
            recordMessage(m, "Stored");
            return "Message successfully stored.";
        }
        return "Invalid option.";
    }

    // SentMessage(): lets the user choose to send, disregard or store the message
    public static String sentMessage(Message m) {
        String choice;
        while (true) {
            System.out.println("");
            System.out.println("What would you like to do with this message?");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter option (1-3): ");
            choice = input.nextLine().trim();
            if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                break;
            }
            System.out.println("Invalid option. Please enter 1, 2 or 3.");
        }

        String result = applyMessageChoice(m, choice);

        // Disregarded messages are deleted when the user presses 0
        if (choice.equals("2")) {
            System.out.println(result);
            String zero = input.nextLine().trim();
            if (zero.equals("0")) {
                messages.remove(m);
                return "Message deleted.";
            }
            return "Message disregarded.";
        }
        return result;
    }

    // Returns "sender -> recipient" for every stored message
    public static ArrayList<String> getStoredSenderAndRecipient() {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < storedRecipientArray.length; i++) {
            lines.add("Sender: " + storedSenderArray[i] + " | Recipient: " + storedRecipientArray[i]);
        }
        return lines;
    }

    // Returns the index of the longest stored message, or -1 when none are stored
    public static int findLongestStoredMessageIndex() {
        int longestIndex = -1;
        for (int i = 0; i < storedMessageArray.length; i++) {
            if (longestIndex == -1
                    || storedMessageArray[i].length() > storedMessageArray[longestIndex].length()) {
                longestIndex = i;
            }
        }
        return longestIndex;
    }

    // Returns the text of the longest stored message ("" when none are stored)
    public static String getLongestStoredMessage() {
        int index = findLongestStoredMessageIndex();
        if (index == -1) {
            return "";
        }
        return storedMessageArray[index];
    }

    // Searches for a message ID; returns {recipient, message} or null when not found
    public static String[] searchByMessageId(String id) {
        populateArrays();

        for (int i = 0; i < messageIdArray.length; i++) {
            if (messageIdArray[i].equals(id.trim())) {
                return new String[]{recipientArray[i], messageTextArray[i]};
            }
        }
        // also look in the stored arrays (read from the JSON file)
        for (int i = 0; i < storedIdArray.length; i++) {
            if (storedIdArray[i].equals(id.trim())) {
                return new String[]{storedRecipientArray[i], storedMessageArray[i]};
            }
        }
        return null;
    }

    // Returns every stored message for the given recipient
    public static ArrayList<String> searchStoredByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < storedRecipientArray.length; i++) {
            if (storedRecipientArray[i].equals(recipient.trim())) {
                found.add(storedMessageArray[i]);
            }
        }
        return found;
    }

    // Deletes a stored message using its hash and returns the result message
    public static String deleteStoredMessageByHash(String hash) {
        for (int i = 0; i < storedHashArray.length; i++) {
            if (storedHashArray[i].equalsIgnoreCase(hash.trim())) {
                String text = storedMessageArray[i];
                storedList.remove(i);
                saveStoredMessagesToJson();
                populateStoredArrays();
                // keep the main message list in step as well
                deleteMessageByHash(hash);
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "No stored message with the hash \"" + hash.trim() + "\" was found.";
    }

    // Builds the report of all stored messages
    public static String buildStoredReport() {
        String nl = System.lineSeparator();
        if (storedMessageArray.length == 0) {
            return "No stored messages have been found.";
        }

        String report = "===== Stored Messages Report =====" + nl;
        for (int i = 0; i < storedMessageArray.length; i++) {
            report = report + "Message " + (i + 1) + " of " + storedMessageArray.length + nl
                    + "Message ID:   " + storedIdArray[i] + nl
                    + "Message Hash: " + storedHashArray[i] + nl
                    + "Sender:       " + storedSenderArray[i] + nl
                    + "Recipient:    " + storedRecipientArray[i] + nl
                    + "Message:      " + storedMessageArray[i] + nl
                    + "--------------------------" + nl;
        }
        report = report + "Total stored messages: " + storedMessageArray.length;
        return report;
    }

    // Menu option 4 on the main menu: Stored Messages
    public static void storedMessagesMenu() {
        // read the JSON file into the stored arrays each time the menu opens
        loadStoredMessages();
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("");
            System.out.println("===== Stored Messages Menu =====");
            System.out.println("1) Display the sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search stored messages by recipient");
            System.out.println("5) Delete a stored message using its hash");
            System.out.println("6) Display the stored messages report");
            System.out.println("7) Back to the main menu");
            System.out.print("Enter option (1-7): ");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                ArrayList<String> lines = getStoredSenderAndRecipient();
                if (lines.isEmpty()) {
                    System.out.println("No stored messages have been found.");
                }
                for (String line : lines) {
                    System.out.println(line);
                }
            } else if (choice.equals("2")) {
                String longest = getLongestStoredMessage();
                if (longest.isEmpty()) {
                    System.out.println("No stored messages have been found.");
                } else {
                    System.out.println("Longest stored message: " + longest);
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter the message ID: ");
                String[] result = searchByMessageId(input.nextLine());
                if (result == null) {
                    System.out.println("No message with that ID was found.");
                } else {
                    System.out.println("Recipient: " + result[0]);
                    System.out.println("Message:   " + result[1]);
                }
            } else if (choice.equals("4")) {
                System.out.print("Enter the recipient cell phone number (+27): ");
                String recipient = input.nextLine();
                ArrayList<String> found = searchStoredByRecipient(recipient);
                if (found.isEmpty()) {
                    System.out.println("No stored messages were found for " + recipient.trim() + ".");
                }
                for (int i = 0; i < found.size(); i++) {
                    System.out.println((i + 1) + ") " + found.get(i));
                }
            } else if (choice.equals("5")) {
                System.out.print("Enter the message hash to delete: ");
                System.out.println(deleteStoredMessageByHash(input.nextLine()));
            } else if (choice.equals("6")) {
                System.out.println(buildStoredReport());
            } else if (choice.equals("7")) {
                inMenu = false;
            } else {
                System.out.println("Invalid option. Please enter a number from 1 to 7.");
            }
        }
    }
}
