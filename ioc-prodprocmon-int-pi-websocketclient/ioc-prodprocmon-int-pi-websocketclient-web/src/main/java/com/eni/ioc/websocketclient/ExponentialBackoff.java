package com.eni.ioc.websocketclient;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.ApplicationMain;

public class ExponentialBackoff {
    private final int MAX_SHIFT = 13;
    
    private final Random random = new Random();
    private final long baseSleepTimeMs;

    private final int maxAttempts;
    private int attempts;
    private static final Logger logger = LoggerFactory.getLogger(ExponentialBackoff.class);

    public ExponentialBackoff(long baseSleepTimeMs, int maxAttempts) {
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
    }

    public boolean allowRetry() {
        if (maxAttempts == -1 || attempts < maxAttempts) {
            try {
            	long sleepTime = getSleepTimeMs();
            	logger.info(sleepTime+"ms sleep");
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            attempts++;
            return true;
        }
        return false;
    }

   
    public long getSleepTimeMs() {
        int attempt = this.getAttemptCount() + 1;
        logger.debug("number of attempts " + attempt);
        if (attempt > MAX_SHIFT) {
            attempt = MAX_SHIFT;
        }
        int rand = random.nextInt(1 << attempt); //8192
        logger.debug("number random: " + rand);
        long sleepTime = baseSleepTimeMs * Math.max(1, rand);
        return sleepTime;
    }
    
    public void begin() {
        this.attempts = 0;
    }
    
    public int getAttemptCount() {
        return attempts;
    }

    public long getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }
    
    public int getMaxAttemptCount() {
        return maxAttempts;
    }


    public String toString() {
        return "";//ToStringBuilder.reflectionToString(this);
    }

	
}