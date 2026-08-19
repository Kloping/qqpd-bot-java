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
- **[V2群相关](v2.md)**

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
> step2 发送消息   tips: MessageEvent exts Sender
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

### 其他设置项

#### 日志设置

SDK 全部日志通过 SLF4J 输出，源码使用类级 `@Slf4j` 生成的 `log.trace`、`log.debug`、`log.info`、`log.warn` 和 `log.error`。日志等级、格式、文件输出和颜色由宿主日志实现管理。

旧版 `LoggerImpl`、`starter.APPLICATION.logger`、`Config.logLevel`、`logToFile` 与 `logFileDir` 已删除，不能再用于配置日志。

#### 独立运行

直接运行 SDK 时，使用可选的 Logback provider 输出到标准输出。默认等级为 `INFO`，默认控制台格式为：

```text
%clr([%thread]){blue} %clr(%-32.32logger{48}){magenta} %clr(%d{yyyy-MM-dd HH:mm:ss}){red} %clr(%-5p): %clr(%msg){light_green}%n
```

可使用 JVM 参数覆盖默认设置：

```text
-Dqqbot.logging.level=DEBUG
-Dqqbot.logging.color=false
-Dqqbot.logging.pattern="[%thread] %-32.32logger{32} %d{yyyy-MM-dd HH:mm:ss} %-5p: %msg%n"
```

`qqbot.logging.level` 支持 `TRACE`、`DEBUG`、`INFO`、`WARN`、`ERROR`、`OFF` 等 Logback 标准等级。设置 `qqbot.logging.color=false` 可禁用 ANSI 颜色。未指定颜色的 `%clr(...)` 会按级别着色：`ERROR` 红、`WARN` 黄、`INFO` 绿、`DEBUG` 青。自定义模式支持 `black`、`red`、`green`、`light_green`、`yellow`、`blue`、`magenta`、`cyan` 和 `white`。

#### Spring Boot 项目中的日志配置

检测到 Spring Boot 后，SDK 不会创建或修改 appender，也不会修改日志等级、颜色或输出格式。SDK 的默认 Logback provider 为 Maven 可选依赖，不会传递到 Spring Boot 用户项目。

请使用 Spring Boot 标准日志配置：

```yaml
logging:
  level:
    io.github.kloping.qqbot: DEBUG
  pattern:
    console: "%clr([%thread]){blue} %clr(%-32.32logger{48}){magenta} %clr(%d{yyyy-MM-dd HH:mm:ss}){red} %clr(%-5p): %clr(%msg){light_green}%n"
```

也可在 `logback-spring.xml` 中配置 `io.github.kloping.qqbot` logger、控制台 appender 和文件 appender。Spring Boot 的配置优先，且不会被 SDK 覆盖。
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

//其中主动发送qq群

    starter.registerListenerHost(new ListenerHost() {
        @EventReceiver
        public void onEvent(ConnectedEvent event) {
            V2MsgData data = new V2MsgData().setContent("测试主动消息");
            starter.getBot().groupBaseV2.send("groupOpenId", data.toString(), SEND_MESSAGE_HEADERS);
        }
    });
![img.png](./imgs/img.png)

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
