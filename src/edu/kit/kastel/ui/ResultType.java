package edu.kit.kastel.ui;

/**
 * The type of Result of a execution.
 *
 * @author Programmieren-Team
 */
public enum ResultType {
    /**
     * The execution did not end with success.
     */
    FAILURE() {
        @Override
        public <T> void printResult(String formattedMessage, T... args) {
            System.err.printf(ERROR + formattedMessage + NEW_LINE_SYMBOL, args);
        }
    },
    /**
     * The execution did end with success.
     */
    SUCCESS() {
        @Override
        public <T> void printResult(String formattedMessage, T... args) {
            System.out.printf(formattedMessage + NEW_LINE_SYMBOL, args);
        }
    };
    /**
     * New line symbol.
     */
    public static final String NEW_LINE_SYMBOL = "%n";
    private static final String ERROR = "Error: ";

    /**
     * Prints the result of the execution.
     * @param formattedMessage  the formatted message
     * @param args              the arguments
     * @param <T>               the type of the arguments
     */
    public abstract <T> void printResult(String formattedMessage, T... args);
}
