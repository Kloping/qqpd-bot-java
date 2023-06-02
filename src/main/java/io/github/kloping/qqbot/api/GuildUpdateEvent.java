package io.github.kloping.qqbot.api;

/**
 * @author github.kloping
 */
public interface GuildUpdateEvent extends GuildEvent {

    String getUnionAppId();

    String getUnionOrgId();

    String getUnionWorldId();
}
