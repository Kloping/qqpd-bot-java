package io.github.kloping.qqbot.api;

/**
 * <pre class="language-yaml codecopy-enabled"><code>GUILDS (1 &lt;&lt; 0)
 *   <span class="token punctuation">-</span> GUILD_CREATE           // 当机器人加入新guild时
 *   <span class="token punctuation">-</span> GUILD_UPDATE           // 当guild资料发生变更时
 *   <span class="token punctuation">-</span> GUILD_DELETE           // 当机器人退出guild时
 *   <span class="token punctuation">-</span> CHANNEL_CREATE         // 当channel被创建时
 *   <span class="token punctuation">-</span> CHANNEL_UPDATE         // 当channel被更新时
 *   <span class="token punctuation">-</span> CHANNEL_DELETE         // 当channel被删除时
 *
 * GUILD_MEMBERS (1 &lt;&lt; 1)
 *   <span class="token punctuation">-</span> GUILD_MEMBER_ADD       // 当成员加入时
 *   <span class="token punctuation">-</span> GUILD_MEMBER_UPDATE    // 当成员资料变更时
 *   <span class="token punctuation">-</span> GUILD_MEMBER_REMOVE    // 当成员被移除时
 *
 * GUILD_MESSAGES (1 &lt;&lt; 9)    // 消息事件，仅 <span class="token important">*私域*</span> 机器人能够设置此 intents。
 *   <span class="token punctuation">-</span> MESSAGE_CREATE         // 发送消息事件，代表频道内的全部消息，而不只是 at 机器人的消息。内容与 AT_MESSAGE_CREATE 相同
 *   <span class="token punctuation">-</span> MESSAGE_DELETE         // 删除（撤回）消息事件
 *
 * GUILD_MESSAGE_REACTIONS (1 &lt;&lt; 10)
 *   <span class="token punctuation">-</span> MESSAGE_REACTION_ADD    // 为消息添加表情表态
 *   <span class="token punctuation">-</span> MESSAGE_REACTION_REMOVE // 为消息删除表情表态
 *
 * DIRECT_MESSAGE (1 &lt;&lt; 12)
 *   <span class="token punctuation">-</span> DIRECT_MESSAGE_CREATE   // 当收到用户发给机器人的私信消息时
 *   <span class="token punctuation">-</span> DIRECT_MESSAGE_DELETE   // 删除（撤回）消息事件
 *
 *
 * GROUP_AND_C2C_EVENT (1 &lt;&lt; 25)
 *   <span class="token punctuation">-</span> C2C_MESSAGE_CREATE      // 用户单聊发消息给机器人时候
 *   <span class="token punctuation">-</span> FRIEND_ADD              // 用户添加使用机器人
 *   <span class="token punctuation">-</span> FRIEND_DEL              // 用户删除机器人
 *   <span class="token punctuation">-</span> C2C_MSG_REJECT          // 用户在机器人资料卡手动关闭"主动消息"推送
 *   <span class="token punctuation">-</span> C2C_MSG_RECEIVE         // 用户在机器人资料卡手动开启"主动消息"推送开关
 *   <span class="token punctuation">-</span> GROUP_AT_MESSAGE_CREATE // 用户在群里@机器人时收到的消息
 *   <span class="token punctuation">-</span> GROUP_ADD_ROBOT         // 机器人被添加到群聊
 *   <span class="token punctuation">-</span> GROUP_DEL_ROBOT         // 机器人被移出群聊
 *   <span class="token punctuation">-</span> GROUP_MSG_REJECT        // 群管理员主动在机器人资料页操作关闭通知
 *   <span class="token punctuation">-</span> GROUP_MSG_RECEIVE       // 群管理员主动在机器人资料页操作开启通知
 *
 * INTERACTION (1 &lt;&lt; 26)
 *   <span class="token punctuation">-</span> INTERACTION_CREATE     // 互动事件创建时
 *
 * MESSAGE_AUDIT (1 &lt;&lt; 27)
 * <span class="token punctuation">-</span> MESSAGE_AUDIT_PASS     // 消息审核通过
 * <span class="token punctuation">-</span> MESSAGE_AUDIT_REJECT   // 消息审核不通过
 *
 * FORUMS_EVENT (1 &lt;&lt; 28)  // 论坛事件，仅 <span class="token important">*私域*</span> 机器人能够设置此 intents。
 *   <span class="token punctuation">-</span> FORUM_THREAD_CREATE     // 当用户创建主题时
 *   <span class="token punctuation">-</span> FORUM_THREAD_UPDATE     // 当用户更新主题时
 *   <span class="token punctuation">-</span> FORUM_THREAD_DELETE     // 当用户删除主题时
 *   <span class="token punctuation">-</span> FORUM_POST_CREATE       // 当用户创建帖子时
 *   <span class="token punctuation">-</span> FORUM_POST_DELETE       // 当用户删除帖子时
 *   <span class="token punctuation">-</span> FORUM_REPLY_CREATE      // 当用户回复评论时
 *   <span class="token punctuation">-</span> FORUM_REPLY_DELETE      // 当用户回复评论时
 *   <span class="token punctuation">-</span> FORUM_PUBLISH_AUDIT_RESULT      // 当用户发表审核通过时
 *
 * AUDIO_ACTION (1 &lt;&lt; 29)
 *   <span class="token punctuation">-</span> AUDIO_START             // 音频开始播放时
 *   <span class="token punctuation">-</span> AUDIO_FINISH            // 音频播放结束时
 *   <span class="token punctuation">-</span> AUDIO_ON_MIC            // 上麦时
 *   <span class="token punctuation">-</span> AUDIO_OFF_MIC           // 下麦时
 *
 * PUBLIC_GUILD_MESSAGES (1 &lt;&lt; 30) // 消息事件，此为公域的消息事件
 *   <span class="token punctuation">-</span> AT_MESSAGE_CREATE       // 当收到@机器人的消息时
 *   <span class="token punctuation">-</span> PUBLIC_MESSAGE_DELETE   // 当频道的消息被删除时
 * </code><i class="code-copy" title="Copy to clipboard"><svg style="color:#aaa;font-size:14px" t="1572422231464" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3201" width="14" height="14"><path d="M866.461538 39.384615H354.461538c-43.323077 0-78.769231 35.446154-78.76923 78.769231v39.384616h472.615384c43.323077 0 78.769231 35.446154 78.769231 78.76923v551.384616h39.384615c43.323077 0 78.769231-35.446154 78.769231-78.769231V118.153846c0-43.323077-35.446154-78.769231-78.769231-78.769231z m-118.153846 275.692308c0-43.323077-35.446154-78.769231-78.76923-78.769231H157.538462c-43.323077 0-78.769231 35.446154-78.769231 78.769231v590.769231c0 43.323077 35.446154 78.769231 78.769231 78.769231h512c43.323077 0 78.769231-35.446154 78.76923-78.769231V315.076923z m-354.461538 137.846154c0 11.815385-7.876923 19.692308-19.692308 19.692308h-157.538461c-11.815385 0-19.692308-7.876923-19.692308-19.692308v-39.384615c0-11.815385 7.876923-19.692308 19.692308-19.692308h157.538461c11.815385 0 19.692308 7.876923 19.692308 19.692308v39.384615z m157.538461 315.076923c0 11.815385-7.876923 19.692308-19.692307 19.692308H216.615385c-11.815385 0-19.692308-7.876923-19.692308-19.692308v-39.384615c0-11.815385 7.876923-19.692308 19.692308-19.692308h315.076923c11.815385 0 19.692308 7.876923 19.692307 19.692308v39.384615z m78.769231-157.538462c0 11.815385-7.876923 19.692308-19.692308 19.692308H216.615385c-11.815385 0-19.692308-7.876923-19.692308-19.692308v-39.384615c0-11.815385 7.876923-19.692308 19.692308-19.692308h393.846153c11.815385 0 19.692308 7.876923 19.692308 19.692308v39.384615z" p-id="3202"></path></svg></i></pre>
 * @author github.kloping
 */
