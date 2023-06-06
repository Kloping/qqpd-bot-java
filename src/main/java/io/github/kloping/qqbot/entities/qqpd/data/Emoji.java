package io.github.kloping.qqbot.entities.qqpd.data;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;

import java.util.LinkedList;
import java.util.List;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>表情ID，系统表情使用数字为ID，emoji使用emoji本身为id，参考 Emoji 列表</td></tr> <tr><td>type</td> <td>uint32</td> <td>表情类型 EmojiType</td></tr></tbody></table>
 *
 * @author github.kloping
 */
public class Emoji implements SendAble {
    public static final List<Emoji> VALUES = new LinkedList<>();
    public static final Emoji 得意 = new Emoji(1, 4, "得意");
    public static final Emoji 流泪 = new Emoji(1, 5, "流泪");
    public static final Emoji 睡 = new Emoji(1, 8, "睡");
    public static final Emoji 大哭 = new Emoji(1, 9, "大哭");
    public static final Emoji 尴尬 = new Emoji(1, 10, "尴尬");
    public static final Emoji 调皮 = new Emoji(1, 12, "调皮");
    public static final Emoji 微笑 = new Emoji(1, 14, "微笑");
    public static final Emoji 酷 = new Emoji(1, 16, "酷");
    public static final Emoji 可爱 = new Emoji(1, 21, "可爱");
    public static final Emoji 傲慢 = new Emoji(1, 23, "傲慢");
    public static final Emoji 饥饿 = new Emoji(1, 24, "饥饿");
    public static final Emoji 困 = new Emoji(1, 25, "困");
    public static final Emoji 惊恐 = new Emoji(1, 26, "惊恐");
    public static final Emoji 流汗 = new Emoji(1, 27, "流汗");
    public static final Emoji 憨笑 = new Emoji(1, 28, "憨笑");
    public static final Emoji 悠闲 = new Emoji(1, 29, "悠闲");
    public static final Emoji 奋斗 = new Emoji(1, 30, "奋斗");
    public static final Emoji 疑问 = new Emoji(1, 32, "疑问");
    public static final Emoji 嘘 = new Emoji(1, 33, "嘘");
    public static final Emoji 晕 = new Emoji(1, 34, "晕");
    public static final Emoji 敲打 = new Emoji(1, 38, "敲打");
    public static final Emoji 再见 = new Emoji(1, 39, "再见");
    public static final Emoji 发抖 = new Emoji(1, 41, "发抖");
    public static final Emoji 爱情 = new Emoji(1, 42, "爱情");
    public static final Emoji 跳跳 = new Emoji(1, 43, "跳跳");
    public static final Emoji 拥抱 = new Emoji(1, 49, "拥抱");
    public static final Emoji 蛋糕 = new Emoji(1, 53, "蛋糕");
    public static final Emoji 咖啡 = new Emoji(1, 60, "咖啡");
    public static final Emoji 玫瑰 = new Emoji(1, 63, "玫瑰");
    public static final Emoji 爱心 = new Emoji(1, 66, "爱心");
    public static final Emoji 太阳 = new Emoji(1, 74, "太阳");
    public static final Emoji 月亮 = new Emoji(1, 75, "月亮");
    public static final Emoji 赞 = new Emoji(1, 76, "赞");
    public static final Emoji 握手 = new Emoji(1, 78, "握手");
    public static final Emoji 胜利 = new Emoji(1, 79, "胜利");
    public static final Emoji 飞吻 = new Emoji(1, 85, "飞吻");
    public static final Emoji 西瓜 = new Emoji(1, 89, "西瓜");
    public static final Emoji 冷汗 = new Emoji(1, 96, "冷汗");
    public static final Emoji 擦汗 = new Emoji(1, 97, "擦汗");
    public static final Emoji 抠鼻 = new Emoji(1, 98, "抠鼻");
    public static final Emoji 鼓掌 = new Emoji(1, 99, "鼓掌");
    public static final Emoji 糗大了 = new Emoji(1, 100, "糗大了");
    public static final Emoji 坏笑 = new Emoji(1, 101, "坏笑");
    public static final Emoji 左哼哼 = new Emoji(1, 102, "左哼哼");
    public static final Emoji 右哼哼 = new Emoji(1, 103, "右哼哼");
    public static final Emoji 哈欠 = new Emoji(1, 104, "哈欠");
    public static final Emoji 委屈 = new Emoji(1, 106, "委屈");
    public static final Emoji 左亲亲 = new Emoji(1, 109, "左亲亲");
    public static final Emoji 可怜 = new Emoji(1, 111, "可怜");
    public static final Emoji 示爱 = new Emoji(1, 116, "示爱");
    public static final Emoji 抱拳 = new Emoji(1, 118, "抱拳");
    public static final Emoji 拳头 = new Emoji(1, 120, "拳头");
    public static final Emoji 爱你 = new Emoji(1, 122, "爱你");
    public static final Emoji NO = new Emoji(1, 123, "NO");
    public static final Emoji OK = new Emoji(1, 124, "OK");
    public static final Emoji 转圈 = new Emoji(1, 125, "转圈");
    public static final Emoji 挥手 = new Emoji(1, 129, "挥手");
    public static final Emoji 喝彩 = new Emoji(1, 144, "喝彩");
    public static final Emoji 棒棒糖 = new Emoji(1, 147, "棒棒糖");
    public static final Emoji 茶 = new Emoji(1, 171, "茶");
    public static final Emoji 泪奔 = new Emoji(1, 173, "泪奔");
    public static final Emoji 无奈 = new Emoji(1, 174, "无奈");
    public static final Emoji 卖萌 = new Emoji(1, 175, "卖萌");
    public static final Emoji 小纠结 = new Emoji(1, 176, "小纠结");
    public static final Emoji doge = new Emoji(1, 179, "doge");
    public static final Emoji 惊喜 = new Emoji(1, 180, "惊喜");
    public static final Emoji 骚扰 = new Emoji(1, 181, "骚扰");
    public static final Emoji 笑哭 = new Emoji(1, 182, "笑哭");
    public static final Emoji 我最美 = new Emoji(1, 183, "我最美");
    public static final Emoji 点赞 = new Emoji(1, 201, "点赞");
    public static final Emoji 托脸 = new Emoji(1, 203, "托脸");
    public static final Emoji 托腮 = new Emoji(1, 212, "托腮");
    public static final Emoji 啵啵 = new Emoji(1, 214, "啵啵");
    public static final Emoji 蹭一蹭 = new Emoji(1, 219, "蹭一蹭");
    public static final Emoji 抱抱 = new Emoji(1, 222, "抱抱");
    public static final Emoji 拍手 = new Emoji(1, 227, "拍手");
    public static final Emoji 佛系 = new Emoji(1, 232, "佛系");
    public static final Emoji 喷脸 = new Emoji(1, 240, "喷脸");
    public static final Emoji 甩头 = new Emoji(1, 243, "甩头");
    public static final Emoji 加油抱抱 = new Emoji(1, 246, "加油抱抱");
    public static final Emoji 脑阔疼 = new Emoji(1, 262, "脑阔疼");
    public static final Emoji 捂脸 = new Emoji(1, 264, "捂脸");
    public static final Emoji 辣眼睛 = new Emoji(1, 265, "辣眼睛");
    public static final Emoji 哦哟 = new Emoji(1, 266, "哦哟");
    public static final Emoji 头秃 = new Emoji(1, 267, "头秃");
    public static final Emoji 问号脸 = new Emoji(1, 268, "问号脸");
    public static final Emoji 暗中观察 = new Emoji(1, 269, "暗中观察");
    public static final Emoji emm = new Emoji(1, 270, "emm");
    public static final Emoji 吃瓜 = new Emoji(1, 271, "吃瓜");
    public static final Emoji 呵呵哒 = new Emoji(1, 272, "呵呵哒");
    public static final Emoji 我酸了 = new Emoji(1, 273, "我酸了");
    public static final Emoji 汪汪 = new Emoji(1, 277, "汪汪");
    public static final Emoji 汗 = new Emoji(1, 278, "汗");
    public static final Emoji 无眼笑 = new Emoji(1, 281, "无眼笑");
    public static final Emoji 敬礼 = new Emoji(1, 282, "敬礼");
    public static final Emoji 面无表情 = new Emoji(1, 284, "面无表情");
    public static final Emoji 摸鱼 = new Emoji(1, 285, "摸鱼");
    public static final Emoji 哦 = new Emoji(1, 287, "哦");
    public static final Emoji 睁眼 = new Emoji(1, 289, "睁眼");
    public static final Emoji 敲开心 = new Emoji(1, 290, "敲开心");
    public static final Emoji 摸锦鲤 = new Emoji(1, 293, "摸锦鲤");
    public static final Emoji 期待 = new Emoji(1, 294, "期待");
    public static final Emoji 拜谢 = new Emoji(1, 297, "拜谢");
    public static final Emoji 元宝 = new Emoji(1, 298, "元宝");
    public static final Emoji 牛啊 = new Emoji(1, 299, "牛啊");
    public static final Emoji 右亲亲 = new Emoji(1, 305, "右亲亲");
    public static final Emoji 牛气冲天 = new Emoji(1, 306, "牛气冲天");
    public static final Emoji 喵喵 = new Emoji(1, 307, "喵喵");
    public static final Emoji 仔细分析 = new Emoji(1, 314, "仔细分析");
    public static final Emoji 加油 = new Emoji(1, 315, "加油");
    public static final Emoji 崇拜 = new Emoji(1, 318, "崇拜");
    public static final Emoji 比心 = new Emoji(1, 319, "比心");
    public static final Emoji 庆祝 = new Emoji(1, 320, "庆祝");
    public static final Emoji 拒绝 = new Emoji(1, 322, "拒绝");
    public static final Emoji 吃糖 = new Emoji(1, 324, "吃糖");
    public static final Emoji 生气 = new Emoji(1, 326, "生气");
    public static final Emoji 晴天 = new Emoji(2, 9728, "☀ 晴天");
    public static final Emoji 咖啡2 = new Emoji(2, 9749, "☕ 咖啡");
    public static final Emoji 可爱2 = new Emoji(2, 9786, "☺ 可爱");
    public static final Emoji 闪光 = new Emoji(2, 10024, "✨ 闪光");
    public static final Emoji 错误 = new Emoji(2, 10060, "❌ 错误");
    public static final Emoji 问号 = new Emoji(2, 10068, "❔ 问号");
    public static final Emoji 玫瑰2 = new Emoji(2, 127801, "🌹 玫瑰");
    public static final Emoji 西瓜2 = new Emoji(2, 127817, "🍉 西瓜");
    public static final Emoji 苹果 = new Emoji(2, 127822, "🍎 苹果");
    public static final Emoji 草莓 = new Emoji(2, 127827, "🍓 草莓");
    public static final Emoji 拉面 = new Emoji(2, 127836, "🍜 拉面");
    public static final Emoji 面包 = new Emoji(2, 127838, "🍞 面包");
    public static final Emoji 刨冰 = new Emoji(2, 127847, "🍧 刨冰");
    public static final Emoji 啤酒 = new Emoji(2, 127866, "🍺 啤酒");
    public static final Emoji 干杯 = new Emoji(2, 127867, "🍻 干杯");
    public static final Emoji 庆祝2 = new Emoji(2, 127881, "🎉 庆祝");
    public static final Emoji 虫 = new Emoji(2, 128027, "🐛 虫");
    public static final Emoji 牛 = new Emoji(2, 128046, "🐮 牛");
    public static final Emoji 鲸鱼 = new Emoji(2, 128051, "🐳 鲸鱼");
    public static final Emoji 猴 = new Emoji(2, 128053, "🐵 猴");
    public static final Emoji 拳头2 = new Emoji(2, 128074, "👊 拳头");
    public static final Emoji 好的 = new Emoji(2, 128076, "👌 好的");
    public static final Emoji 厉害 = new Emoji(2, 128077, "👍 厉害");
    public static final Emoji 鼓掌2 = new Emoji(2, 128079, "👏 鼓掌");
    public static final Emoji 内衣 = new Emoji(2, 128089, "👙 内衣");
    public static final Emoji 男孩 = new Emoji(2, 128102, "👦 男孩");
    public static final Emoji 爸爸 = new Emoji(2, 128104, "👨 爸爸");
    public static final Emoji 爱心2 = new Emoji(2, 128147, "💓 爱心");
    public static final Emoji 礼物 = new Emoji(2, 128157, "💝 礼物");
    public static final Emoji 睡觉 = new Emoji(2, 128164, "💤 睡觉");
    public static final Emoji 水 = new Emoji(2, 128166, "💦 水");
    public static final Emoji 吹气 = new Emoji(2, 128168, "💨 吹气");
    public static final Emoji 肌肉 = new Emoji(2, 128170, "💪 肌肉");
    public static final Emoji 邮箱 = new Emoji(2, 128235, "📫 邮箱");
    public static final Emoji 火 = new Emoji(2, 128293, "🔥 火");
    public static final Emoji 呲牙 = new Emoji(2, 128513, "😁 呲牙");
    public static final Emoji 激动 = new Emoji(2, 128514, "😂 激动");
    public static final Emoji 高兴 = new Emoji(2, 128516, "😄 高兴");
    public static final Emoji 嘿嘿 = new Emoji(2, 128522, "😊 嘿嘿");
    public static final Emoji 羞涩 = new Emoji(2, 128524, "😌 羞涩");
    public static final Emoji 哼哼 = new Emoji(2, 128527, "😏 哼哼");
    public static final Emoji 不屑 = new Emoji(2, 128530, "😒 不屑");
    public static final Emoji 汗2 = new Emoji(2, 128531, "😓 汗");
    public static final Emoji 失落 = new Emoji(2, 128532, "😔 失落");
    public static final Emoji 飞吻2 = new Emoji(2, 128536, "😘 飞吻");
    public static final Emoji 亲亲 = new Emoji(2, 128538, "😚 亲亲");
    public static final Emoji 淘气 = new Emoji(2, 128540, "😜 淘气");
    public static final Emoji 吐舌 = new Emoji(2, 128541, "😝 吐舌");
    public static final Emoji 大哭2 = new Emoji(2, 128557, "😭 大哭");
    public static final Emoji 紧张 = new Emoji(2, 128560, "😰 紧张");
    public static final Emoji 瞪眼 = new Emoji(2, 128563, "😳 瞪眼");


    private Integer id;
    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>1</td> <td>系统表情</td></tr> <tr><td>2</td> <td>emoji表情</td></tr></tbody></table>
     */
    private Integer type;

    /**
     * 不存在
     */
    @JSONField(serialize = false, deserialize = false)
    private String text;

    public Emoji(Integer type, Integer id, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
        VALUES.add(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Emoji valueOf(Integer type, Integer id) {
        for (Emoji value : VALUES) {
            if (value.type == type && value.id == (id)) return value;
        }
        return new Emoji(type, id, "未知表情");
    }

    public static Emoji valueOf(Integer id) {
        for (Emoji value : VALUES) {
            if (value.id == id) return value;
        }
        return new Emoji(1, id, "未知表情");
    }

    @Override
    public ActionResult send(SenderAndCidMidGetter er) {
        MessagePacket packet = new MessagePacket();
        packet.setContent(toString0());
        return er.send(packet);
    }

    @Override
    public String toString() {
        return String.format("[emoji(%s):%s]", getId(), getText());
    }

    public String toString0() {
        return String.format("<emoji:%s>", getId());
    }
}
