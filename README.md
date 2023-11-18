![ComWeChatBotClient](https://socialify.git.ci/Kloping/qqpd-bot-java/image?description=1&font=Inter&name=1&pattern=Circuit%20Board&theme=Auto)
<p align="center">
    <a href="https://github.com/Kloping/qqpd-bot-java/blob/main/LICENSE"><img src="https://img.shields.io/github/license/Kloping/qqpd-bot-java" alt="License"></a>
    <a href="https://github.com/Kloping/qqpd-bot-java/releases"><img src="https://img.shields.io/github/v/release/Kloping/qqpd-bot-java?color=blueviolet&include_prereleases" alt="release"></a>
</p>

## QQ频道机器人 Java SDK

> 非官方 可用于 Java 8+

Java SDK主要基于[基础 API (opens new window)](https://bot.q.qq.com/wiki/develop/api/)封装，提供给用户一种简单、高效的使用方式。

Maven

```xml
<!-- https://repo1.maven.org/maven2/io/github/kloping/bot-qqpd-java/ -->
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.5.0-Alpha2</version>
</dependency>
```

### 使用前提

1. 到https://q.qq.com/ 申请机器人 获得Bot 开发者ID(appid) 和 机器人令牌(token)

~~2. 发布审核 发布后为公域~~

### [开发文档](./docs) / [q群使用说明](./docs/v2.md)

### 使用示例

启动方式

```java 
    Starter starter=new Starter("appid","token");
    starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
    starter.run();
```

> #### V1.4+ 注册监听器主机方式 [荐]

```java
starter.registerListenerHost(new ListenerHost(){
    @Override
    public void handleException(Throwable e){
    }

    @EventReceiver
    public void onEvent(MessageChannelReceiveEvent event){
        event.send("测试");
    }

    @EventReceiver 
    public void onEvent(MessageDirectReceiveEvent event){
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
import io.github.kloping.qqbot.impl.EventReceiver;
import io.github.kloping.qqbot.impl.ListenerHost;
```

更多使用方式参考查看 [test](./src/test/java)

SDK尚在完善中...