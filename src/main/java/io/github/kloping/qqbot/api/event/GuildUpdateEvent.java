package io.github.kloping.qqbot.api.event;

/**
 * @author github.kloping
 */
public interface GuildUpdateEvent extends GuildEvent {

    String getUnionAppId();

    String getUnionOrgId();

    String getUnionWorldId();
}
