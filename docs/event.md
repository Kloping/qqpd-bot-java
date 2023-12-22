## 事件文档

**_待完善.._**

- [Event](../src/main/java/io/github/kloping/qqbot/api/event/Event.java) 所有事件顶级接口

<hr>
以下事件可直接写入 ↓

[ListenerHost](../src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 监听事件中.
[详细实现](readme.md#事件注册)

- [MessageEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageEvent.java) 消息事件顶级接口
  - [MessageDeleteEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageDeleteEvent.java) 消息撤回事件
  - [MessageContainsAtEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageContainsAtEvent.java)
    子频道存在AT消息事件
  - [MessageChannelReceiveEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageChannelReceiveEvent.java)
    子频道消息接收事件
  - [MessageDirectReceiveEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageDirectReceiveEvent.java)
    私信消息接收事件
  - [MessageReactionEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageReactionEvent.java) 消息被 添加/移除
    表情 事件
- [GuildEvent](../src/main/java/io/github/kloping/qqbot/api/event/GuildEvent.java) 频道事件
  - [GuildUpdateEvent](../src/main/java/io/github/kloping/qqbot/api/event/GuildUpdateEvent.java) 频道信息 更新事件
  - [ChannelEvent](../src/main/java/io/github/kloping/qqbot/api/event/ChannelEvent.java) 子频道事件
  - [MemberUpdateEvent](../src/main/java/io/github/kloping/qqbot/api/event/MemberUpdateEvent.java) 成员信息 更新事件
    - BaseMemberRemoveEvent
    - BaseMemberUpdateEvent
  
> 对框架未处理的事件 在 1.5.0-Alpha5 中添加的[事件注册的方法](../src/test/java/EventsRegisterTest.java)

    Starter#registerEventsRegister(Class<? extends Events.EventRegister> cla) 
