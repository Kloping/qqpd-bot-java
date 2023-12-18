## QQ频道机器人 Java SDK 非官方 文档
_**待完善..**_
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

在配置好项目依赖后 即可使用

[maven仓库](https://repo1.maven.org/maven2/io/github/kloping/bot-qqpd-java/)

#### 启动方式

```java
//启动类新建
Starter starter = new Starter("appid", "token");
// 私域推荐Intents.PRIVATE_INTENTS 公域机器人推荐 Intents.PUBLIC_INTENTS
starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
//启动
starter.run();
```
#### 事件注册

```java
starter.registerListenerHost(new ListenerHost(){
    @Override
    public void handleException(Throwable e){
    }
    
    //必须要有该注解 否则将不注册
    @EventReceiver
    public void onEvent(MessageChannelReceiveEvent event){
        event.send("Hello World!");
    }
});
```

分为板块

- [事件 event](event.md)
- [消息 message](message.md)
- [动作 action](action.md)

<hr>

> 日志设置

```java
public class LogDemo {
    
    private static final SimpleDateFormat dfn = new SimpleDateFormat("/yyyy-MM-dd");

    public static String getLogFile() {
        File file = new File(String.format("./logs/%s.log", dfn.format(new Date())));
        file.getParentFile().mkdirs();
        return file.getAbsolutePath();
    }

    public static void main(String[] args) {
        //设置控制台输出日志等级
        starter.APPLICATION.logger.setLogLevel(0);
        //设置日志输出文件 不受控制台日志输出等级影响
        starter.APPLICATION.logger.setOutFile(getLogFile());
    }
}
```
<hr>

> 自定义消息发送 
- 通过http请求达到想要的目的获取bot请求必要的请求头方式

```java
//方法必须在start.run 之后
//频道发送请求必要请求头
starter.APPLICATION.INSTANCE.getContextManager().getContextEntity(Start0.class).getHeaders()
//q群发送请求必要请求头
starter.APPLICATION.INSTANCE.getContextManager().getContextEntity(Start0.class).getHeaders()
```