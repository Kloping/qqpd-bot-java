package io.github.kloping.qqbot.entities.exceptions;

/**
 * @author github.kloping
 */
public class FileMsgUploadFailedException extends RuntimeException {
    public FileMsgUploadFailedException() {
    }

    public FileMsgUploadFailedException(String message) {
        super(message);
    }
}
