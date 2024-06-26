package org.example.todolist.repository;

import org.example.todolist.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ToDoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ToDo> rowMapper = (ResultSet rs, int rowNum) -> {
        return new ToDo(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getBoolean("completionstatus")
        );
    };

    public List<ToDo> findAll() {
        return jdbcTemplate.query("SELECT * FROM ToDos", rowMapper);
    }

    public ToDo findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM ToDos WHERE id = ?", new Object[]{id}, rowMapper);
    }

//    public int save(ToDo ToDo) {
//        return jdbcTemplate.update("INSERT INTO ToDos (description, completionstatus) VALUES (?, ?)",
//                ToDo.getDescription(), ToDo.isCompletionStatus());
//    }

    public ToDo save(ToDo toDo) {
        final String insertSql = "INSERT INTO Todos (description, completionStatus) VALUES (?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, toDo.getDescription());
            ps.setBoolean(2, toDo.isCompletionStatus());
            return ps;
        });

        long newId = jdbcTemplate.queryForObject("SELECT last_insert_rowid()", Integer.class);
        toDo.setId(newId);
        return toDo;
    }

    public int update(Long id, ToDo ToDo) {
        return jdbcTemplate.update("UPDATE ToDos SET description = ?, completionstatus = ? WHERE id = ?",
                ToDo.getDescription(), ToDo.isCompletionStatus(), id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM ToDos WHERE id = ?", id);
    }
}
