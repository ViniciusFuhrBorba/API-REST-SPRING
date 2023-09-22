package br.com.unisenai.apprest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonRepository {

    @Autowired
    private JdbcTemplate db;

    public List<Person> findAll() {
        String sql = "select * from person";

        return db.query(sql, (resultSet, index) -> {
            return toPerson(resultSet);
        });
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        return Person.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    public Person insert(Person person) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(db)
                .withTableName("person")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", person.getName());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        person.setId(id.intValue());
        return person;
    }

    public Person findById(int id) {
        String sql = "select * from person where id = ?";
        return db.queryForObject(sql, (resultset, index) -> {
            return toPerson(resultset);
        }, id);
    }

    public Person update(Person person) {
        String sql = "update person set name = ? where id = ?";
        int result = db.update(sql, person.getName(), person.getId());
        return result == 1 ? person : null;
    }

    public boolean delete(int id) {
        String sql = "delete from person where id = ?";
        int result = db.update(sql, id);
        return result == 1;
    }

}
