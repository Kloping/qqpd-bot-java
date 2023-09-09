package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.judge.Judge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author github.kloping
 */
public class MutePack {
    private String mute_end_timestamp;
    private String mute_seconds;
    private List<String> user_ids = new ArrayList<>();

    public void setMuteEndTimestamp(long timestamp) {
        this.mute_end_timestamp = String.valueOf(timestamp);
    }

    public void setMuteSeconds(int sec) {
        this.mute_seconds = String.valueOf(sec);
    }

    public List<String> getUserIds() {
        return user_ids;
    }

    @Override
    public String toString() {
        JSONObject jo = new JSONObject();
        if (Judge.isNotEmpty(mute_end_timestamp)) {
            jo.put("mute_end_timestamp", mute_end_timestamp);
        } else if (Judge.isNotEmpty(mute_seconds)) {
            jo.put("mute_seconds", mute_seconds);
        }
        if (!user_ids.isEmpty()) {
            jo.put("user_ids", JSON.toJSONString(user_ids));
        }
        return jo.toString();
    }
}
