## QQ频道机器人 Java SDK 非官方

JAVA SDK主要基于[基础 API (opens new window)](https://bot.q.qq.com/wiki/develop/api/)封装，提供给用户一种简单、高效的使用方式。



可用于 Java 8+

Maven

```xml
<!-- https://mvnrepository.com/artifact/io.github.kloping/bot-qqpd-java -->
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.1</version>
</dependency>
```

## 使用前提

1. 到https://q.qq.com/ 申请机器人 获得BotAppID（开发者ID） 和 机器人令牌（token）

2. 发布审核

## 使用示例

发送消息

```java
         Starter starter = new Starter("BotAppID", "token") {
            @Override
            protected void after() {
                super.after();
                StarterApplication.Setting.INSTANCE.getContextManager().append("1073742336", INTENTS_ID);
            }

            @Override
            protected void wssWork() {
                super.wssWork();
            }
        };
        
        starter.run();
        GuildBase base0 = starter.getContextManager().getContextEntity(GuildBase.class);
        MessageBase base = starter.getContextManager().getContextEntity(MessageBase.class);
        Guild[] guilds = base0.getGuilds();
        Channel[] channels = base0.getChannels(guilds[0].getId());
        Channel channel = channels[0];
        for (Channel channel1 : channels) {
            if (channel1.getName().equals("游戏大厅")) {
                channel = channel1;
            }
        }
        Thread.sleep(1000);
        System.out.println(base.send(channel.getId(), new AbstractMap.SimpleEntry<>("content", "测试消息")));
        System.out.println(channel.send("1"));
```





使用方式参考查看 [test](https://github.com/Kloping/qqpd-bot-java/tree/master/src/test/java)





该sdk尚在编写中..
