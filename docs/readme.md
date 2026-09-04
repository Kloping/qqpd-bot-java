## QQ官方机器人 JavaSDK 开发文档

<hr>

### 包目录说明:

- [api](../src/main/java/io/github/kloping/qqbot/api) 对于整个SDK需要实现的功能的定义并无实现
- [entities](../src/main/java/io/github/kloping/qqbot/entities) QQ频道官方文档数据格式的重写
- [http](../src/main/java/io/github/kloping/qqbot/http) SDK使用到的http请求工具的定义
- [impl](../src/main/java/io/github/kloping/qqbot/impl) 对于SDK api的基本实现
- [network](../src/main/java/io/github/kloping/qqbot/network) SDK的网络层
- [utils](../src/main/java/io/github/kloping/qqbot/utils) sdk 的工具类
- [Resource.java](../src/main/java/io/github/kloping/qqbot/Resource.java) 公用资源类
- [Starter.java](../src/main/java/io/github/kloping/qqbot/Starter.java) 启动类
- [Start0](../src/main/java/io/github/kloping/qqbot/Start0.java) 启动类附属

<hr>

#### 相关部分文档指引

- **[事件 event](event.md)**
- **[消息发送 message](message.md)**
- **[网络相关配置](network.md)**
- **[日志相关配置](logging.md)**

<hr>

在配置好项目依赖后 即可使用

