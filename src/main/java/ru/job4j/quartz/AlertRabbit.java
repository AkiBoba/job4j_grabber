package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        String period;

        try (InputStream in = AlertRabbit
                .class
                .getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            period = config.getProperty("rabbit.interval");
                Class.forName(config.getProperty("driver-class-name"));
            try (Connection connect = DriverManager.getConnection(
                        config.getProperty("url"),
                        config.getProperty("username"),
                        config.getProperty("password")
                )) {
                    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                    scheduler.start();
                    JobDataMap data = new JobDataMap();
                    data.put("connect", connect);
                    JobDetail job = newJob(Rabbit.class)
                            .usingJobData(data)
                            .build();
                    assert period != null;
                    SimpleScheduleBuilder times = simpleSchedule()
                            .withIntervalInSeconds(Integer.parseInt(period))
                            .repeatForever();
                    Trigger trigger = newTrigger()
                            .startNow()
                            .withSchedule(times)
                            .build();
                    scheduler.scheduleJob(job, trigger);
                    Thread.sleep(10000);
                    scheduler.shutdown();
                } catch (Exception se) {
                    se.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connect = (Connection) context
                    .getJobDetail()
                    .getJobDataMap()
                    .get("connect");
            try (PreparedStatement ps = connect.prepareStatement(
                    "insert into rabbit(created_date) values (?);")) {
                ps.setLong(1, System.currentTimeMillis());
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}