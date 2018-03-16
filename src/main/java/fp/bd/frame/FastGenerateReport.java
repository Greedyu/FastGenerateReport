package fp.bd.frame;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import fp.bd.frame.common.MySQLConfig;
import fp.bd.frame.common.PrestoConfig;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@Import({MySQLConfig.class, PrestoConfig.class})
public class FastGenerateReport {

    public static void main(String[] args) {
        //监听控制台日志
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusManager statusManager = lc.getStatusManager();
        OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
        statusManager.add(onConsoleListener);
        SpringApplication.run(FastGenerateReport.class, args);
    }

}