> [maven仓库](https://repo1.maven.org/maven2/io/github/kloping/bot-qqpd-java/)

#### 启动流程

> step-1

 登录q.qq开发者平台获得 appid 和 secret 等参数

> step0 启动程序
```java
Starter starter = new Starter("appid", "secret");
// 私域推荐Intents.PRIVATE_INTENTS 公域机器人推荐 Intents.PUBLIC_INTENTS
starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
// starter.getConfig().setCode(Intents.PUBLIC_INTENTS.getCode());
// 切换沙箱与正式环境
// starter.getConfig().sandbox();
// 启动
starter.run();
```
#### 事件注册 
> step1 接收事件 tips:方法中参数为Event任一子类或实现
```java
starter.registerListenerHost(new ListenerHost(){
    //必须要有该注解 否则将不注册
    @EventReceiver
    public void onEvent(MessageEvent event){
        event.send("Hello World!");
    }
});
```

### 消息发送
> step2 发送消息   tips: MessageEvent extends Sender
- 发送文本`sender.send("文本");`
- 发送图片
```java
//step1 构造Image
Image image = null;
//图片链接构造
image = new Image(url);
//bytes数据构造
image = new Image(bytes);        
//step2 send
sender.send(image);
```
- 发送 markdown

      event.send(new Markdown("custom_template_id")
        //申请的模板 参数填充
        .addParam("key", "value")
        //可选 设置按钮模板 
        .setKeyboard("id"));   


<hr>

### 此处为qq机器人在群里中使用的说明

v2 使用条件(必须) 当前(23.11.16)

- 机器人必须为公域
- 必须有 **_[在QQ群配置](https://q.qq.com/qqbot/#/developer/sandbox)_**  的权限
- 配置完成后，群主可从沙箱群“设置-群机器人”打开机器人列表页添加测试机器人

<hr>

> 以下为 必要启动代码


<details>
<summary>展开查看</summary>

```java
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.MessageAsyncBuilder;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.impl.BaseConnectedEvent;
import io.github.kloping.qqbot.impl.EventReceiver;
import io.github.kloping.qqbot.impl.ListenerHost;

public class demo {
    public static void main(String[] args) {
        //==============================================必要↓↓↓↓↓↓↓
        Starter starter = new Starter("appid", "secret");
        //===================================公域推荐订阅===============↓群聊/好友 事件订阅
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS);
        starter.run();
        starter.registerListenerHost(new ListenerHost() {

            @EventReceiver
            public void onMessage(MessageChannelReceiveEvent event) {
                MessageAsyncBuilder builder = new MessageAsyncBuilder();
                builder.append("测试发图!");
                builder.append(new Image("https://kloping.top/icon.jpg"));
                builder.append(Emoji.K歌);
                event.send(builder.build());
            }

            /**
             * 因为是公域 所以仅当bot被at时才能触发事件
             * @param event
             */
            @EventReceiver
            public void onMessage(GroupMessageEvent event) {
                MessageAsyncBuilder builder = new MessageAsyncBuilder();
                builder.append("测试发图!");
                //目前仅支持 以url发送图片 https://bot.q.qq.com/wiki/develop/api-231017/server-inter/message/send-receive/rich-text-media.html#%E5%8F%91%E9%80%81%E5%88%B0%E7%BE%A4%E8%81%8A
                builder.append(new Image("https://kloping.top/icon.jpg"));
                builder.append(Emoji.K歌);
                event.sendMessage(builder.build());
            }
        });
    }
}
```

</details>


## v1.5.4-R2 群管理 API

以下方法通过 `Group` 对象调用。事件中可直接使用 `GroupEvent#getGroup()` 或
`GroupMessageEvent#getSubject()` 获取当前群对象。

```java
import io.github.kloping.qqbot.api.v2.GroupEvent;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.Mute;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApproval;

public void handle(GroupEvent event) {
    Group group = event.getGroup();
    String cursor = null;
    String memberOpenid = "member-openid";

    // 获取群基本信息
    System.out.println(group.getInfo());
    // 获取机器人在群内的状态
    System.out.println(group.getBotState());
    // 入群申请列表：默认获取第一页；也可传 cursor 和 limit（最大 50）
    System.out.println(group.getJoinRequestList());
    System.out.println(group.getJoinRequestList(cursor, 50));
//    for (JoinRequest joinRequest : group.getJoinRequestList().getList()) {
//        joinRequest.ifApprove(p -> p.getAnswer().contains("没"));
//    }
    // 审批入群申请：op 可填写 approve / decline
    group.approvalJoinRequest(memberOpenid, new JoinApproval().setOp("approve"));
    // 查询群禁言状态
    System.out.println(group.getMuteSetting());
    // 设置成员禁言，时长单位为秒；解除禁言使用 group.unmuteMember(memberOpenid)
    group.muteMember(memberOpenid, Mute.Add, 60);
}
```

`getJoinRequestList(cursor, limit)` 返回 `JoinRequestList`，可通过 `getList()` 获取申请列表、
`getNextCursor()` 获取下一页游标。审批也可使用申请对象提供的 `ifApprove` / `ifDecline` 条件方法。
`muteMember` 支持 `Mute.Add`、`Mute.Update`，解除成员禁言请调用 `unmuteMember`。

群禁言也可以通过 `setMuteSetting(...)` 传入 `GroupMuteSetting.GroupMuteSettingRequest` 进行批量或细粒度设置。

<hr>


#### 自定义消息发送 
> 通过http请求达到想要的目的获取bot请求必要的请求头方式

```java
//方法必须在start.run 之后
//频道发送请求必要请求头
starter.APPLICATION.INSTANCE.getContextManager().getContextEntity(Start0.class).getHeaders()
//q群发送请求必要请求头
starter.APPLICATION.INSTANCE.getContextManager().getContextEntity(Start0.class).getHeaders()

```

#### 其中主动发送qq群

                 MessageAsyncBuilder builder = new MessageAsyncBuilder();
                builder.image("https://kloping.top/icon.jpg");
                builder.text("主动消息测试");
                bot.sendMessage("474905EE5C4F5199A1EC08E1C04BF077", builder.build());


### 依赖排斥

- v1.5.0-Beta7 在与com.alibaba.fastjson2:fastjson2
  同时引用时会产生大量空指针[#20](https://github.com/Kloping/qqpd-bot-java/issues/20)

<hr>

### 2025/4/18 v1.5.2-R1 + 

> 支持自定义websocket链接地址

对于webhook已开通且无法再进行websocket开发者而言 

可通过[webhook转websocket服务](https://github.com/DevOpen-Club/qbot-webhook-to-websocket)继续使用本项目

from [@NintyCat](https://github.com/NintyCat)

使用代码如下
```java
    //===========================你的自定义地址
    starter.getConfig().setWslink("wss://api.sgroup.qq.com/websocket");
    starter.getConfig().setWebSocketListener(new WebSocketListener() {
        @Override
        public boolean onMessage(WebSocketClient client, String msg) {
            Pack pack = GSON.fromJson(msg, Pack.class);
            // log or syso
            if (pack == null) {
                //TODO
            } else {
                for (OnPackReceive onPackReceive : starter.getWssWorker().getOnPackReceives()) {
                    if (onPackReceive instanceof AuthAndHeartbeat) continue;
                    onPackReceive.onReceive(pack);
                }
            }
            return false;
        }
    });
    start.run();
```

### 1.5.2-R2 

> 初步对webhook链接方式的支持
> 
> 使用该方式链接 其他配置参数将失效

在服务启动后需要将q.qq配置链接设置为 "https://you-domain/webhook0" 路径

> 确保https地址可访问到项目部署机器 #此类问题 不建议开issue

验证完成后确定配置


```java
  //设置webhook服务端口 默认为0时不开启webhook
  starter.getConfig().setWebhookport(81);
```
