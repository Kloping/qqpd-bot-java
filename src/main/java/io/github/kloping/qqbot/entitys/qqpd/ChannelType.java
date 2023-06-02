package io.github.kloping.qqbot.entitys.qqpd;

/**
 * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>文字子频道</td></tr> <tr><td>1</td> <td>保留，不可用</td></tr> <tr><td>2</td> <td>语音子频道</td></tr> <tr><td>3</td> <td>保留，不可用</td></tr> <tr><td>4</td> <td>子频道分组</td></tr> <tr><td>10005</td> <td>直播子频道</td></tr> <tr><td>10006</td> <td>应用子频道</td></tr> <tr><td>10007</td> <td>论坛子频道</td></tr></tbody></table>
 *
 * @author github.kloping
 */
public enum ChannelType {
    TEXT_CHANNEL(0),
    UNKNOWN1(1),
    VOICE_CHANNEL(2),
    UNKNOWN3(3),
    GROUPING(4),
    LIVING_CHANNEL(10005),
    APP_CHANNEL(10006),
    FORUM_CHANNEL(10007),
    ;

    final int id;

    ChannelType(int id) {
        this.id = id;
    }
}
