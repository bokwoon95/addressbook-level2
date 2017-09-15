package seedu.addressbook.ui;

import static seedu.addressbook.common.Messages.MESSAGE_GOODBYE;
import static seedu.addressbook.common.Messages.MESSAGE_INIT_FAILED;
import static seedu.addressbook.common.Messages.MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_STORAGE_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_WELCOME;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.personToAdd;

/**
 * Text UI of the application.
 */
public class TextUi {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = "|| ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = "===================================================";

    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    /** Format of a comment input line. Comment lines are silently consumed when reading user input. */
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";

    private static final Pattern fieldAndPrivacyPattern = Pattern.compile("(?<someString>[^/]+)(?<isPrivate>[/p]*)");

    private final Scanner in;
    private final PrintStream out;

    public TextUi() {
        this(System.in, System.out);
    }

    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Returns true if the user input line should be ignored.
     * Input should be ignored if it is parsed as a comment, is only whitespace, or is empty.
     *
     * @param rawInputLine full raw user input line.
     * @return true if the entire user input line should be ignored.
     */
    private boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Returns true if the user input line is a comment line.
     *
     * @param rawInputLine full raw user input line.
     * @return true if input line is a comment.
     */
    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     * Ignores empty, pure whitespace, and comment lines.
     * Echos the command back to the user.
     * @return command (full line) entered by the user
     */
    public String getUserCommand() {
        out.print(LINE_PREFIX + "Enter command: ");
        String fullInputLine = in.nextLine();

        // silently consume all ignored lines
        while (shouldIgnore(fullInputLine)) {
            fullInputLine = in.nextLine();
        }

        showToUser("[Command entered:" + fullInputLine + "]");
        return fullInputLine;
    }


    public void showWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        showToUser(
                DIVIDER,
                DIVIDER,
                MESSAGE_WELCOME,
                version,
                MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE,
                storageFileInfo,
                DIVIDER);
    }

    public void showGoodbyeMessage() {
        showToUser(MESSAGE_GOODBYE, DIVIDER, DIVIDER);
    }


    public void showInitFailedMessage() {
        showToUser(MESSAGE_INIT_FAILED, DIVIDER, DIVIDER);
    }

    /** Shows message(s) to the user */
    public void showToUser(String... message) {
        for (String m : message) {
            out.println(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX));
        }
    }

    /**
     * Shows the result of a command execution to the user. Includes additional formatting to demarcate different
     * command execution segments.
     */
    public void showResultToUser(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();
        if (resultPersons.isPresent()) {
            showPersonListView(resultPersons.get());
        }
        showToUser(result.feedbackToUser, DIVIDER);
    }

    /**
     * Shows a list of persons to the user, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void showPersonListView(List<? extends ReadOnlyPerson> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextHidePrivate());
        }
        showToUserAsIndexedList(formattedPersons);
    }

    /** Shows a list of strings to the user, formatted as an indexed list. */
    private void showToUserAsIndexedList(List<String> list) {
        showToUser(getIndexedListForViewing(list));
    }

    /** Formats a list of strings as a viewable indexed list. */
    private static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    public void initializePersonToAdd(personToAdd emptyPerson) {
        int inputCounter = 0;
        Boolean continueFlag;
        String input;
        System.out.println("NOTE: Enter 'undo' at any time to undo the last entry");
        while (inputCounter < 5) {
            continueFlag = false;
            switch (inputCounter) {
                case 0:
                    System.out.print("Enter Name: ");
                    break;
                case 1:
                    System.out.print("Enter Phone Number (append /p to set as private): ");
                    break;
                case 2:
                    System.out.print("Enter Email (append /p to set as private): ");
                    break;
                case 3:
                    System.out.print("Enter Address (append /p to set as private): ");
                    break;
                case 4:
                    System.out.print("Enter tags (separated by commas): ");
                    break;
            }

            input = in.nextLine();

            if (input.equals("undo")){
                switch (inputCounter) {
                    case 0:
                        break;
                    case 1:
                        continueFlag = true;
                        emptyPerson.setName("empty");
                        break;
                    case 2:
                        continueFlag = true;
                        emptyPerson.setPhone("empty");
                        emptyPerson.setPhonePrivacy(false);
                        break;
                    case 3:
                        continueFlag = true;
                        emptyPerson.setEmail("empty");
                        emptyPerson.setEmailPrivacy(false);
                        break;
                    case 4:
                        continueFlag = true;
                        emptyPerson.setAddress("empty");
                        emptyPerson.setAddressPrivacy(false);
                        break;
                }
                inputCounter--;
            }

            if (continueFlag) continue;

            switch (inputCounter) {
                case 0:
                    emptyPerson.setName(input);
                    break;
                case 1:
                    Matcher pMatch = fieldAndPrivacyPattern.matcher(input);
                    if (!pMatch.matches()) {
                        //throw exception
                    } else {
                        emptyPerson.setPhone(pMatch.group("someString"));
                        emptyPerson.setPhonePrivacy("/p".equals(pMatch.group("isPrivate")));
                    }
                    break;
                case 2:
                    Matcher eMatch = fieldAndPrivacyPattern.matcher(input);
                    if (!eMatch.matches()) {
                        //throw exception
                    } else {
                        emptyPerson.setEmail(eMatch.group("someString"));
                        emptyPerson.setEmailPrivacy("/p".equals(eMatch.group("isPrivate")));
                    }
                    break;
                case 3:
                    Matcher aMatch = fieldAndPrivacyPattern.matcher(input);
                    if (!aMatch.matches()) {
                        //throw exception
                    } else {
                        emptyPerson.setAddress(aMatch.group("someString"));
                        emptyPerson.setAddressPrivacy("/p".equals(aMatch.group("isPrivate")));
                    }
                    break;
                case 4:
                    if (input.isEmpty()) break;
                    for (String onetag: input.split(",")) {
                        emptyPerson.addTag(onetag);
                    }
                    break;
            }
            inputCounter++;
        }
    }

}
