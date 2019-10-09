package com.company.levelup.dao;

import com.company.levelup.model.LevelUp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpJdbcTemplateImplTest {

    @Autowired
    LevelUpDao dao;

    @Before
    public void setUp() throws Exception {

        List<LevelUp> allLevelUp = dao.getAll();
        allLevelUp.forEach(levelUp -> dao.deleteLevelUp(levelUp.getLevelUpId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addGetDeleteLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        levelUp = dao.addLeveUp(levelUp);

        LevelUp fromService = dao.getLevelUp(levelUp.getLevelUpId());
        assertEquals(fromService, levelUp);

        dao.deleteLevelUp(levelUp.getLevelUpId());
        fromService = dao.getLevelUp(levelUp.getLevelUpId());
        assertNull(fromService);
    }

    @Test
    public void getAll() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        levelUp = dao.addLeveUp(levelUp);

        List<LevelUp> list = dao.getAll();
        assertEquals(list.size(), 1);

    }

    @Test
    public void updateLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));
        levelUp = dao.addLeveUp(levelUp);

        levelUp.setCustomerId(10);
        levelUp.setPoints(350);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));
        dao.updateLevelUp(levelUp);

        LevelUp fromService = dao.getLevelUp(levelUp.getLevelUpId());

        assertEquals(fromService, levelUp);

    }

    @Test
    public void getPointsByCustomerId() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));
        dao.addLeveUp(levelUp);

        int pointsByCustomerId = dao.getPointsByCustomerId(10);
        assertEquals(250, pointsByCustomerId);

    }

    @Test
    public void getLevelUpByCustomerId() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));
        dao.addLeveUp(levelUp);

        LevelUp fromService = dao.getLevelUpByCustomerId(10);
        assertEquals(fromService, levelUp);
    }

}