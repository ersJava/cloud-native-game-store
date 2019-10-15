package com.company.levelup.queue;

import com.company.levelup.LevelUpApplication;
import com.company.levelup.service.ServiceLayer;
import com.company.levelup.viewmodel.LevelUpViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    protected ServiceLayer sl;

    @RabbitListener(queues = LevelUpApplication.QUEUE_NAME)
    public void receiveLevelUp(LevelUpViewModel levelUpViewModel) {

        try {
            LevelUpViewModel lvm = new LevelUpViewModel(
                    levelUpViewModel.getLevelUpId(),
                    levelUpViewModel.getCustomerId(),
                    levelUpViewModel.getPoints(),
                    levelUpViewModel.getMemberDate());
            sl.saveLevelUp(lvm);
            System.out.println("Creating the following comment: " +levelUpViewModel.toString());

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }

    }


}
