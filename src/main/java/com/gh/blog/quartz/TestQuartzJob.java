package com.gh.blog.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/31 14:55
 */
public class TestQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Trigger trigger = jobExecutionContext.getTrigger();
        System.err.println("Start time:" + trigger.getKey().getName() + "<==>" + trigger.getKey().getGroup() + "<==>" + printTime);
    }
}
