package it.bz.opendatahub.alpinebitsserver.application.common.exception;

/**
 * This exception is thrown if a condition is not met, that is
 * necessary for the application to start.
 * <p>
 * An example is a missing System.env property needed for configuration
 */
public class StartupConditionFailedException extends RuntimeException {

    /**
     * Constructs a new StartupConditionFailedException exception with the specified
     * detail message. The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public StartupConditionFailedException(String message) {
        super(message);
    }

}
