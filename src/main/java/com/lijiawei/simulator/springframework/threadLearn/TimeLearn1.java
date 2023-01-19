package com.lijiawei.simulator.springframework.threadLearn;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Li JiaWei
 * @ClassName: TimeLearn1
 * @Description:  研究一下Timer类内部实现 本质上任务优先级队列 + 线程池
 * @Date: 2023/1/11 16:39
 * @Version: 1.0
 */
@Slf4j
public class TimeLearn1 {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.info("timerTask is executing now!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
}
