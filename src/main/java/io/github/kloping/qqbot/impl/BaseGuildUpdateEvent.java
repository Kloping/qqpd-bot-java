package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.GuildUpdateEvent;
import io.github.kloping.qqbot.entities.Bot;

/**
 * @author github.kloping
 */
public class BaseGuildUpdateEvent extends BaseGuildEvent implements GuildUpdateEvent {
    public BaseGuildUpdateEvent(JSONObject jo, Bot bot) {
        super(jo, bot);
    }

    @Override
    public String getUnionAppId() {
        return getMetadata().getString("union_appid");
    }

    @Override
    public String getUnionOrgId() {
        return getMetadata().getString("union_org_id");
    }

    @Override
    public String getUnionWorldId() {
        return getMetadata().getString("union_world_id");
    }

    @Override
    public String toString() {
        return String.format("guild(%s) id(%s) info change", guild.getName(), guild.getId());
    }

    @Override
    public String getId() {
        return getMetadata().get("id").toString();
    }
}
