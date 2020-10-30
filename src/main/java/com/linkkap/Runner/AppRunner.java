package com.linkkap.Runner;

import com.linkkap.service.CrackerSev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yeeq
 * @date 2020/10/30
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private CrackerSev crackerSev;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        crackerSev.downLoadFileByUrl();
    }
}
