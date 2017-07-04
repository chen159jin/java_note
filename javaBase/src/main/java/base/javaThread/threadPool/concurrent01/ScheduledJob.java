package base.javaThread.threadPool.concurrent01;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class Temp extends Thread {
    public void run() {
        System.out.println("run");
    }
}

public class ScheduledJob {
	
    public static void main(String args[]) throws Exception {
    
    	Temp command = new Temp();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService    定时周期执行指定的任务
        //newScheduledThreadPool(1)   创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
        //(池中所保存的线程数，即使线程是空闲的也包括在内。)
        ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(command, 5, 1, TimeUnit.SECONDS);
        //scheduleWithFixedDelay  创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
        //如果任务的任一执行遇到异常，就会取消后续执行。否则，只能通过执行程序的取消或终止方法来终止该任务。 
        //command - 要执行的任务         initialDelay - 首次执行的延迟时间          delay - 一次执行终止和下一次执行开始之间的延迟         unit - initialDelay 和 delay 参数的时间单位 

    
    }
}