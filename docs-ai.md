# QQ机器人 Java SDK 文档

## 简介

QQ机器人 Java SDK 是一个非官方的 QQ 机器人开发工具包，支持 Java 8+ 及 Kotlin 环境。该 SDK 提供了简单、高效的 API 来开发 QQ 机器人，支持 QQ 频道与 Q 群消息收发（私域/公域兼容）。

## 特性

- 支持 QQ 官方频道与 Q 群消息收发（私域/公域兼容）
- 开箱即用的消息类型（文本/图片/Markdown/按钮交互）
- 灵活的事件监听机制（`@EventReceiver`注解驱动）
- 多环境支持（沙箱/正式环境一键切换）
- 完善的 HTTP API 封装（频道管理、禁言、消息审核等）
- 支持 Java 8+ 及 Kotlin 协程环境
- 支持 WebSocket 与 Webhook 两种连接方式

## 环境要求

- Java 8 或更高版本
- Maven 或 Gradle 构建工具

## 安装

### Maven

```xml
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.5.3-L1</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.kloping:bot-qqpd-java:1.5.3-L1'
```

## 快速开始

### 1. 获取机器人凭证

在开始使用 SDK 之前，您需要到 [QQ机器人官方平台](https://q.qq.com/) 申请机器人，获得以下信息：

- Bot 开发者ID (appid)
- 机器人令牌 (token)
- （可选）机器人密钥 (secret) - 用于 Q 群功能

### 2. 基础使用

```java
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;

public class MyBot {
    public static void main(String[] args) {
        // 创建 Starter 实例
        Starter starter = new Starter("your_appid", "your_token");
        
        // 设置事件订阅（私域机器人示例）
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        
        // 启动机器人
        starter.run();
    }
}
```

### 3. 注册事件监听器

推荐使用 `ListenerHost` 方式注册事件监听器：

```java
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.impl.ListenerHost;

starter.registerListenerHost(new ListenerHost(){
    @EventReceiver
    public void onEvent(MessageChannelReceiveEvent event){
        event.send("频道消息回复测试");
    }
    
    @EventReceiver
    public void onEvent(MessageDirectReceiveEvent event){
        event.send("私信消息回复测试");
    }
});
```

## 核心概念

### Starter

[Starter](./src/main/java/io/github/kloping/qqbot/Starter.java) 是 SDK 的入口类，负责初始化和运行机器人。

#### 构造函数

```java
// 用于频道机器人
Starter starter = new Starter("appid", "token");

// 用于 Q 群机器人（需要 secret）
Starter starter = new Starter("appid", "token", "secret");
```

#### 主要方法

- `run()`: 启动机器人
- `registerListenerHost(ListenerHost listenerHost)`: 注册事件监听器
- `setReconnect(Boolean reconnect)`: 设置断线重连
- `getConfig()`: 获取配置对象

)

通过 `starter.getConfig()` 获取配置对象，主要配置项包括：

- `setCode(Integer code)`: 设置事件订阅类型
- `setReconnect(Boolean reconnect)`: 设置是否断线重连（默认 true）
- `setWslink(String wslink)`: 设置 WebSocket 链接地址
- `setWebhookport(Integer webhookport)`: 设置 Webhook 端口
- `sandbox()`: 切换沙箱环境

)

事件订阅决定了机器人能接收到哪些事件。根据机器人的类型（公域/私域）选择合适的订阅：

```java
// 公域机器人推荐
starter.getConfig().setCode(Intents.PUBLIC_INTENTS.getCode());

// 私域机器人推荐
starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());

// 自定义订阅
starter.getConfig().setCode(Intents.GUILD_MESSAGES.and(Intents.DIRECT_MESSAGE));
```

)

