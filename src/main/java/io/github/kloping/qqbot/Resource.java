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

    public static void print() {
        System.out.println("\n" +
                "                                 __               __                 __                                                    \n" +
                "                                /\\ \\             /\\ \\               /\\ \\__             __                                  \n" +
                "   __         __       _____    \\_\\ \\            \\ \\ \\____    ___   \\ \\ ,_\\           /\\_\\       __      __  __     __     \n" +
                " /'__`\\     /'__`\\    /\\ '__`\\  /'_` \\   _______  \\ \\ '__`\\  / __`\\  \\ \\ \\/   _______ \\/\\ \\    /'__`\\   /\\ \\/\\ \\  /'__`\\   \n" +
                "/\\ \\L\\ \\   /\\ \\L\\ \\   \\ \\ \\L\\ \\/\\ \\L\\ \\ /\\______\\  \\ \\ \\L\\ \\/\\ \\L\\ \\  \\ \\ \\_ /\\______\\ \\ \\ \\  /\\ \\L\\.\\_ \\ \\ \\_/ |/\\ \\L\\.\\_ \n" +
                "\\ \\___, \\  \\ \\___, \\   \\ \\ ,__/\\ \\___,_\\\\/______/   \\ \\_,__/\\ \\____/   \\ \\__\\\\/______/ _\\ \\ \\ \\ \\__/.\\_\\ \\ \\___/ \\ \\__/.\\_\\\n" +
                " \\/___/\\ \\  \\/___/\\ \\   \\ \\ \\/  \\/__,_ /             \\/___/  \\/___/     \\/__/         /\\ \\_\\ \\ \\/__/\\/_/  \\/__/   \\/__/\\/_/\n" +
                "      \\ \\_\\      \\ \\_\\   \\ \\_\\                                                        \\ \\____/                             \n" +
                "       \\/_/       \\/_/    \\/_/                                                         \\/___/                              \n");
    }
}
