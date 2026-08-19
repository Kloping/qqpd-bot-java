package io.github.kloping.qqbot.utils;

import io.github.kloping.spt.interfaces.Logger;
import org.fusesource.jansi.Ansi;
import org.slf4j.LoggerFactory;

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

    private final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger("io.github.kloping.qqbot");

    public static final Color NORMAL_LOW_COLOR = new Color(116, 117, 116, 224);
    public static final Color NORMAL_COLOR = new Color(202, 206, 199, 247);
    public static final Color INFO_COLOR = new Color(24, 220, 85, 247);
    public static final Color DEBUG_COLOR = new Color(234, 213, 103, 247);
    public static final Color ERROR_COLOR = new Color(224, 17, 106, 247);

    /** 默认过滤 Normal，只输出 Info、Debug 和 Error。 */
    private int logLevel = 1;

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
    public synchronized void setOutFile(String path) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ignored) {
            }
            writer = null;
        }
        file = null;
        updd = null;
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
        if (level != -1 && level < logLevel) return;
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
        String consoleLog = out == null ? log : out;
        if (!isSpringBootPresent() || isSlf4jUnavailable()) {
            System.out.println(consoleLog);
            return;
        }
        if (level == -1) slf4jLogger.error(consoleLog);
        else if (level == 2) slf4jLogger.debug(consoleLog);
        else slf4jLogger.info(consoleLog);
        // 控制台统一由宿主 SLF4J/Logback 管理，以便应用配置日志格式。
    }

    private boolean isSlf4jUnavailable() {
        return LoggerFactory.getILoggerFactory().getClass().getName()
                .equals("org.slf4j.helpers.NOPLoggerFactory");
    }

    private boolean isSpringBootPresent() {
        try {
            Class.forName("org.springframework.boot.SpringApplication", false,
                    LoggerImpl.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    /**
     * 设置日志级别：-1 输出全部，0 输出 Normal 及以上，1 默认，2 仅输出 Debug 和 Error。
     */
    public int setLogLevel(int level) {
        if (level < -1 || level > 2) throw new IllegalArgumentException("logLevel must be between -1 and 2");
        return logLevel = level;
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
            if (!f0.exists() && f0.getParentFile() != null) f0.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(f0, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
