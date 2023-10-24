package io.github.kloping.qqbot.entities.ex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>name</td> <td>string</td> <td>子频道名称</td></tr> <tr><td>type</td> <td>int</td> <td>子频道类型 <a href="/wiki/develop/api/openapi/channel/model.html#channeltype" class="">ChannelType</a></td></tr> <tr><td>sub_type</td> <td>int</td> <td>子频道子类型 <a href="/wiki/develop/api/openapi/channel/model.html#channelsubtype" class="">ChannelSubType</a></td></tr> <tr><td>position</td> <td>int</td> <td>子频道排序，必填；当子频道类型为 <code>子频道分组（ChannelType=4）</code>时，必须大于等于 2</td></tr> <tr><td>parent_id</td> <td>string</td> <td>子频道所属分组ID</td></tr> <tr><td>private_type</td> <td>int</td> <td>子频道私密类型 <a href="/wiki/develop/api/openapi/channel/model.html#privatetype" class="">PrivateType</a></td></tr> <tr><td>private_user_ids</td> <td>string 数组</td> <td>子频道私密类型成员 ID</td></tr> <tr><td>speak_permission</td> <td>int</td> <td>子频道发言权限 <a href="/wiki/develop/api/openapi/channel/model.html#speakpermission" class="">SpeakPermission</a></td></tr> <tr><td>application_id</td> <td>string</td> <td>应用类型子频道应用 AppID，仅应用子频道需要该字段</td></tr></tbody></table>
 *
 * @author github.kloping
 */
public class ChannelData {

    public static ChannelData create() {
        return new ChannelData();
    }

    private JSONObject data = JSON.parseObject("{\n" +
            "  \"name\": \"默认子频道名称\",\n" +
            "  \"type\": 0,\n" +
            "  \"position\": 1,\n" +
            "  \"sub_type\": 0,\n" +
            "  \"speak_permission\": 1,\n" +
            "  \"private_type\": 0\n" +
            "}\n");

    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>1000000</td> <td>王者开黑大厅</td></tr> <tr><td>1000001</td> <td>互动小游戏</td></tr> <tr><td>1000010</td> <td>腾讯投票</td></tr> <tr><td>1000051</td> <td>飞车开黑大厅</td></tr> <tr><td>1000050</td> <td>日程提醒</td></tr> <tr><td>1000070</td> <td>CoDM 开黑大厅</td></tr> <tr><td>1010000</td> <td>和平精英开黑大厅</td></tr></tbody></table>
     * @param id
     * @return
     */
    public ChannelData applicationId(Integer id){
        data.put("application_id",id);
        return this;
    }

    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>无效类型</td></tr> <tr><td>1</td> <td>所有人</td></tr> <tr><td>2</td> <td>群主管理员+指定成员，可使用 <a href="/wiki/develop/api/openapi/channel_permissions/put_channel_permissions.html#修改子频道权限" class="">修改子频道权限接口</a> 指定成员</td></tr></tbody></table>
     *
     * @param id
     * @return
     */
    public ChannelData speakPermission(Integer id) {
        data.put("speak_permission", id);
        return this;
    }

    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>公开频道</td></tr> <tr><td>1</td> <td>群主管理员可见</td></tr> <tr><td>2</td> <td>群主管理员+指定成员，可使用 <a href="/wiki/develop/api/openapi/channel_permissions/put_channel_permissions.html#修改子频道权限" class="">修改子频道权限接口</a> 指定成员</td></tr></tbody></table>
     *
     * @param type
     * @return
     */
    public ChannelData privateType(Integer type) {
        data.put("private_type", type);
        return this;
    }

    public ChannelData parentId(String id) {
        data.put("parent_id", id);
        return this;
    }

    public ChannelData position(Integer position) {
        data.put("position", position);
        return this;
    }

    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>闲聊</td></tr> <tr><td>1</td> <td>公告</td></tr> <tr><td>2</td> <td>攻略</td></tr> <tr><td>3</td> <td>开黑</td></tr></tbody></table>
     *
     * @param type
     * @return
     */
    public ChannelData subType(Integer type) {
        data.put("sub_type", type);
        return this;
    }

    public ChannelData name(String name) {
        data.put("name", name);
        return this;
    }

    /**
     * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>文字子频道</td></tr> <tr><td>1</td> <td>保留，不可用</td></tr> <tr><td>2</td> <td>语音子频道</td></tr> <tr><td>3</td> <td>保留，不可用</td></tr> <tr><td>4</td> <td>子频道分组</td></tr> <tr><td>10005</td> <td>直播子频道</td></tr> <tr><td>10006</td> <td>应用子频道</td></tr> <tr><td>10007</td> <td>论坛子频道</td></tr></tbody></table>
     *
     * @return
     */
    public ChannelData type(Integer type) {
        data.put("type", type);
        return this;
    }


    @Override
    public String toString() {
        return data.toString();
    }
}
