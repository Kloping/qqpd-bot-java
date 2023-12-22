package io.github.kloping.qqbot.entities.ex;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 无模板 暂未适配自定义按钮
 * 按钮不可单独发送
 * 需与md一并发送
 *
 * @author github.kloping
 */
@Data
@AllArgsConstructor
public class Keyboard {
    private String id;
}
