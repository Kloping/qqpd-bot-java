package io.github.kloping.qqbot.network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

/**
 * WebSocketClient的监听处理
 * <br>  根据提示文档自行处理操作
 *
 * @author github.kloping
 */
public interface WebSocketListener {
    /**
     * @param client
     * @param handshake
     * @return 决定是否进行框架接下来的操作 true执行
     */
    default boolean onOpen(WebSocketClient client, ServerHandshake handshake) {
        return true;
    }

    /**
     * @param client
     * @param msg
     * @return 决定是否进行框架接下来的操作 true执行 此处若false则不执行(所有事件无法触发)
     */
    default boolean onMessage(WebSocketClient client, String msg) {
        return true;
    }

    /**
     * @param client
     * @param msg
     * @return 决定是否进行框架接下来的操作 true执行 若为false将无法发送消息
     */
    default boolean onSend(WebSocketClient client, String msg) {
        return true;
    }

    /**
     * Called after the websocket connection has been closed.
     *
     * @param client
     * @param code   The codes can be looked up here: {@link CloseFrame}
     * @param reason Additional information string
     * @param remote Returns whether or not the closing of the connection was initiated by the remote
     *               host.
     * @return 决定是否进行框架接下来的操作 true执行 若为false将不自动重连
     */
    default boolean onClose(WebSocketClient client, int code, String reason, boolean remote) {
        return true;
    }

    /**
     * @param client
     * @param e
     * @return 同上 框架操作仅日志
     */
    default boolean onError(WebSocketClient client, Exception e) {
        return true;
    }
}
