package com.company.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SequenceGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SEQUENCE_SQL = "select SEQ_CUST.NEXTVAL AS  SEQUENCE from DUAL";

    public String getSequence() {

        Map<String, Object> sequenceMap = jdbcTemplate.queryForMap(SEQUENCE_SQL);
        return String.valueOf(sequenceMap.getOrDefault("SEQUENCE", 1001));
    }


}
