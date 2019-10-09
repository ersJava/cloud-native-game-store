package com.company.levelup.dao;

import com.company.levelup.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpJdbcTemplateImpl implements  LevelUpDao{

    private static final String INSERT_LEVELUP_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?)";

    private static final String SELECT_LEVELUP_SQL =
            "select * from level_up where level_up_id = ?";

    private static final String SELECT_ALL_LEVELUP_OBJ_SQL =
            "select * from level_up";

    private static final String DELETE_LEVELUP_SQL =
            "delete from level_up where level_up_id = ?";

    private static final String UPDATE_LEVELUP_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";

    private static final String SELECT_POINTS_BY_CUSTOMER_ID_SQL =
            "select points from level_up where customer_id = ?";

    private static final String SELECT_LEVELUP_BY_CUSTOMER_ID_SQL =
            "select * from level_up where customer_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LevelUpJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());

        return levelUp;
    }

    @Override
    @Transactional
    public LevelUp addLeveUp(LevelUp levelUp) {

        jdbcTemplate.update(INSERT_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        levelUp.setLevelUpId(id);

        return levelUp;
    }

    @Override
    public LevelUp getLevelUp(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_LEVELUP_SQL, this::mapRowToLevelUp, id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<LevelUp> getAll() {
        return jdbcTemplate.query(SELECT_ALL_LEVELUP_OBJ_SQL, this::mapRowToLevelUp);
    }

    @Override
    public void deleteLevelUp(int id) {
        jdbcTemplate.update(DELETE_LEVELUP_SQL, id);

    }

    @Override
    public void updateLevelUp(LevelUp levelUp) {

        jdbcTemplate.update(UPDATE_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId());
    }

    @Override
    public int getPointsByCustomerId(int customerId) {
        return jdbcTemplate.queryForObject(SELECT_POINTS_BY_CUSTOMER_ID_SQL, new Object[] { customerId }, Integer.class);
    }

    @Override
    public LevelUp getLevelUpByCustomerId(int customerId) {
        return jdbcTemplate.queryForObject(SELECT_LEVELUP_BY_CUSTOMER_ID_SQL, this::mapRowToLevelUp, customerId);
    }
}
