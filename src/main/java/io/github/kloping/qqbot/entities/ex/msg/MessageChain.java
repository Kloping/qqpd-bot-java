package io.github.kloping.qqbot.entities.ex.msg;

import io.github.kloping.judge.Judge;
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

import java.util.*;
import java.util.function.Consumer;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * @author github.kloping
 */
public class MessageChain implements SendAble, List<SendAble> {

    private final List<SendAble> data = new LinkedList<>();

    public void forEach(Consumer<? super SendAble> consumer) {
        for (SendAble data : data) {
            consumer.accept(data);
        }
    }

    public MessageChain append(SendAble sendAble) {
        if (sendAble != null) data.add(sendAble);
        return this;
    }

    private MessageChain append(SendAble sendAble, MessagePreBuilder builder) {
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
        return this;
    }

    public MessageChain at(String id) {
        return append(new At(At.MEMBER_TYPE, id));
    }

    public MessageChain image(String url) {
        return append(new Image(url));
    }

    public MessageChain image(byte[] bytes) {
        return append(new Image(bytes));
    }

    public MessageChain text(String text) {
        return append(new PlainText(text));
    }

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (data.isEmpty()) return null;
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePreBuilder builder = new MessagePreBuilder();
            boolean flag0 = false;
            Result result = null;
            for (SendAble sendAble : data) {
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
                } else if (sendAble instanceof Markdown) {
                    result = ((Markdown) sendAble).send(er);
                }
            }
            if (!builder.isEmpty()) {
                result = er.send(builder.build());
            }
            return result;
        } else {
            SenderV2 v2 = (SenderV2) er;
            boolean flag0 = false;
            V2MsgData data = new V2MsgData();
            if (Judge.isNotEmpty(er.getMid())) data.setMsg_id(er.getMid());
            Result<V2Result> result = null;
            int sent = 0;
            for (SendAble e0 : this.data) {
                if (e0 instanceof Image) {
                    Image image = (Image) e0;
                    RawMessage.imagePrepare(image, er.getBot());
                    if (!flag0) {
                        if (image.getFile_type() != 1) {
                            image.send(er);
                            sent++;
                            continue;
                        }
                        flag0 = true;
                    } else {
                        data.setMsg_seq(v2.getMsgSeq());
                        v2.getV2().send(er.getCid(), data.toString(), SEND_MESSAGE_HEADERS);
                        data = new V2MsgData();
                        if (Judge.isNotEmpty(er.getMid())) data.setMsg_id(er.getMid());
                    }
                    if (Judge.isNotEmpty(image.getUrl())) {
                        result = new Result<>(v2.getV2().sendFile(er.getCid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": false}",
                                image.getFile_type(), image.getUrl()), Channel.SEND_MESSAGE_HEADERS));
                    } else {
                        result = new Result<>(v2.getV2().sendFile(er.getCid(), String.format("{\"file_type\": %s,\"file_data\": \"%s\",\"srv_send_msg\": false}",
                                image.getFile_type(), Base64.getEncoder().encodeToString(image.getBytes())), Channel.SEND_MESSAGE_HEADERS));
                    }
                    result.getData().logFileInfo(er.getBot().logger, image);
                    data.setMsg_type(7);
                    data.setMedia(new V2MsgData.Media(result.getData().getFile_info()));
                } else if (e0 instanceof Markdown) {
                    result = ((Markdown) e0).send(er, v2.getMsgSeq());
                    sent++;
                } else if (e0 instanceof Keyboard) {
                    data.setKeyboard(e0);
                    data.setMsg_type(2);
                } else {
                    data.setContent(data.getContent() + e0.toString());
                }
            }
            if (sent < this.data.size()) {
                data.setMsg_seq(v2.getMsgSeq());
                return result = new Result<V2Result>(v2.getV2().send(er.getCid(), data.toString(), SEND_MESSAGE_HEADERS));
            } else return result;
        }
    }

    @Override
    public SendAble get(int index) {
        return data.get(index);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<SendAble> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(SendAble sendAble) {
        return data.add(sendAble);
    }

    @Override
    public boolean remove(Object o) {
        return data.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends SendAble> c) {
        return data.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends SendAble> c) {
        return data.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return data.remove(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return data.retainAll(c);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public SendAble set(int index, SendAble element) {
        return data.set(index, element);
    }

    @Override
    public void add(int index, SendAble element) {
        data.add(index, element);
    }

    @Override
    public SendAble remove(int index) {
        return data.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<SendAble> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<SendAble> listIterator(int index) {
        return data.listIterator(index);
    }

    @Override
    public List<SendAble> subList(int fromIndex, int toIndex) {
        return data.subList(fromIndex, toIndex);
    }

    public void reSet(List<SendAble> collect) {
        data.clear();
        data.addAll(collect);
    }
}
