package com.company.levelup.service;

import com.company.levelup.dao.LevelUpDao;
import com.company.levelup.dao.LevelUpJdbcTemplateImpl;
import com.company.levelup.exception.NotFoundException;
import com.company.levelup.model.LevelUp;
import com.company.levelup.viewmodel.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer serviceLayer;

    private LevelUpDao dao;

    @Before
    public void setUp() throws Exception {

        setUpLevelUpDaoMock();

        serviceLayer = new ServiceLayer(dao);
    }

    private void setUpLevelUpDaoMock() {

        dao = mock(LevelUpJdbcTemplateImpl.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(10);
        levelUp1.setPoints(250);
        levelUp1.setMemberDate(LocalDate.of(2019, 8, 21));

        // update Mock data
        LevelUp updatePoints = new LevelUp();
        updatePoints.setLevelUpId(5);
        updatePoints.setCustomerId(20);
        updatePoints.setPoints(200);
        updatePoints.setMemberDate(LocalDate.of(2019, 8, 21));

        // Mock save
        doReturn(levelUp).when(dao).addLevelUp(levelUp1);

        // Mock getAll
        List<LevelUp> list = new ArrayList<>();
        list.add(levelUp);
        doReturn(list).when(dao).getAllLevelUps();

        // Mock getById
        doReturn(levelUp).when(dao).getLevelUp(1);

        // Mock delete
        doNothing().when(dao).deleteLevelUp(21);
        doReturn(null).when(dao).getLevelUp(21);

        // Custom Methods

        // Mock getByCustomerId
        doReturn(levelUp).when(dao).getLevelUpByCustomerId(10);

        // Mock updatePoints
        doNothing().when(dao).updateLevelUp(updatePoints);
        doReturn(updatePoints).when(dao).getLevelUp(5);

        // Mock getPoints
        doReturn(levelUp.getPoints()).when(dao).getPointsByCustomerId(10);

    }

    @Test
    public void saveFindLevelUp() {

        LevelUpViewModel lvm = new LevelUpViewModel();
        lvm.setCustomerId(10);
        lvm.setPoints(250);
        lvm.setMemberDate(LocalDate.of(2019, 8, 21));

        lvm = serviceLayer.saveLevelUp(lvm);

        LevelUpViewModel lvm2 = serviceLayer.findLevelUp(lvm.getLevelUpId());

        assertEquals(lvm2, lvm);

    }

    @Test
    public void findAllLevelUp() {

        List<LevelUpViewModel> list = serviceLayer.findAllLevelUp();
        assertEquals(1, list.size());

    }

    @Test(expected = NotFoundException.class)
    public void removeLevelUp() {

        serviceLayer.removeLevelUp(21);

        LevelUpViewModel lvm = serviceLayer.findLevelUp(21);
    }

    @Test
    public void updatePoints() {

        LevelUpViewModel updatePoints = new LevelUpViewModel();
        updatePoints.setLevelUpId(5);
        updatePoints.setCustomerId(20);
        updatePoints.setPoints(200);
        updatePoints.setMemberDate(LocalDate.of(2019, 8, 21));

        serviceLayer.updatePoints(updatePoints);

        LevelUpViewModel fromService = serviceLayer.findLevelUp(updatePoints.getLevelUpId());

        assertEquals(fromService, updatePoints);

    }

    @Test
    public void getPoints() {

        LevelUpViewModel levelUp = new LevelUpViewModel();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        levelUp = serviceLayer.saveLevelUp(levelUp);

        int pointsLevelUp = levelUp.getPoints();

        assertEquals(pointsLevelUp, 250);

    }

    @Test
    public void getLevelUpByCustomerId() {

        LevelUpViewModel lvm = new LevelUpViewModel();
        lvm.setLevelUpId(1);
        lvm.setCustomerId(10);
        lvm.setPoints(250);
        lvm.setMemberDate(LocalDate.of(2019, 8, 21));
        lvm = serviceLayer.saveLevelUp(lvm);

        LevelUpViewModel lvm2 = serviceLayer.getLevelUpByCustomerId(lvm.getCustomerId());
        assertEquals(lvm2, lvm);

    }
}