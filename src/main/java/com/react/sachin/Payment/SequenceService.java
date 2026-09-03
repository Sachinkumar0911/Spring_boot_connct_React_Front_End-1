package com.react.sachin.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SequenceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public synchronized String merchantOrderNo(String serial, String descp, String param) {

        String selectQry =
                "SELECT IFNULL(MAX(seq_no),0)+1, " +
                "CONCAT(DATE_FORMAT(NOW(),'%Y%m%d'), LPAD(IFNULL(MAX(seq_no),0)+1,4,0)) " +
                "FROM generate_sequence_no " +
                "WHERE serial = ? AND descp = ? " +
                "AND date = DATE_FORMAT(NOW(),'%Y-%m-%d') AND param = ?";

        int[] seqHolder = new int[1];
        String[] seqNoWithDateHolder = new String[1];

        jdbcTemplate.query(selectQry, rs -> {
            seqHolder[0] = rs.getInt(1);
            seqNoWithDateHolder[0] = descp + rs.getString(2);
        }, serial, descp, param);

        int seqNo = seqHolder[0];
        String seqNoWithDate = seqNoWithDateHolder[0];

        int rowsAffected;

        if (seqNo == 1) {
            String insertQry =
                    "INSERT INTO generate_sequence_no (serial, date, seq_no, descp, param, year, month) " +
                    "VALUES (?, DATE_FORMAT(NOW(),'%Y-%m-%d'), ?, ?, ?, YEAR(NOW()), MONTH(NOW()))";

            rowsAffected = jdbcTemplate.update(insertQry, serial, seqNo, descp, param);
        } else {
            String updateQry =
                    "UPDATE generate_sequence_no SET seq_no = ? " +
                    "WHERE serial = ? AND descp = ? " +
                    "AND date = DATE_FORMAT(NOW(),'%Y-%m-%d') AND param = ?";

            rowsAffected = jdbcTemplate.update(updateQry, seqNo, serial, descp, param);
        }

        if (rowsAffected <= 0) {
            throw new RuntimeException("Failed to generate sequence number — rolling back");
        }

        return seqNoWithDate;
    }
}
