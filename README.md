![qqpd-bot-java](https://socialify.git.ci/Kloping/qqpd-bot-java/image?description=1&descriptionEditable=QQ%E5%AE%98%E6%96%B9%E6%9C%BA%E5%99%A8%E4%BA%BA%20Java%2FJVM%2Fkotlin%20SDK%20QQ%20bot%20sdk%20qq%E6%9C%BA%E5%99%A8%E4%BA%BAsdk&font=Source%20Code%20Pro&forks=1&issues=1&language=1&name=1&owner=1&pattern=Overlapping%20Hexagons&pulls=1&stargazers=1&theme=Auto)

<p align="center">
    <a href="https://github.com/Kloping/qqpd-bot-java/blob/main/LICENSE"><img src="https://img.shields.io/github/license/Kloping/qqpd-bot-java" alt="License"></a>
    <a href="https://github.com/Kloping/qqpd-bot-java/releases"><img src="https://img.shields.io/github/v/release/Kloping/qqpd-bot-java?color=blueviolet&include_prereleases" alt="release"></a>
</p>

## QQ机器人 Java/JVM/kotlin SDK

> 非官方 可用于 Java 8+

Java SDK主要基于[基础 API (opens new window)](https://bot.q.qq.com/wiki/develop/api/)封装，提供给用户一种简单、高效的使用方式。

### ✨ 特性
- 支持QQ官方频道与Q群消息收发（私域/公域兼容）
- 开箱即用的消息类型（文本/图片/Markdown/按钮交互）
- 灵活的事件监听机制（`@EventReceiver`注解驱动）
- 多环境支持（沙箱/正式环境一键切换）
- 完善的HTTP API封装（频道管理、禁言、消息审核等）
- 支持Java 8+及Kotlin协程环境
- 支持`websocket`与`webhook`方式链接

Maven

```xml
<!-- https://repo1.maven.org/maven2/io/github/kloping/bot-qqpd-java/ -->
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.5.2-L3</version>
</dependency>
```

Gradle
 
    implementation 'io.github.kloping:bot-qqpd-java:1.5.2-L3'

### 使用前提

1. 到https://q.qq.com/ 申请机器人 获得Bot 开发者ID(appid) 和 机器人令牌(token)

~~2. 发布审核 发布后为公域~~

### [开发文档](./docs/readme.md) / [q群使用说明](./docs/v2.md)

### 使用示例

启动方式

```java 
    Starter starter = new Starter("appid","token");
    //如果使用q群 则 new Starter("appid", "token", "secret");
    starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
    // webhook 链接方式
    //starter.getConfig().setWebhookport(81);
    starter.run();
```

> #### V1.5.0-Beta7+ 注册监听器主机方式 [荐]

```java
starter.registerListenerHost(new ListenerHost(){
    @EventReceiver
    public void onEvent(MessageEvent event){
        event.send("测试通过");
    }
});
```

> #### V1.4.6
> 事件订阅 默认的事件订阅 不会接收消息事件 <br>
> 需要确定自己的机器人是公域还是私域 <br>
> 来确定 需要 **[设置订阅](src/test/java/test_Intents.java)** 的 **[事件类型](src/main/java/io/github/kloping/qqbot/api/Intents.java)**
```java
//单事件订阅方式
starter.getConfig().setCode(Intents.GUILD_MESSAGES.getCode());

//多事件订阅方式
starter.getConfig().setCode(Intents.START.and(Intents.GUILD_MESSAGES,Intents.DIRECT_MESSAGE));

// 公域机器人订阅推荐
starter.getConfig().setCode(Intents.PUBLIC_INTENTS.getCode());

// 私域机器人订阅推荐
starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
```

#### 导入指引

```java
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.impl.ListenerHost;
```

更多使用方式参考查看 [test](./src/test/java)