[ListenerHost](./src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 是事件监听器的基类，通过 `@EventReceiver` 注解标记处理方法：

```java
public class MyListener extends ListenerHost {
    @EventReceiver
    public void onMessage(MessageEvent event) {
        // 处理消息事件
    }
}
```

### 消息事件

SDK 提供了多种消息事件类型：

- [MessageChannelReceiveEvent](./src/main/java/io/github/kloping/qqbot/api/message/MessageChannelReceiveEvent.java): 频道消息事件
- [MessageDirectReceiveEvent](./src/main/java/io/github/kloping/qqbot/api/message/MessageDirectReceiveEvent.java): 私信消息事件
- [MessageEvent](./src/main/java/io/github/kloping/qqbot/api/message/MessageEvent.java): 消息事件接口

### 消息发送

通过事件对象的 `send()` 方法可以发送消息：

```java
@EventReceiver
public void onMessage(MessageEvent event) {
    // 发送文本消息
    event.send("Hello World");
    
    // 发送复杂消息
    MessageChain chain = new MessageChain();
    chain.text("Hello ").at("user_id").image("image_url");
    event.send(chain);
}
```

## 消息类型

### MessageChain

[MessageChain](./src/main/java/io/github/kloping/qqbot/entities/ex/msg/MessageChain.java) 是消息链，可以包含多种消息元素：

```java
MessageChain chain = new MessageChain();
chain.text("文本消息")
     .at("用户ID")
     .image("图片URL")
     .append(new Markdown("Markdown内容"));
```

### 支持的消息元素

- [PlainText](./src/main/java/io/github/kloping/qqbot/entities/ex/PlainText.java): 纯文本
- [At](./src/main/java/io/github/kloping/qqbot/entities/ex/At.java): @某人
- [AtAll](./src/main/java/io/github/kloping/qqbot/entities/ex/AtAll.java): @全体成员
- [Image](./src/main/java/io/github/kloping/qqbot/entities/ex/Image.java): 图片
- [Markdown](./src/main/java/io/github/kloping/qqbot/entities/ex/Markdown.java): Markdown 消息
- [FileMsg](./src/main/java/io/github/kloping/qqbot/entities/ex/FileMsg.java): 文件消息

## 高级功能

### Webhook 模式

除了默认的 WebSocket 连接方式，SDK 还支持 Webhook 模式：

```java
Starter starter = new Starter("appid", "token");
starter.getConfig().setWebhookport(8080); // 设置 Webhook 端口
starter.run();
```

### 沙箱环境

```java
Starter starter = new Starter("appid", "token");
starter.getConfig().sandbox(); // 切换到沙箱环境
starter.run();
```

### 异常处理

在 [ListenerHost](./src/main/java/io/github/kloping/qqbot/impl/ListenerHost.java) 中重写 `handleException` 方法处理异常：

```java
public class MyListener extends ListenerHost {
    @Override
    public boolean handleException(Throwable e) {
        // 处理异常
        e.printStackTrace();
        return true; // 返回 true 继续日志打印
    }
}
```

## 完整示例

```java
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;

public class MyBot {
    public static void main(String[] args) {
        // 初始化
        Starter starter = new Starter("your_appid", "your_token");
        
        // 设置事件订阅
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        
        // 注册事件监听器
        starter.registerListenerHost(new ListenerHost(){
            @EventReceiver
            public void onMessage(MessageChannelReceiveEvent event) {
                // 回复消息
                MessageChain chain = new MessageChain();
                chain.text("Hello, ").at(event.getSender().getId());
                event.send(chain);
            }
        });
        
        // 启动机器人
        starter.run();
    }
}
```

## API 文档

详细 API 文档请参考源码中的 Javadoc 注释。

## 常见问题

1. **如何选择公域还是私域机器人？**
   - 公域机器人只能接收 @机器人的消息
   - 私域机器人可以接收频道内的所有消息

2. **如何处理 Q 群消息？**
   - 需要在构造 [Starter](./src/main/java/io/github/kloping/qqbot/Starter.java) 时提供 secret 参数
   - 使用 `Intents.GROUP_INTENTS` 订阅群聊事件

3. **如何调试机器人？**
   - 可以使用沙箱环境进行测试
   - 查看控制台日志输出

## 贡献

欢迎提交 Issue 和 Pull Request 来改进这个 SDK。

## 许可证

本项目基于 MIT 许可证发布。