public enum Intents {
    /**
     * 订阅前提
     */
    START(0),
    GUILDS(1 << 0),
    GUILD_MEMBERS(1 << 1),
    /**
     * 仅私域
     */
    GUILD_MESSAGES(1 << 9),
    GUILD_MESSAGE_REACTIONS(1 << 10),
    DIRECT_MESSAGE(1 << 12),
    /**
     * 公域事件
     */
    OPEN_FORUMS_EVENT(1 << 18),
    AUDIO_OR_LIVE_CHANNEL_MEMBER(1 << 19),
    /**
     * 群聊事件
     */
    GROUP_INTENTS(1 << 25),
    INTERACTION(1 << 26),
    MESSAGE_AUDIT(1 << 27),
    /**
     * 仅私域
     */
    FORUMS_EVENT(1 << 28),
    AUDIO_ACTION(1 << 29),
    /**
     * 公域消息事件
     */
    PUBLIC_GUILD_MESSAGES(1 << 30),
    /**
     * Intents 事件订阅方式
     * 默认
     */
    DEFAULT(738726915),
    /**
     * 公域机器人订阅推荐
     */
    PUBLIC_INTENTS(1812468739),
    PUBLIC_GROUP_INTENTS(1812468739 | 1 << 25),
    /**
     * 私域机器人订阅推荐
     */
    PRIVATE_INTENTS(1007162883)
    ;

    private final Integer code;

    private Intents(Integer code) {
        this.code = code;
    }

    public Integer and(Intents... intents) {
        Integer code0 = code;
        for (Intents intent : intents) {
            code0 = code0 | intent.code;
        }
        return code0;
    }

    public Integer getCode() {
        return code;
    }
}
