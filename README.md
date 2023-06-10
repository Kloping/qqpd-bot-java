## QQ频道机器人 Java SDK 非官方

JAVA SDK主要基于[基础 API (opens new window)](https://bot.q.qq.com/wiki/develop/api/)封装，提供给用户一种简单、高效的使用方式。

可用于 Java 8+

Maven

```xml
<!-- https://mvnrepository.com/artifact/io.github.kloping/bot-qqpd-java -->
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.4.8-M1</version>
</dependency>
```

#### TIPS: 目前仅支持单bot运行

### 使用前提

1. 到https://q.qq.com/ 申请机器人 获得BotAppID（开发者ID） 和 机器人令牌（token）

2. 发布审核

### [临时文档](./docs)

### 使用示例

启动方式

```java 
    Starter starter=new Starter("appid","token");
    starter.getConfig().setIntents(intents);
    starter.run();
```

#### V1.4+ 注册监听器主机方式 [荐]

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

#### V1.4.6

事件订阅 默认的事件订阅 不会接收消息事件

需要确定自己的机器人是公域还是私域

来确定 需要 **[设置订阅](src/test/java/test_Intents.java)** 的 **[事件类型](src/main/java/io/github/kloping/qqbot/api/Intents.java)**

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

导入指引

```java

```

使用方式参考查看 [test](./src/test/java)

该sdk尚在编写中..
