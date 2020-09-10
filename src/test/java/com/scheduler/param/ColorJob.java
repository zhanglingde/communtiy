package com.scheduler.param;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author zhangling
 * @since 2020/9/10 11:00
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ColorJob.class);
    public static final String FAVORITE_COLOR = "favoriteColor";
    public static final String EXECUTION_COUNT = "count";
    private int _counter = 1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String favoriteColor = jobDataMap.getString("favoriteColor");
        int count = jobDataMap.getInt("count");
        JobKey jobKey = context.getJobDetail().getKey();


        int _counter = 1;
        logger.info("ColorJob: " + jobKey + " executing at " + new Date() + "\n" +
                "  favorite color is " + favoriteColor + "\n" +
                "  execution count (from job map) is " + count + "\n" +
                "  execution count (from job member variable) is " + _counter);

        count++;
        jobDataMap.put("count", count);
    }
}
