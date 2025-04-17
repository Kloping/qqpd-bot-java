## 事件文档

### 事件概述

在 QQ 官方机器人 Java SDK 中，事件是处理机器人接收到的各种消息和状态变化的核心机制。通过注册事件监听器，开发者可以捕获并处理这些事件，从而实现机器人的各种功能。

### 事件注册

事件注册是通过 `Starter` 类的 `registerListenerHost` 方法完成的。开发者需要创建一个实现了 `ListenerHost` 接口的类，并在其中使用 `@EventReceiver` 注解标记事件处理方法。


**_待完善.._**

- [Event](../src/main/java/io/github/kloping/qqbot/api/event/Event.java) 所有事件顶级接口

<hr>
以下事件可直接写入 ↓

[ListenerHost](../src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 监听事件中.
[详细实现](readme.md#事件注册)

- [MessageEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageEvent.java) 消息事件顶级接口
  
> 对框架未处理的事件 在 1.5.0-Alpha5 中添加的[事件注册的方法](../src/test/java/EventsRegisterTest.java)

    Starter#registerEventsRegister(Class<? extends Events.EventRegister> cla) 
