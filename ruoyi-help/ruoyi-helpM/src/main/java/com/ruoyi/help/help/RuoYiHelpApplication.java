package com.ruoyi.help.help;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 老师管理模块
 *
 * @author ruoyi
 */
@EnableDubbo
@SpringBootApplication
public class RuoYiHelpApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RuoYiHelpApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
