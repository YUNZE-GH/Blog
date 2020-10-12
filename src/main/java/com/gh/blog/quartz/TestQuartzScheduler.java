package com.gh.blog.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/31 14:57
 */
public class TestQuartzScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(TestQuartzJob.class).withIdentity("job1", "grop1").build();
        // 3、构建Trigger实例,每隔1s执行一次
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger-1", "triggerGroup-1").startNow()
                .withSchedule(simpleScheduleBuilder).build();

        // 4、执行
        scheduler.scheduleJob(jobDetail, trigger1);
        System.out.println("--------scheduler start ! ------------");

        // 睡眠
//        TimeUnit.MINUTES.sleep(1);
//        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }
}
