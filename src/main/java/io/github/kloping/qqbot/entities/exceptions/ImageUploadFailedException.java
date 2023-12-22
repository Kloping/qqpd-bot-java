package io.github.kloping.qqbot.entities.exceptions;

/**
 * @author github.kloping
 */
public class ImageUploadFailedException extends RuntimeException {
    public ImageUploadFailedException() {
    }

    public ImageUploadFailedException(String message) {
        super(message);
    }
}
