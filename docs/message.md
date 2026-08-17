### 消息文档

- [RawMessage](../src/main/java/io/github/kloping/qqbot/entities/qqpd/message/RawMessage.java) 所有消息顶级父类
- [DirectMessage](../src/main/java/io/github/kloping/qqbot/entities/qqpd/message/DirectMessage.java) 私信消息
  - 从  [MessageDirectReceiveEvent](../src/main/java/io/github/kloping/qqbot/api/message/MessageDirectReceiveEvent.java)
    私信消息接收事件中获取
  - [可发送私信](../src/main/java/io/github/kloping/qqbot/api/DirectSender.java)


- [MessageChain](../src/main/java/io/github/kloping/qqbot/entities/ex/msg/MessageChain.java)
  - MessageEvent 获取的默认消息类型

| 方法      |                         参数 |    作用     |
|:--------|---------------------------:|:---------:|
| forEach | Consumer<? super SendAble> |   遍历消息    |
| get     |                        int | 获取指定索引的消息 |
| size    |                            |  获取消息数量   |

- 可被发送的 SendAble
  - At
  - AtAll
  - Emoji
  - Image
  - MessageChain
  - MessagePre
  - PlainText
  - Markdown
  - Keyboard
- 消息发送者 Sender
  - BaseMessageChannelReceiveEvent
  - BaseMessageContainsAtEvent
  - BaseMessageDeleteEvent
  - BaseMessageDirectReceiveEvent
  - BaseMessageEvent
  - BaseMessageReactionEvent
  - BaseMessageReceiveEvent
  - Channel
  - DirectMessage
  - DirectSender
  - Dms
  - MessageChannelReceiveEvent
  - MessageContainsAtEvent
  - MessageDeleteEvent
  - MessageDirectReceiveEvent
  - MessageReceiveEvent
  - RawMessage
  - SenderAndCidMidGetter

消息构造:

- [MessagePreBuilder](../src/main/java/io/github/kloping/qqbot/entities/ex/MessagePreBuilder.java) 默认的消息构造器
  可用于发送大部分简单常用消息
- [MessageAsyncBuilder](../src/main/java/io/github/kloping/qqbot/entities/ex/MessageAsyncBuilder.java) 异步消息构造器
  用于发送部分多图消息

       //示例
       MessageAsyncBuilder builder = new MessageAsyncBuilder()
                        .text("你好")
                        .image("url")
                        .image(new byte[0])
                        .at("1")
                        .append(Emoji.emm);
       event.send(builder.build());
-

<hr>

<details>
<summary> <a href="../src/main/java/io/github/kloping/qqbot/entities/qqpd/data/Emoji.java">Emoji全部</a> </summary> 

