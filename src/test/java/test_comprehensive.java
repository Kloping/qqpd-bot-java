import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.number.NumberUtils;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.api.message.MessagePacket;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author github.kloping
 */
public class test_comprehensive {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
//        starter.getWssWorker().setReconnect(true);

        String w0 = String.format("<@!%s> /天气", starter.getBot().getInfo().getId());
        starter.addListener(new OnAtMessageListener() {
            @Override
            public void onMessage(Message message) {
                String content = message.getContent();
                if (content == null && content.isEmpty()) {
                    return;
                } else if (content.startsWith(w0)) {
                    String s = content.substring(w0.length());
                    message.send("<@!" + message.getAuthor().getId() + ">  \n" + w0(s.trim()), message);
                }
            }
        });

        String w1 = String.format("<@!%s> /小爱", starter.getBot().getInfo().getId());
        starter.addListener(new OnAtMessageListener() {
            @Override
            public void onMessage(Message message) {
                String content = message.getContent();
                if (content == null && content.isEmpty()) {
                    return;
                } else if (content.startsWith(w1)) {
                    String s = content.substring(w1.length());
                    String r0 = w1(s.trim());
                    message.send("<@!" + message.getAuthor().getId() + ">  \n" + r0, message);
                }
            }
        });

        String w2 = String.format("<@!%s> /蹭", starter.getBot().getInfo().getId());
        starter.addListener(new OnAtMessageListener() {
            @Override
            public void onMessage(Message message) {
                message.send("<@!" + message.getAuthor().getId() + "> 功能失效,尽量修复中..");
//                String content = message.getContent();
//                if (content == null && content.isEmpty()) {
//                    return;
//                } else if (content.startsWith(w2)) {
//                    String s = content.substring(w2.length());
//                    String qid = NumberUtils.findNumberFromString(s);
//                    if (qid.isEmpty()){
//
//                    }
//                    String avatar = starter.getBot().getGuild(message.getGuild_id())
//                            .memberMap().get(qid).getUser().getAvatar();
//                    MessagePacket packet = new MessagePacket()
//                            .setContent("<@!" + message.getAuthor().getId() + "> 功能失效,尽量修复中..")
//                            //这里上传图片的网址必须是经过备案的域名下的文件
//                            .setImage(avatar)
//                            .setReplyId(message.getId());
//                    message.send(packet);
//                }
            }
        });

    }


    public static String w1(String s0) {
        try {
            Document document = null;
            try {
                document = Jsoup.connect("https://xiaobapi.top/api/xb/api/xiaoai.php?msg=" + s0).ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return document.body().text();
        } catch (Exception e) {
            e.printStackTrace();
            return "???";
        }
    }

    public static String w0(String s0) {
        try {
            Document document = null;
            try {
                document = Jsoup.connect("https://xiaoapi.cn/API/zs_tq.php?type=cytq&msg=" + s0 + "&num=20&n=1").ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json = document.body().text();
            JSONObject jo = JSON.parseObject(json);
            return jo.getString("data");
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
