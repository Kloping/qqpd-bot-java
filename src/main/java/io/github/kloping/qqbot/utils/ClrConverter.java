package io.github.kloping.qqbot.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import java.util.Locale;

/** Supports Spring Boot-style %clr(value){color} patterns outside Spring Boot. */
public class ClrConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String color = getFirstOption();
        if (color == null) return levelColor(event);
        switch (color.toLowerCase(Locale.ROOT)) {
            case "black":
                return ANSIConstants.BLACK_FG;
            case "red":
                return ANSIConstants.RED_FG;
            case "green":
                return ANSIConstants.GREEN_FG;
            case "light_green":
            case "light-green":
                return "92";
            case "yellow":
                return ANSIConstants.YELLOW_FG;
            case "blue":
                return ANSIConstants.BLUE_FG;
            case "magenta":
                return ANSIConstants.MAGENTA_FG;
            case "cyan":
                return ANSIConstants.CYAN_FG;
            case "white":
                return ANSIConstants.WHITE_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

    private String levelColor(ILoggingEvent event) {
        switch (event.getLevel().toInt()) {
            case 40000:
                return ANSIConstants.RED_FG;
            case 30000:
                return ANSIConstants.YELLOW_FG;
            case 20000:
                return ANSIConstants.GREEN_FG;
            case 10000:
                return ANSIConstants.CYAN_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
