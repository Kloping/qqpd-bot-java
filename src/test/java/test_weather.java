
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author github.kloping
 */
public class test_weather {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        String w0 = String.format("<@!%s> /天气", starter.getBot().getInfo().getId());
        starter.registerListenerHost(new ListenerHost() {
            @Override
            public void handleException(Throwable e) {

            }

            @EventReceiver
            private void event(MessageEvent event) {
                RawMessage message = event.getRawMessage();
                String content = message.getContent();
                if (content == null && content.isEmpty()) {
                    return;
                } else if (content.startsWith(w0)) {
                    String s = content.substring(w0.length());
                    message.send("<@!" + message.getAuthor().getId() + ">  \n" + w0(s.trim()));
                }
            }
        });
    }

    public static String w0(String s0) {
        try {
            Document document = null;
            try {
                document = Jsoup.connect("http://kloping.top/api/get/weather?address=" + s0).ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json = document.body().text();
            JSONObject jo = JSON.parseObject(json);
            WeatherDetail wd = jo.toJavaObject(WeatherDetail.class);
            StringBuilder sb = new StringBuilder();
            sb.append(wd.getTime()).append("\n");
            sb.append(wd.getAddress()).append(":").append(wd.getDescribed()).append("\n");
            sb.append(wd.getWind()).append("\n");
            sb.append(wd.getAir()).append("\n");
            sb.append(wd.getHumidity()).append("\n");
            sb.append(wd.getPm()).append("\n");
            sb.append("现在温度:").append(wd.getTemperatureNow()).append("\n");
            sb.append("今日温度:").append(wd.getTemperature()).append("\n");
            sb.append(wd.getUva()).append("\n");
            sb.append(wd.getSunOn()).append("\n");
            sb.append(wd.getSunDown()).append("\n");
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败";
        }
    }

    public static class WeatherDetail {
        /**
         * 地名
         */
        private String address;
        /**
         * 描述
         */
        private String described;
        /**
         * 湿度
         */
        private String humidity;
        /**
         * 风向
         */
        private String wind;
        /**
         * 紫外线强度
         */
        private String uva;
        /**
         * 温度
         */
        private String temperature;
        /**
         * 现在温度
         */
        private String temperatureNow;
        /**
         * PM
         */
        private String pm;
        /**
         * 日出
         */
        private String sunOn;
        /**
         * 日落
         */
        private String sunDown;
        /**
         * 时间
         */
        private String time;
        private String air;

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDescribed() {
            return described;
        }

        public void setDescribed(String described) {
            this.described = described;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getUva() {
            return uva;
        }

        public void setUva(String uva) {
            this.uva = uva;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperatureNow() {
            return temperatureNow;
        }

        public void setTemperatureNow(String temperatureNow) {
            this.temperatureNow = temperatureNow;
        }

        public String getPm() {
            return pm;
        }

        public void setPm(String pm) {
            this.pm = pm;
        }

        public String getSunOn() {
            return sunOn;
        }

        public void setSunOn(String sunOn) {
            this.sunOn = sunOn;
        }

        public String getSunDown() {
            return sunDown;
        }

        public void setSunDown(String sunDown) {
            this.sunDown = sunDown;
        }
    }

}
