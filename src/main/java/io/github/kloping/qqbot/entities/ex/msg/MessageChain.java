package io.github.kloping.qqbot.entities.ex.msg;

import io.github.kloping.judge.Judge;
import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.*;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * @author github.kloping
 */
public class MessageChain implements SendAble {

    public List<SendAble> list = new LinkedList<>();

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (list.isEmpty()) return null;
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePreBuilder builder = new MessagePreBuilder();
            boolean flag0 = false;
            for (SendAble sendAble : list) {
                if (sendAble instanceof PlainText) {
                    builder.append(sendAble.toString());
                } else if (sendAble instanceof Image) {
                    if (!flag0) {
                        builder.append((Image) sendAble);
                        flag0 = true;
                    } else {
                        er.send(builder.build());
                        builder.clear();
                        builder.append((Image) sendAble);
                    }
                } else if (sendAble instanceof At) {
                    builder.append((At) sendAble);
                } else if (sendAble instanceof AtAll) {
                    builder.append((AtAll) sendAble);
                } else if (sendAble instanceof Emoji) {
                    builder.append((Emoji) sendAble);
                }
            }
            return er.send(builder.build());
        } else {
            SenderV2 v2 = (SenderV2) er;
            boolean flag0 = false;

            V2MsgData data = new V2MsgData();
            if (Judge.isNotEmpty(er.getMid())) data.setMsg_id(er.getMid());

            for (SendAble e0 : list) {
                if (e0 instanceof Image) {
                    Image image = (Image) e0;
                    if (RawMessage.imagePrepare(image, er.getBot())) continue;
                    if (!flag0) {
                        flag0 = true;
                        V2Result result = v2.getV2().sendFile(er.getCid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": false}",
                                image.getFile_type(), image.getUrl()), Channel.SEND_MESSAGE_HEADERS);
                        result.logFileInfo(er.getBot().logger);
                        data.setMsg_type(7);
                        data.setMedia(new V2MsgData.Media(result.getFile_info()));
                    } else {
                        v2.getV2().send(er.getCid(), data.toString(), SEND_MESSAGE_HEADERS);
                        data = new V2MsgData();
                        if (Judge.isNotEmpty(er.getMid())) data.setMsg_id(er.getMid());
                        V2Result result = v2.getV2().sendFile(er.getCid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": false}",
                                image.getFile_type(), image.getUrl()), Channel.SEND_MESSAGE_HEADERS);
                        result.logFileInfo(er.getBot().logger);
                        data.setMsg_type(7);
                        data.setMedia(new V2MsgData.Media(result.getFile_info()));
                    }
                } else {
                    data.setContent(data.getContent() + e0.toString());
                }
            }
            return new Result<V2Result>(v2.getV2().send(er.getCid(), data.toString(), SEND_MESSAGE_HEADERS));
        }
    }

    public void forEach(Consumer<? super SendAble> consumer) {
        for (SendAble data : list) {
            consumer.accept(data);
        }
    }

    public MessageChain append(SendAble sendAble) {
        if (sendAble != null)
            list.add(sendAble);
        return this;
    }

    private void append(SendAble sendAble, MessagePreBuilder builder) {
        if (sendAble instanceof At) {
            builder.append((At) sendAble);
        } else if (sendAble instanceof AtAll) {
            builder.append((AtAll) sendAble);
        } else if (sendAble instanceof Image) {
            builder.append((Image) sendAble);
        } else if (sendAble instanceof PlainText) {
            builder.append(((PlainText) sendAble).getText());
        } else if (sendAble instanceof Emoji) {
            builder.append((Emoji) sendAble);
        }
    }

    public int size() {
        return list.size();
    }

    public SendAble get(int index) {
        return list.get(index);
    }

    public boolean isType(int index, Class<? extends SendAble> cla) {
        return ObjectUtils.isSuperOrInterface(cla, list.get(index).getClass());
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
