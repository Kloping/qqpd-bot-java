package io.github.kloping.qqbot;

import com.google.gson.Gson;
import io.github.kloping.qqbot.api.exc.RequestException;
import io.github.kloping.qqbot.impl.exc.InvalidRequestException;
import io.github.kloping.spt.annotations.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@Entity
public class Resource {
    public static final Gson GSON = new Gson();

    public static final Map<Integer, Class<? extends RequestException>> CODE2EXCEPTION = new HashMap<>();

    static {
        Resource.CODE2EXCEPTION.put(11255, InvalidRequestException.class);
        Resource.CODE2EXCEPTION.put(11244, InvalidRequestException.class);
    }
}
