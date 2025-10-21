package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Student-specific prefix definitions */
    public static final Prefix PREFIX_CLASS = new Prefix("c/");
    public static final Prefix PREFIX_SUBJECTS = new Prefix("s/");
    public static final Prefix PREFIX_EMERGENCY_CONTACT = new Prefix("ec/");
    public static final Prefix PREFIX_ATTENDANCE = new Prefix("att/");
    public static final Prefix PREFIX_PAYMENT_STATUS = new Prefix("pay/");
    public static final Prefix PREFIX_ASSIGNMENT_STATUS = new Prefix("asg/");
    public static final Prefix PREFIX_LESSON = new Prefix("l/");
    public static final Prefix PREFIX_STATUS = new Prefix("st/");

}
