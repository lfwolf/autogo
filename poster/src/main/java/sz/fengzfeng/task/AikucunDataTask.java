package sz.fengzfeng.task;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//@Component
public class AikucunDataTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AikucunDataTask.class);
	private static final long SECOND = 1000;
	private static final long MINUTE = 1000*60;
	
    @Scheduled(fixedRate = 3 * MINUTE)
    public void task1() {
        LOGGER.info("当前时间：{}\t\t任务：fixedRate task，每3秒执行一次", System.currentTimeMillis());
    }
    
    /**
     * 固定延迟3秒，从前一次任务结束开始计算，延迟3秒执行
     */
    @Scheduled(fixedDelay = 3 * SECOND)
    public void task3(){
        //do something
    }

    /**
     * cron表达式，每5秒执行
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void task2() {
        LOGGER.info("当前时间：{}\t\t任务：cron task，每5秒执行一次", System.currentTimeMillis());
    }


}
