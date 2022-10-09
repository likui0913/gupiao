package com.gupiao.xjob;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
public class TestXJob {

    ReentrantLock lock = new ReentrantLock();

    // fixedDelay、cron、fixedRate
    // fixedDelay 该方式最简单,在上一个任务执行完成之后,间隔3秒(因为@Scheduled(fixedDelay = 3 * 1000))后,执行下一个任务
    // 每隔5秒执行一次：*/5 * * * * ?
    // 每隔1分钟执行一次：0 */1 * * * ?
    // 每天23点执行一次：0 0 23 * * ?
    // 每天凌晨1点执行一次：0 0 1 * * ?
    // 每月1号凌晨1点执行一次：0 0 1 1 * ?
    // 每月最后一天23点执行一次：0 0 23 L * ?
    // 每周星期天凌晨1点实行一次：0 0 1 ? * L
    // 在20分、25分、30分执行一次：0 20,25,30 * * * ?

    //3.添加定时任务
    //@Scheduled(cron="0/5 * * * * *")
    private void configureTasks(){
        if(lock.tryLock()){
            try {
                System.out.println("执行静态定时任务时间："+ LocalDateTime.now());
                Thread.sleep(10000000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("加锁失败："+ LocalDateTime.now());
        }

    }

}
