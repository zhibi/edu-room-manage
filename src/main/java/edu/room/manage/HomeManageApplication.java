

package edu.room.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * SpringBoot启动核心
 *
 * @author 执笔
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
@MapperScan("edu.hrm.mapper")
public class EduHrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduHrmApplication.class, args);
    }

}
