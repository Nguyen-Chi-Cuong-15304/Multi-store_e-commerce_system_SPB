package com.example.projectII.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.projectII.Service.PromotionService;

@Configuration
@EnableScheduling
public class ScheduleTask {
    private final PromotionService promotionService;

    public ScheduleTask(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void applyPromotion() {
        promotionService.applyPromotion();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void endPromotion() {
        promotionService.endPromotion();
    }
}
