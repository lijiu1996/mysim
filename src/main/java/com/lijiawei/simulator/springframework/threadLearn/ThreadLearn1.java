package com.lijiawei.simulator.springframework.threadLearn;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

/**
 * @author Li JiaWei
 * @ClassName: ThreadLearn1
 * @Description:  线程池异常调研 -- 线程池如果抛出异常的话 则线程池当前异常线程会被移除 然后重新加入一个新线程进入线程池中
 * @Date: 2023/1/11 16:24
 * @Version: 1.0
 */
@Slf4j
public class ThreadLearn1 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTaskExecutor pool = init();
        IntStream.range(1,11).boxed().forEach(i -> {
            pool.execute(() -> makeException("任务"+i,pool));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }

    private static void makeException(String name, ThreadPoolTaskExecutor pool) {
        String printStr = "thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name;
        log.info(printStr);
        log.info(String.valueOf(pool.getPoolSize()));
        throw new RuntimeException("我有大病 要gg了");
    }

    private static ThreadPoolTaskExecutor init() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("test_thread_");
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(1000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadFactory(r ->
             {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.info("!!!! 出了大问题啦，thread={},e={}",t,e.getMessage());
                    }
                });
                return thread;
            }
    );
        executor.initialize();
        return executor;
    }
}
