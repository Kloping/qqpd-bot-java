# QQ 官方机器人 Java SDK

本文档介绍 SDK 的依赖、启动、事件监听、消息发送，以及 QQ 群机器人和自定义连接方式。

## 目录
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [相关文档](#相关文档)
- [事件监听](#事件监听)
- [消息发送](#消息发送)
- [QQ 群机器人](#qq-群机器人)
- [自定义消息与主动发送](#自定义消息与主动发送)
- [配置连接方式](#配置连接方式)
- [依赖兼容性](#依赖兼容性)

## 快速开始
### 1. 准备应用凭据
登录 [QQ 开放平台](https://q.qq.com/) 创建机器人，获取 `appid` 和 `secret`。

### 2. 引入 SDK
项目依赖配置完成后即可使用，也可以浏览 [Maven 仓库](https://repo1.maven.org/maven2/io/github/kloping/bot-qqpd-java/) 获取版本信息。

### 3. 创建并启动机器人
```java
Starter starter = new Starter("appid", "secret");
// 私域机器人：Intents.PRIVATE_INTENTS；公域机器人：Intents.PUBLIC_INTENTS
starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
// 切换沙箱环境：starter.getConfig().sandbox();
starter.run();
```

## 项目结构
- [api](../src/main/java/io/github/kloping/qqbot/api)：SDK 功能定义
- [entities](../src/main/java/io/github/kloping/qqbot/entities)：QQ 官方数据结构
- [http](../src/main/java/io/github/kloping/qqbot/http)：HTTP 请求工具
- [impl](../src/main/java/io/github/kloping/qqbot/impl)：API 基础实现
- [network](../src/main/java/io/github/kloping/qqbot/network)：网络连接层
- [utils](../src/main/java/io/github/kloping/qqbot/utils)：工具类
- [Resource.java](../src/main/java/io/github/kloping/qqbot/Resource.java)：公共资源
- [Starter.java](../src/main/java/io/github/kloping/qqbot/Starter.java)：启动入口
- [Start0.java](../src/main/java/io/github/kloping/qqbot/Start0.java)：启动附属组件

## 相关文档
- [事件（event）](event.md)
- [消息发送（message）](message.md)
- [网络配置（network）](network.md)
- [日志配置（logging）](logging.md)

## 事件监听
在 `Starter#run()` 后注册监听器。监听方法参数可以是任意 `Event` 子类或实现类，且必须添加 `@EventReceiver` 注解。
```java
starter.registerListenerHost(new ListenerHost() {
    @EventReceiver
    public void onEvent(MessageEvent event) {
        event.send("Hello World!");
    }
});
```

## 消息发送
`MessageEvent` 实现了发送能力，可直接调用 `send`。
### 文本
```java
sender.send("文本");
```
### 图片
```java
Image image = new Image(url);   // 图片 URL
// Image image = new Image(bytes); // 图片字节
sender.send(image);
```
### Markdown
```java
event.send(new Markdown("custom_template_id")
    .addParam("key", "value")
    .setKeyboard("id"));
```

## QQ 群机器人
### 使用条件
当前 v2 群机器人能力需要满足：
1. 机器人类型为公域机器人。
2. 已在[QQ群配置页面](https://q.qq.com/qqbot/#/developer/sandbox)完成权限配置。
3. 群主从沙箱群“设置 → 群机器人”页面添加测试机器人。

~~公域机器人通常只有在被 `@` 时才会触发群消息事件。~~

### 群管理 API（v1.5.4-R2）
以下方法通过 `Group` 对象调用。事件中可使用 `GroupEvent#getGroup()` 或 `GroupMessageEvent#getSubject()` 获取群对象。
```java
Group group = event.getGroup();
String memberOpenid = "member-openid";
System.out.println(group.getInfo());
System.out.println(group.getBotState());
System.out.println(group.getJoinRequestList());
System.out.println(group.getJoinRequestList(null, 50));
group.approvalJoinRequest(memberOpenid, new JoinApproval().setOp("approve"));
System.out.println(group.getMuteSetting());
group.muteMember(memberOpenid, Mute.Add, 60);
// 解除禁言：group.unmuteMember(memberOpenid)
```
`getJoinRequestList(cursor, limit)` 返回 `JoinRequestList`，可通过 `getList()` 获取申请列表、`getNextCursor()` 获取下一页游标。审批支持 `ifApprove` / `ifDecline` 条件方法；`muteMember` 支持 `Mute.Add`、`Mute.Update`，群禁言可通过 `setMuteSetting(...)` 设置。

### 主动发送群消息
```java
MessageAsyncBuilder builder = new MessageAsyncBuilder();
builder.image("https://kloping.top/icon.jpg");
builder.text("主动消息测试");
bot.sendMessage("474905EE5C4F5199A1EC08E1C04BF077", builder.build());
```

## 配置连接方式
### 自定义 WebSocket 地址（v1.5.2-R1 及以上）
已开通 Webhook 且无法继续使用 WebSocket 时，可使用[Webhook 转 WebSocket 服务](https://github.com/DevOpen-Club/qbot-webhook-to-websocket)。设置 `starter.getConfig().setWslink(...)` 后按项目提供的监听器处理消息。
### Webhook（v1.5.2-R2）
Webhook 模式启用后，其他连接配置将失效。将回调地址设置为 `https://your-domain/webhook0` ，确认部署机器可访问后，再设置服务端口：
```java
// 0 表示不开启 Webhook
starter.getConfig().setWebhookport(81);
```

## 依赖兼容性
`v1.5.0-Beta7` 与 `com.alibaba.fastjson2:fastjson2` 同时引用时可能产生空指针异常，详见 [Issue #20](https://github.com/Kloping/qqpd-bot-java/issues/20)。
