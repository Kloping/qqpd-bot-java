package io.github.kloping.qqbot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.BotContent;
import io.github.kloping.qqbot.api.exc.RequestException;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.exc.QBotError;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.impls.HttpStatusReceiver;
import io.github.kloping.spt.interfaces.component.HttpClientManager;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author github.kloping
 */
@Entity
@Slf4j
public class HttpClientConfig implements HttpStatusReceiver {
    @AutoStandAfter
    public void after(HttpClientManager manager) {
        manager.addHttpStatusReceiver(this);
    }


    @AutoStand
    Bot bot;

    @AutoStand
    Starter.Config config;

    @Override
    public void receive(HttpClientManager manager, String url, Integer code, Class<?> interface0, Method method,
                        Connection.Method reqMethod, Class<?> cla, Object o, Document metadata) {
        if (o == null || code == null || metadata == null) return;
        log.debug(String.format("Use the (%s) method through the (%s) interface to request " +
                        "the data obtained by the response code of the (%s) URL is (%s), " +
                        "and (%s) may be converted to (%s) type Will be processed and filtered",
                reqMethod.name(), interface0.getSimpleName(), url, code, metadata.body().wholeText(), o
        ));
        fillAll(cla, o);
        config.getEventExecutor().submit(() -> {
            if (o instanceof ActionResult) {
                ActionResult result = (ActionResult) o;
                if (result.getSent()) {
                    RawMessage rawMessage = result.getRawMessage();
                    if (url.contains("dms")) {
                        log.info(String.format("Bot(%s): %s <= %s",
                                bot.getInfo().getUsername(),
                                rawMessage.getChannelId() + "(私信)", rawMessage.getContent().trim()));
                    } else {
                        log.info(String.format("Bot(%s): %s <= %s",
                                bot.getInfo().getUsername(),
                                bot.getGuild(rawMessage.getGuildId()).getChannel(rawMessage.getChannelId()).getName(),
                                rawMessage.getContent().trim()));
                    }
                }
            }
        });
        if (code >= 400 || code < 200) {
            RequestException requestException = null;
            String bodyJson = metadata.body().wholeText();
            JSONObject exjo = JSON.parseObject(bodyJson);
            QBotError error = exjo.toJavaObject(QBotError.class);
            //加入exc对象
            int eccode = exjo.getInteger("code");
            if (Resource.CODE2EXCEPTION.containsKey(eccode)) {
                try {
                    Class<? extends RequestException> exceptionClass = Resource.CODE2EXCEPTION.get(eccode);
                    Constructor constructor = exceptionClass.getConstructor(int.class, String.class, String.class, String.class);
                    requestException = (RequestException) constructor.newInstance(eccode, bodyJson, url, method.getName());
                } catch (Exception e) {
                    log.error("Failed to create request exception", e);
                }
            } else {
                requestException = new RequestException(eccode, bodyJson, url, method.getName());
            }
            if (requestException != null) requestException.setData(error);
            throw requestException;
        }
    }


    public void fillAll(Class<?> cla, Object o) {
        if (o instanceof BotContent) {
            BotContent content = (BotContent) o;
            content.setBot(bot);
        }

        if (cla.isArray()) {
            Object[] obs = (Object[]) o;
            for (Object ob : obs) {
                if (ob instanceof BotContent) {
                    BotContent content = (BotContent) ob;
                    content.setBot(bot);
                }
            }
        }
    }
}
