package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.MySpringTool.entity.KeyVals;
import org.jsoup.helper.HttpConnection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author github.kloping
 */
public class BaseKeyVals implements KeyVals {
    private List<HttpConnection.KeyVal> list = new LinkedList<>();

    public BaseKeyVals add(HttpConnection.KeyVal keyVal) {
        list.add(keyVal);
        return this;
    }

    @Override
    public Collection<HttpConnection.KeyVal> values() {
        return list;
    }
}
