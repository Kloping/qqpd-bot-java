package io.github.kloping.qqbot.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/** Configures console logging only when the SDK runs outside Spring Boot. */
public final class StandaloneLogging {
    private static final String DEFAULT_PATTERN = "%clr([%.15thread]){blue} %clr(%-32.32logger{48}){magenta} %clr(%d{yyyy-MM-dd HH:mm:ss}){red} %clr(%-5p): %clr(%msg){light_green}%n";
    private static final String PLAIN_PATTERN = "[%thread] %-32.32logger{32} %d{yyyy-MM-dd HH:mm:ss} %-5p: %msg%n";

    private StandaloneLogging() {
    }

    public static void configure() {
        if (isSpringBootPresent()) return;
        if (!(LoggerFactory.getILoggerFactory() instanceof LoggerContext)) return;

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        registerClrConverter(context);
        Logger root = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        root.detachAndStopAllAppenders();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(System.getProperty("qqbot.logging.pattern",
                Boolean.parseBoolean(System.getProperty("qqbot.logging.color", "true")) ? DEFAULT_PATTERN : PLAIN_PATTERN));
        encoder.start();

        ConsoleAppender<ch.qos.logback.classic.spi.ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setTarget("System.out");
        appender.setEncoder(encoder);
        appender.start();

        root.setLevel(Level.toLevel(System.getProperty("qqbot.logging.level", "INFO"), Level.INFO));
        root.addAppender(appender);
    }

    @SuppressWarnings("unchecked")
    private static void registerClrConverter(LoggerContext context) {
        Map<String, String> converters = (Map<String, String>) context.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
        if (converters == null) {
            converters = new HashMap<>();
            context.putObject(CoreConstants.PATTERN_RULE_REGISTRY, converters);
        }
        converters.put("clr", ClrConverter.class.getName());
    }

    private static boolean isSpringBootPresent() {
        try {
            Class.forName("org.springframework.boot.SpringApplication", false, StandaloneLogging.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}
