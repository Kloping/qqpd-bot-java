## 事件文档

- [Event](../src/main/java/io/github/kloping/qqbot/api/interfaces/Event.java) 所有事件顶级接口
- [ChannelEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/Event.java) 子频道事件
- [GuildEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/Event.java) 频道事件

<hr>
以下事件可直接写入 ↓

[ListenerHost](../src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 监听事件中.
详细实现 见 readme V1.4+ 注册监听器主机方式

- [MessageEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/message/MessageEvent.java) 消息事件顶级接口
- [MessageDeleteEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/message/MessageDeleteEvent.java) 消息撤回事件
- [MessageContainsAtEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/message/MessageContainsAtEvent.java) 子频道存在AT事件
- [MessageChannelReceiveEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/message/MessageChannelReceiveEvent.java) 子频道消息接收事件
- [MessageDirectReceiveEvent](../src/main/java/io/github/kloping/qqbot/api/interfaces/message/MessageDirectReceiveEvent.java) 私信消息接收事件
