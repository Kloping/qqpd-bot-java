package io.github.kloping.qqbot.entities.ex;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class Image {
    private String url;
    private byte[] bytes;

    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    public Image(String url) {
        this.url = url;
    }
}
