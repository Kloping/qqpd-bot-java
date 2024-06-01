## 网络相关文档


### 主要新增

```java

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
    boolean onOpen(WebSocketClient client, ServerHandshake handshake)

    /**
     * @param client
     * @param msg
     * @return 决定是否进行框架接下来的操作 true执行 此处若false则不执行(所有事件无法触发)
     */
    boolean onMessage(WebSocketClient client, String msg)

    /**
     * @param client
     * @param msg
     * @return 决定是否进行框架接下来的操作 true执行 若为false将无法发送消息
     */
    boolean onSend(WebSocketClient client, String msg)

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
    boolean onClose(WebSocketClient client, int code, String reason, boolean remote)

    /**
     * @param client
     * @param e
     * @return 同上 框架操作仅日志
     */
    boolean onError(WebSocketClient client, Exception e)
}

```

> 使用示例

```java
public class demo{
    public static void main(String[] args) {
        starter.getConfig().setWebSocketListener(new WebSocketListener() {
            @Override
            public boolean onOpen(WebSocketClient client, ServerHandshake handshake) {
                client.setConnectionLostTimeout(10);
                System.out.println("ws打开了");
                return true;
            }

            @Override
            public boolean onMessage(WebSocketClient client, String msg) {
                System.out.println("ws接收到消息了");
                return true;
            }

            @Override
            public boolean onSend(WebSocketClient client, String msg) {
                System.out.println("ws将发送消息");
                return true;
            }

            @Override
            public boolean onClose(WebSocketClient client, int code, String reason, boolean remote) {
                System.out.println("ws关闭了");
                return true;
            }

            @Override
            public boolean onError(WebSocketClient client, Exception e) {
                System.out.println("ws内部发送报错");
                return true;
            }
        });
    }
}
```