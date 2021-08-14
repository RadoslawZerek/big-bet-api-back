package com.radoslawzerek.bigbetbackend.scheduler;

import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import com.radoslawzerek.bigbetbackend.service.BetsReviewer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BetsReviewScheduler {

    private final BetsReviewer betsReviewer;
    private final UserRepository userRepository;

    public BetsReviewScheduler(BetsReviewer betsReviewer, UserRepository userRepository) {
        this.betsReviewer = betsReviewer;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void reviewPendingBets() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            betsReviewer.updateBetsStatus(user.getId());
        }
    }
}
