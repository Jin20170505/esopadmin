/**
 *
 */
package com.jeeplus.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>应用服务程序·启动成功<<<<<<<<<<<<<");
    }


}