source: [Emoji 列表](https://bot.q.qq.com/wiki/develop/api/openapi/emoji/model.html#emoji-%E5%88%97%E8%A1%A8)

<table><thead><tr><th>表情类型</th> <th>表情ID</th> <th>表情含义</th></tr></thead> <tbody><tr><td>1</td> <td>4</td> <td>得意</td></tr> <tr><td>1</td> <td>5</td> <td>流泪</td></tr> <tr><td>1</td> <td>8</td> <td>睡</td></tr> <tr><td>1</td> <td>9</td> <td>大哭</td></tr> <tr><td>1</td> <td>10</td> <td>尴尬</td></tr> <tr><td>1</td> <td>12</td> <td>调皮</td></tr> <tr><td>1</td> <td>14</td> <td>微笑</td></tr> <tr><td>1</td> <td>16</td> <td>酷</td></tr> <tr><td>1</td> <td>21</td> <td>可爱</td></tr> <tr><td>1</td> <td>23</td> <td>傲慢</td></tr> <tr><td>1</td> <td>24</td> <td>饥饿</td></tr> <tr><td>1</td> <td>25</td> <td>困</td></tr> <tr><td>1</td> <td>26</td> <td>惊恐</td></tr> <tr><td>1</td> <td>27</td> <td>流汗</td></tr> <tr><td>1</td> <td>28</td> <td>憨笑</td></tr> <tr><td>1</td> <td>29</td> <td>悠闲</td></tr> <tr><td>1</td> <td>30</td> <td>奋斗</td></tr> <tr><td>1</td> <td>32</td> <td>疑问</td></tr> <tr><td>1</td> <td>33</td> <td>嘘</td></tr> <tr><td>1</td> <td>34</td> <td>晕</td></tr> <tr><td>1</td> <td>38</td> <td>敲打</td></tr> <tr><td>1</td> <td>39</td> <td>再见</td></tr> <tr><td>1</td> <td>41</td> <td>发抖</td></tr> <tr><td>1</td> <td>42</td> <td>爱情</td></tr> <tr><td>1</td> <td>43</td> <td>跳跳</td></tr> <tr><td>1</td> <td>49</td> <td>拥抱</td></tr> <tr><td>1</td> <td>53</td> <td>蛋糕</td></tr> <tr><td>1</td> <td>60</td> <td>咖啡</td></tr> <tr><td>1</td> <td>63</td> <td>玫瑰</td></tr> <tr><td>1</td> <td>66</td> <td>爱心</td></tr> <tr><td>1</td> <td>74</td> <td>太阳</td></tr> <tr><td>1</td> <td>75</td> <td>月亮</td></tr> <tr><td>1</td> <td>76</td> <td>赞</td></tr> <tr><td>1</td> <td>78</td> <td>握手</td></tr> <tr><td>1</td> <td>79</td> <td>胜利</td></tr> <tr><td>1</td> <td>85</td> <td>飞吻</td></tr> <tr><td>1</td> <td>89</td> <td>西瓜</td></tr> <tr><td>1</td> <td>96</td> <td>冷汗</td></tr> <tr><td>1</td> <td>97</td> <td>擦汗</td></tr> <tr><td>1</td> <td>98</td> <td>抠鼻</td></tr> <tr><td>1</td> <td>99</td> <td>鼓掌</td></tr> <tr><td>1</td> <td>100</td> <td>糗大了</td></tr> <tr><td>1</td> <td>101</td> <td>坏笑</td></tr> <tr><td>1</td> <td>102</td> <td>左哼哼</td></tr> <tr><td>1</td> <td>103</td> <td>右哼哼</td></tr> <tr><td>1</td> <td>104</td> <td>哈欠</td></tr> <tr><td>1</td> <td>106</td> <td>委屈</td></tr> <tr><td>1</td> <td>109</td> <td>左亲亲</td></tr> <tr><td>1</td> <td>111</td> <td>可怜</td></tr> <tr><td>1</td> <td>116</td> <td>示爱</td></tr> <tr><td>1</td> <td>118</td> <td>抱拳</td></tr> <tr><td>1</td> <td>120</td> <td>拳头</td></tr> <tr><td>1</td> <td>122</td> <td>爱你</td></tr> <tr><td>1</td> <td>123</td> <td>NO</td></tr> <tr><td>1</td> <td>124</td> <td>OK</td></tr> <tr><td>1</td> <td>125</td> <td>转圈</td></tr> <tr><td>1</td> <td>129</td> <td>挥手</td></tr> <tr><td>1</td> <td>144</td> <td>喝彩</td></tr> <tr><td>1</td> <td>147</td> <td>棒棒糖</td></tr> <tr><td>1</td> <td>171</td> <td>茶</td></tr> <tr><td>1</td> <td>173</td> <td>泪奔</td></tr> <tr><td>1</td> <td>174</td> <td>无奈</td></tr> <tr><td>1</td> <td>175</td> <td>卖萌</td></tr> <tr><td>1</td> <td>176</td> <td>小纠结</td></tr> <tr><td>1</td> <td>179</td> <td>doge</td></tr> <tr><td>1</td> <td>180</td> <td>惊喜</td></tr> <tr><td>1</td> <td>181</td> <td>骚扰</td></tr> <tr><td>1</td> <td>182</td> <td>笑哭</td></tr> <tr><td>1</td> <td>183</td> <td>我最美</td></tr> <tr><td>1</td> <td>201</td> <td>点赞</td></tr> <tr><td>1</td> <td>203</td> <td>托脸</td></tr> <tr><td>1</td> <td>212</td> <td>托腮</td></tr> <tr><td>1</td> <td>214</td> <td>啵啵</td></tr> <tr><td>1</td> <td>219</td> <td>蹭一蹭</td></tr> <tr><td>1</td> <td>222</td> <td>抱抱</td></tr> <tr><td>1</td> <td>227</td> <td>拍手</td></tr> <tr><td>1</td> <td>232</td> <td>佛系</td></tr> <tr><td>1</td> <td>240</td> <td>喷脸</td></tr> <tr><td>1</td> <td>243</td> <td>甩头</td></tr> <tr><td>1</td> <td>246</td> <td>加油抱抱</td></tr> <tr><td>1</td> <td>262</td> <td>脑阔疼</td></tr> <tr><td>1</td> <td>264</td> <td>捂脸</td></tr> <tr><td>1</td> <td>265</td> <td>辣眼睛</td></tr> <tr><td>1</td> <td>266</td> <td>哦哟</td></tr> <tr><td>1</td> <td>267</td> <td>头秃</td></tr> <tr><td>1</td> <td>268</td> <td>问号脸</td></tr> <tr><td>1</td> <td>269</td> <td>暗中观察</td></tr> <tr><td>1</td> <td>270</td> <td>emm</td></tr> <tr><td>1</td> <td>271</td> <td>吃瓜</td></tr> <tr><td>1</td> <td>272</td> <td>呵呵哒</td></tr> <tr><td>1</td> <td>273</td> <td>我酸了</td></tr> <tr><td>1</td> <td>277</td> <td>汪汪</td></tr> <tr><td>1</td> <td>278</td> <td>汗</td></tr> <tr><td>1</td> <td>281</td> <td>无眼笑</td></tr> <tr><td>1</td> <td>282</td> <td>敬礼</td></tr> <tr><td>1</td> <td>284</td> <td>面无表情</td></tr> <tr><td>1</td> <td>285</td> <td>摸鱼</td></tr> <tr><td>1</td> <td>287</td> <td>哦</td></tr> <tr><td>1</td> <td>289</td> <td>睁眼</td></tr> <tr><td>1</td> <td>290</td> <td>敲开心</td></tr> <tr><td>1</td> <td>293</td> <td>摸锦鲤</td></tr> <tr><td>1</td> <td>294</td> <td>期待</td></tr> <tr><td>1</td> <td>297</td> <td>拜谢</td></tr> <tr><td>1</td> <td>298</td> <td>元宝</td></tr> <tr><td>1</td> <td>299</td> <td>牛啊</td></tr> <tr><td>1</td> <td>305</td> <td>右亲亲</td></tr> <tr><td>1</td> <td>306</td> <td>牛气冲天</td></tr> <tr><td>1</td> <td>307</td> <td>喵喵</td></tr> <tr><td>1</td> <td>314</td> <td>仔细分析</td></tr> <tr><td>1</td> <td>315</td> <td>加油</td></tr> <tr><td>1</td> <td>318</td> <td>崇拜</td></tr> <tr><td>1</td> <td>319</td> <td>比心</td></tr> <tr><td>1</td> <td>320</td> <td>庆祝</td></tr> <tr><td>1</td> <td>322</td> <td>拒绝</td></tr> <tr><td>1</td> <td>324</td> <td>吃糖</td></tr> <tr><td>1</td> <td>326</td> <td>生气</td></tr> <tr><td>2</td> <td>9728</td> <td>☀ 晴天</td></tr> <tr><td>2</td> <td>9749</td> <td>☕ 咖啡</td></tr> <tr><td>2</td> <td>9786</td> <td>☺ 可爱</td></tr> <tr><td>2</td> <td>10024</td> <td>✨ 闪光</td></tr> <tr><td>2</td> <td>10060</td> <td>❌ 错误</td></tr> <tr><td>2</td> <td>10068</td> <td>❔ 问号</td></tr> <tr><td>2</td> <td>127801</td> <td>🌹 玫瑰</td></tr> <tr><td>2</td> <td>127817</td> <td>🍉 西瓜</td></tr> <tr><td>2</td> <td>127822</td> <td>🍎 苹果</td></tr> <tr><td>2</td> <td>127827</td> <td>🍓 草莓</td></tr> <tr><td>2</td> <td>127836</td> <td>🍜 拉面</td></tr> <tr><td>2</td> <td>127838</td> <td>🍞 面包</td></tr> <tr><td>2</td> <td>127847</td> <td>🍧 刨冰</td></tr> <tr><td>2</td> <td>127866</td> <td>🍺 啤酒</td></tr> <tr><td>2</td> <td>127867</td> <td>🍻 干杯</td></tr> <tr><td>2</td> <td>127881</td> <td>🎉 庆祝</td></tr> <tr><td>2</td> <td>128027</td> <td>🐛 虫</td></tr> <tr><td>2</td> <td>128046</td> <td>🐮 牛</td></tr> <tr><td>2</td> <td>128051</td> <td>🐳 鲸鱼</td></tr> <tr><td>2</td> <td>128053</td> <td>🐵 猴</td></tr> <tr><td>2</td> <td>128074</td> <td>👊 拳头</td></tr> <tr><td>2</td> <td>128076</td> <td>👌 好的</td></tr> <tr><td>2</td> <td>128077</td> <td>👍 厉害</td></tr> <tr><td>2</td> <td>128079</td> <td>👏 鼓掌</td></tr> <tr><td>2</td> <td>128089</td> <td>👙 内衣</td></tr> <tr><td>2</td> <td>128102</td> <td>👦 男孩</td></tr> <tr><td>2</td> <td>128104</td> <td>👨 爸爸</td></tr> <tr><td>2</td> <td>128147</td> <td>💓 爱心</td></tr> <tr><td>2</td> <td>128157</td> <td>💝 礼物</td></tr> <tr><td>2</td> <td>128164</td> <td>💤 睡觉</td></tr> <tr><td>2</td> <td>128166</td> <td>💦 水</td></tr> <tr><td>2</td> <td>128168</td> <td>💨 吹气</td></tr> <tr><td>2</td> <td>128170</td> <td>💪 肌肉</td></tr> <tr><td>2</td> <td>128235</td> <td>📫 邮箱</td></tr> <tr><td>2</td> <td>128293</td> <td>🔥 火</td></tr> <tr><td>2</td> <td>128513</td> <td>😁 呲牙</td></tr> <tr><td>2</td> <td>128514</td> <td>😂 激动</td></tr> <tr><td>2</td> <td>128516</td> <td>😄 高兴</td></tr> <tr><td>2</td> <td>128522</td> <td>😊 嘿嘿</td></tr> <tr><td>2</td> <td>128524</td> <td>😌 羞涩</td></tr> <tr><td>2</td> <td>128527</td> <td>😏 哼哼</td></tr> <tr><td>2</td> <td>128530</td> <td>😒 不屑</td></tr> <tr><td>2</td> <td>128531</td> <td>😓 汗</td></tr> <tr><td>2</td> <td>128532</td> <td>😔 失落</td></tr> <tr><td>2</td> <td>128536</td> <td>😘 飞吻</td></tr> <tr><td>2</td> <td>128538</td> <td>😚 亲亲</td></tr> <tr><td>2</td> <td>128540</td> <td>😜 淘气</td></tr> <tr><td>2</td> <td>128541</td> <td>😝 吐舌</td></tr> <tr><td>2</td> <td>128557</td> <td>😭 大哭</td></tr> <tr><td>2</td> <td>128560</td> <td>😰 紧张</td></tr> <tr><td>2</td> <td>128563</td> <td>😳 瞪眼</td></tr></tbody></table>

</details> 

***

### 关于markdown与按钮

```java
public class DemoMarkdownSend{
    public static void main(String[] args) {
        //有模版 已经弃用
      //event.send(new Markdown("custom_template_id")
      //        //申请的模板 参数填充
      //        .addParam("key", "value")
      //        .setKeyboard("id"));
      // 原生发送
      Markdown markdown = new Markdown();
      markdown.setContent("### 测试");
      event.send(markdown);
    }
}
```

- ~~[md模板申请 与 按钮组件申请](https://q.qq.com/qqbot/#/developer/advanced-features)~~ 
- ~~[按钮配置参考](https://bot.q.qq.com/wiki/develop/api-v2/server-inter/message/trans/msg-btn.html#%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E5%8D%8F%E8%AE%AE)~~
- ~~[md配置参考](https://bot.q.qq.com/wiki/develop/api-v2/server-inter/message/type/markdown.html#%E6%94%AF%E6%8C%81%E6%A0%BC%E5%BC%8F)~~
> 当前仅支持群聊发送md 按钮组件不能单独发送 须与md消息组合发送

### 按钮发送
```java
/**
 * 发送案例
 */
public class DemoButtonGroupSend{
    public static void main(String[] args) {
      MessageAsyncBuilder builder = new MessageAsyncBuilder();
      Markdown markdown = Markdown.ofText("### 测试markdown大标题");
      builder.append(markdown); // event.send(builder.build()); //也可单独发送

      Keyboard.KeyboardBuilder keyboardBuilder = new Keyboard.KeyboardBuilder();

      Keyboard keyboard =
              keyboardBuilder
                      .addRow()//添加一个行
                        .addButton()//在这个行添加一个按钮
                          .setLabel("测试标题")//设置这个按钮的标题
                          .setVisitedLabel("测试标题.")
                          .setStyle(1)
                          .setActionData("123")
                          .setActionEnter(false)
                          .setActionReply(true)
                          .setActionType(2)
                      .build()//构建按钮
                      .build()//构建行
              .build();//构建按钮组
      builder.append(keyboard); // event.send(keyboard); //也可单独发送
      event.send(builder.build());
    }
}

```