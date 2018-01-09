package com.fangda.maintain.springboot;

import com.fangda.maintain.springboot.config.WebMvcConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 基于SpringBoot的服务器运行控制器
 * @author zhangjian559
 *
 */
@RestController  
@SpringBootApplication(scanBasePackages = {"com.fangda.maintain.web.service","com.fangda.maintain.web.controller","com.fangda.maintain"},
		exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ServletComponentScan("com.fangda")
@ImportResource("classpath*:applicationContext.xml")
@Import({WebMvcConfig.class})
public class ServerControlHandler {

private static Logger log = LogManager.getLogger("ServerControlHandler");
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServerControlHandler.class,args);
		log.info("服务器已经启动!");
	}

}
