package edu.kit.kastel.ui;

/**
 * This class describes a result of a command execution.
 *
 * @author Programmieren-Team
 */
public class Result {
    private final ResultType type;
    private final String message;

    /**
     * Constructs a new Result without a message.
     * @param type the type of the result
     */
    public Result(final ResultType type) {
        this(type, null);
    }

    /**
     * Constructs a new Result with a message.
     * @param type    the type of the result
     * @param message message to carry
     */
    public Result(final ResultType type, final String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Returns the type of the result.
     * @return the type of the result
     */
    public ResultType getType() {
        return this.type;
    }

    /**
     * Returns the carried message of the result or {@code null} if there is none.
     * @return the message or {@code null}
     */
    public String getMessage() {
        return this.message;
    }
}
