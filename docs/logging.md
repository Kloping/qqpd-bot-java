# 日志配置

SDK 全部日志使用 SLF4J；源码中的日志调用均为类级 `@Slf4j` 生成的 `log.trace`、`log.debug`、`log.info`、`log.warn` 和 `log.error`。

旧版 `LoggerImpl`、`starter.APPLICATION.logger`、`Config.logLevel`、`logToFile` 与 `logFileDir` 已删除，不能再用于配置日志。

## 独立运行

直接运行 SDK 时，会使用可选的 Logback provider，将日志输出到标准输出。默认日志等级为 `INFO`，默认控制台格式如下：

```text
%clr([%thread]){blue} %clr(%-32.32logger{48}){magenta} %clr(%d{yyyy-MM-dd HH:mm:ss}){red} %clr(%-5p): %clr(%msg){light_green}%n
```

可通过 JVM 参数覆盖默认行为：

```text
-Dqqbot.logging.level=DEBUG
-Dqqbot.logging.color=false
-Dqqbot.logging.pattern="[%thread] %-32.32logger{32} %d{yyyy-MM-dd HH:mm:ss} %-5p: %msg%n"
```
等效以下java代码
```java
public class DemoLogSetter{
   public static void main() {
        System.setProperty("qqbot.logging.level", "DEBUG");
        System.setProperty("qqbot.logging.color", "false");
        System.setProperty("qqbot.logging.pattern", "[%thread] %-32.32logger{32} %d{yyyy-MM-dd HH:mm:ss} %-5p: %msg%n");
    }
}

```

`qqbot.logging.level` 支持 `TRACE`、`DEBUG`、`INFO`、`WARN`、`ERROR`、`OFF` 等 Logback 标准等级。设置 `qqbot.logging.color=false` 可禁用 ANSI 颜色。

未指定颜色的 `%clr(...)` 会按级别着色：`ERROR` 红、`WARN` 黄、`INFO` 绿、`DEBUG` 青。自定义 `qqbot.logging.pattern` 时可使用 Spring Boot 兼容的 `%clr(value){color}`。支持 `black`、`red`、`green`、`light_green`、`yellow`、`blue`、`magenta`、`cyan` 与 `white`。

## Spring Boot

检测到 Spring Boot 后，SDK 不会创建或修改 appender，也不会修改日志等级、颜色或输出格式。SDK 的默认 Logback provider 为 Maven 可选依赖，不会传递到 Spring Boot 用户项目。

请使用 Spring Boot 标准配置设置 SDK 日志：

```yaml
logging:
  level:
    io.github.kloping.qqbot: DEBUG
  pattern:
    console: "%clr([%thread]){blue} %clr(%-32.32logger{48}){magenta} %clr(%d{yyyy-MM-dd HH:mm:ss}){red} %clr(%-5p): %clr(%msg){light_green}%n"
```

也可在 `logback-spring.xml` 中配置 `io.github.kloping.qqbot` logger、控制台 appender 和文件 appender。Spring Boot 的配置优先，且不会被 SDK 覆盖。
