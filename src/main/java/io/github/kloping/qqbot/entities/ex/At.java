package io.github.kloping.qqbot.entities.ex;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class At {
    private String type;
    private String targetId;

    public At(String type, String targetId) {
        this.type = type;
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        if (type == "channel") {
            return "<#" + targetId + ">";
        } else if (type == "member") {
            return "<@" + targetId + ">";
        } else return "@";
    }
}
