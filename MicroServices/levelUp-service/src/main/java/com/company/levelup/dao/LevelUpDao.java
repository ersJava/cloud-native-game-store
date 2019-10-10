package com.company.levelup.dao;

import com.company.levelup.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

    // Basic CRUD
    LevelUp addLevelUp(LevelUp levelUp);

    LevelUp getLevelUp(int id);

    List<LevelUp> getAllLevelUps();

    void deleteLevelUp(int id);

    void updateLevelUp(LevelUp levelUp);

    // Custom Method
    int getPointsByCustomerId(int customerId);

    LevelUp getLevelUpByCustomerId(int customerId);

}
