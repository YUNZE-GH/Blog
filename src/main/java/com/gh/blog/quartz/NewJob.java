package com.gh.blog.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author gaohan
 * @version 1.0
 * @date 2020/7/21 17:11
 */
public class NewJob implements Job {

    private Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logger.error("New Job执行时间: " + s);
    }
}
