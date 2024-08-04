## 事件文档

**_待完善.._**

- [Event](../src/main/java/io/github/kloping/qqbot/api/event/Event.java) 所有事件顶级接口

<hr>
以下事件可直接写入 ↓

[ListenerHost](../src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 监听事件中.
[详细实现](readme.md#事件注册)

- [MessageEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageEvent.java) 消息事件顶级接口
  
> 对框架未处理的事件 在 1.5.0-Alpha5 中添加的[事件注册的方法](../src/test/java/EventsRegisterTest.java)

    Starter#registerEventsRegister(Class<? extends Events.EventRegister> cla) 
