package com.company.levelup.service;

import com.company.levelup.dao.LevelUpDao;
import com.company.levelup.exception.NotFoundException;
import com.company.levelup.model.LevelUp;
import com.company.levelup.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private LevelUpDao dao;

    @Autowired
    public ServiceLayer(LevelUpDao dao) {
        this.dao = dao;
    }

    private LevelUpViewModel buildViewModel(LevelUp levelUp) {

        LevelUpViewModel lvm = new LevelUpViewModel();
        lvm.setLevelUpId(levelUp.getLevelUpId());
        lvm.setCustomerId(levelUp.getCustomerId());
        lvm.setPoints(levelUp.getPoints());
        lvm.setMemberDate(levelUp.getMemberDate());

        return lvm;
    }

    @Transactional
    public LevelUpViewModel saveLevelUp(LevelUpViewModel lvm) {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(lvm.getCustomerId());
        levelUp.setPoints(lvm.getPoints());
        levelUp.setMemberDate(lvm.getMemberDate());
        levelUp = dao.addLevelUp(levelUp);

        lvm.setLevelUpId(levelUp.getLevelUpId());

        return lvm;
    }

    public List<LevelUpViewModel> findAllLevelUp() {

        List<LevelUp> levelUpList = dao.getAllLevelUps();

        if(levelUpList.size() == 0){
            throw new NotFoundException("Database is Empty");
        }

        List<LevelUpViewModel> lvmList = new ArrayList<>();

        for (LevelUp l : levelUpList) {
            LevelUpViewModel lvm = buildViewModel(l);
            lvmList.add(lvm);
        }

        return lvmList;
    }

    public LevelUpViewModel findLevelUp (int id) {

        LevelUp levelUp = dao.getLevelUp(id);

        if (levelUp == null)
            throw new NotFoundException(String.format("Level Up account could not be retrieved for id %s", id));
        else
            return buildViewModel(levelUp);
    }

    public void removeLevelUp(int id) {

        LevelUp levelUp = dao.getLevelUp(id);

        if (levelUp == null)
            throw new NotFoundException(String.format("Level Up account could not be retrieved for id %s", id));

        dao.deleteLevelUp(id);
    }

     //  ---- Custom Method ----
    // Update points only for Level Up account
    @Transactional
    public void updatePoints(LevelUpViewModel lvm) {

        LevelUpViewModel fromDatabase = findLevelUp(lvm.getLevelUpId());
        fromDatabase.setPoints(lvm.getPoints());

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(fromDatabase.getLevelUpId());
        levelUp.setCustomerId(fromDatabase.getCustomerId());
        levelUp.setPoints(fromDatabase.getPoints());
        levelUp.setMemberDate(fromDatabase.getMemberDate());

        dao.updateLevelUp(levelUp);

    }

    // Get points by Customer Id
    public int getPoints(int customerId) {

        LevelUp levelUp = dao.getLevelUpByCustomerId(customerId);

        if (levelUp == null)
            throw new NotFoundException(String.format("Level Up account could not be retrieved for customer id %s", customerId));
        else
        return dao.getPointsByCustomerId(customerId);

    }

    public LevelUpViewModel getLevelUpByCustomerId(int customerId) {

        LevelUp levelUp = dao.getLevelUpByCustomerId(customerId);

        if (levelUp == null)
            throw new NotFoundException(String.format("Level Up account could not be retrieved for customer id %s", customerId));
        else
            return buildViewModel(levelUp);

    }

}
