package io.github.kloping.qqbot.entitys;

import lombok.Data;

/**
 * <table><thead><tr><th>CODE</th> <th>名称</th> <th>客户端操作</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>Dispatch</td> <td>Receive</td> <td>服务端进行消息推送</td></tr> <tr><td>1</td> <td>Heartbeat</td> <td>Send/Receive</td> <td>客户端或服务端发送心跳</td></tr> <tr><td>2</td> <td>Identify</td> <td>Send</td> <td>客户端发送鉴权</td></tr> <tr><td>6</td> <td>Resume</td> <td>Send</td> <td>客户端恢复连接</td></tr> <tr><td>7</td> <td>Reconnect</td> <td>Receive</td> <td>服务端通知客户端重新连接</td></tr> <tr><td>9</td> <td>Invalid Session</td> <td>Receive</td> <td>当identify或resume的时候，如果参数有错，服务端会返回该消息</td></tr> <tr><td>10</td> <td>Hello</td> <td>Receive</td> <td>当客户端与网关建立ws连接之后，网关下发的第一条消息</td></tr> <tr><td>11</td> <td>Heartbeat ACK</td> <td>Receive/Reply</td> <td>当发送心跳成功之后，就会收到该消息</td></tr> <tr><td>12</td> <td>HTTP Callback ACK</td> <td>Reply</td> <td>仅用于 http 回调模式的回包，代表机器人收到了平台推送的数据</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
public class Pack<T> {
    private Integer op;
    private Number s;
    private T d;
    private String t;
}
