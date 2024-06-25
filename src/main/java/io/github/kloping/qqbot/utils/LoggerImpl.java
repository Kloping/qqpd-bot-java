package io.github.kloping.qqbot.utils;

import io.github.kloping.spt.interfaces.Logger;
import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author github.kloping
 */
public class LoggerImpl implements Logger {
    public static final LoggerImpl INSTANCE = new LoggerImpl();

    public static final Color NORMAL_LOW_COLOR = new Color(116, 117, 116, 224);
    public static final Color NORMAL_COLOR = new Color(202, 206, 199, 247);
    public static final Color INFO_COLOR = new Color(24, 220, 85, 247);
    public static final Color DEBUG_COLOR = new Color(234, 213, 103, 247);
    public static final Color ERROR_COLOR = new Color(224, 17, 106, 247);

    private int logLevel = 0;

    private SimpleDateFormat df = new SimpleDateFormat("MM/dd-HH:mm:ss:SSS");
    private String prefix = "[github.kloping.ST]";

    /**
     * 区分每日 已达到隔日切换日志文件的效果
     */
    public DateFormat difference = new SimpleDateFormat("dd");
    /**
     * 日志文件格式
     */
    public DateFormat dfn = new SimpleDateFormat("/yyyy-MM-dd");
    /**
     * 日志文件路径
     */
    public String logFileDir = "./logs/%s.log";

    private File file;

    private String updd = null;

    public synchronized File getFile() {
        String dd = difference.format(new Date());
        if (updd == null) updd = dd;
        else if (!updd.equals(dd)) {
            step0();
            setWriter(file);
        }
        if (file == null) step0();
        updd = dd;
        return file;
    }

    private void step0() {
        file = new File(String.format(logFileDir, dfn.format(new Date())));
    }

    /**
     * 必须设置为 %s 以替换 日期的字符串格式 默认 "./logs/%s.log"
     * 设置为 null 时不输出日志文件
     *
     * @param path
     */
    @Override
    public void setOutFile(String path) {
        this.logFileDir = path;
    }

    @Override
    public void setFormat(SimpleDateFormat format) {
        df = format;
    }

    @Override
    public void Log(String mess, Integer level) {
        String log = null;
        String out = null;
        try {
            log = "[" + df.format(new Date()) + "]" + "=>" + mess;
            switch (level) {
                case 0:
                    log = "[Normal]" + log;
                    break;
                case 1:
                    log = "[Info]  " + log;
                    break;
                case 2:
                    log = "[Debug] " + log;
                    break;
                case -1:
                    log = "[Error] " + log;
                    break;
                default:
            }
            log = prefix + log;
            out = null;
            if (level == 0) {
                out = Ansi.ansi().fgRgb(NORMAL_COLOR.getRGB()).a(log).reset().toString();
            } else if (level == 1) {
                out = Ansi.ansi().fgRgb(INFO_COLOR.getRGB()).a(log).reset().toString();
            } else if (level == 2) {
                out = Ansi.ansi().fgRgb(DEBUG_COLOR.getRGB()).a(log).reset().toString();
            } else if (level == -1) {
                out = Ansi.ansi().fgRgb(ERROR_COLOR.getRGB()).a(log).reset().toString();
            }
        } catch (Exception e) {
            if (level != -1 && level < logLevel) {
            } else e.printStackTrace();
        }
        if (logFileDir != null) {
            try {
                BufferedWriter writer = getWriter();
                if (writer != null) {
                    try {
                        log = log.replaceAll("\\\u001B\\[38\\;2\\;[0-9]+\\;[0-9]+\\;[0-9]+m", "")
                                .replaceAll("\\\u001B\\[m", "");
                    } catch (Exception e) {
                    }
                    writer.write(log);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (level != -1 && level < logLevel) return;
        System.out.println(out);
    }

    private BufferedWriter writer = null;

    private BufferedWriter getWriter() {
        File f0 = getFile();
        if (f0 != null && writer == null) {
            setWriter(f0);
        }
        return writer;
    }

    private void setWriter(File f0) {
        try {
            if (writer != null) writer.close();
            writer = new BufferedWriter(new FileWriter(f0, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setLogLevel(int level) {
        return logLevel = level;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
