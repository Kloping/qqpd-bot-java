package io.github.kloping.qqbot.http.data;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class Result<D> {
    private String msg;
    private D data;

    public Result(D data) {
        this.data = data;
    }

    public Result() {
    }
}
