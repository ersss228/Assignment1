package com.example.sportsystem.api;

import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private final JdbcTemplate jdbcTemplate;

    public ApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/sports")
    public List<Map<String, Object>> listSports() {
        return jdbcTemplate.query(
                "SELECT id, name, isteamsport FROM sport ORDER BY id",
                (rs, rowNum) -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", rs.getInt("id"));
                    m.put("name", rs.getString("name"));
                    m.put("teamSport", rs.getBoolean("isteamsport"));
                    return m;
                }
        );
    }

    @GetMapping("/athletes")
    public List<Map<String, Object>> listAthletes() {
        return jdbcTemplate.query(
                "SELECT id, name, age FROM athlete ORDER BY id",
                (rs, rowNum) -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", rs.getInt("id"));
                    m.put("name", rs.getString("name"));
                    m.put("age", rs.getInt("age"));
                    return m;
                }
        );
    }

    @PostMapping(path = "/athletes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createAthlete(@RequestBody Map<String, Object> body) {
        Object nameObj = body.get("name");
        Object ageObj = body.get("age");
        if (nameObj == null || ageObj == null) {
            throw new IllegalArgumentException("Expected JSON with 'name' and 'age'");
        }
        String name = String.valueOf(nameObj);
        Integer age;
        if (ageObj instanceof Number) {
            age = ((Number) ageObj).intValue();
        } else {
            age = Integer.parseInt(String.valueOf(ageObj));
        }

        Integer id = jdbcTemplate.queryForObject(
                "INSERT INTO athlete(name, age) VALUES (?, ?) RETURNING id",
                Integer.class, name, age
        );

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("age", age);
        return m;
    }

    @GetMapping("/clubs")
    public List<Map<String, Object>> listClubs() {
        return jdbcTemplate.query(
                "SELECT id, name, numberofathletes FROM sportsclub ORDER BY id",
                (rs, rowNum) -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", rs.getInt("id"));
                    m.put("name", rs.getString("name"));
                    m.put("numberOfAthletes", rs.getInt("numberofathletes"));
                    return m;
                }
        );
    }
}
