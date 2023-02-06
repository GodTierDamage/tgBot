package gdt.projects.tgbot.repository;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StatsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int getCountOfIncomesThatGraterThan(BigDecimal amount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("amount", amount);
        return namedParameterJdbcTemplate.queryForObject("SELECT count(*) FROM INCOME WHERE INCOME > :amount", parameters, new StatsRowMapper());
    }

    private static final class StatsRowMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("COUNT");
        }
    }

    public List<Income> getIncomesGreaterThen(Long amount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("amount", amount);
        return namedParameterJdbcTemplate.query("SELECT * FROM INCOME WHERE INCOME > :amount", parameters, new IncomesRowMapper());
    }

    private static final class IncomesRowMapper implements RowMapper<Income> {

        @Override
        public Income mapRow(ResultSet resultSet, int i) throws SQLException {
            Income income = new Income();
            Long id = resultSet.getLong("id");
            Long chatId = resultSet.getLong("CHAT_ID");
            BigDecimal inc = resultSet.getBigDecimal("INCOME");
            Timestamp ts = resultSet.getTimestamp("TIME_OF_OPERATION");
            income.setId(id);
            income.setChatId(chatId);
            income.setIncome(inc);
            income.setTimeOfOperation(ts);
            return income;
        }
    }

    public List<Spend> getSpendsGreaterThen(Long amount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("amount", amount);
        return namedParameterJdbcTemplate.query("SELECT * FROM SPEND WHERE SPEND > :amount", parameters, new SpendsRowMapper());
    }

    private static final class SpendsRowMapper implements RowMapper<Spend> {

        @Override
        public Spend mapRow(ResultSet resultSet, int i) throws SQLException {
            Spend spend = new Spend();
            Long id = resultSet.getLong("id");
            Long chatId = resultSet.getLong("CHAT_ID");
            BigDecimal spnd = resultSet.getBigDecimal("SPEND");
            Timestamp ts = resultSet.getTimestamp("TIME_OF_OPERATION");
            spend.setId(id);
            spend.setChat_id(chatId);
            spend.setSpend(spnd);
            spend.setTimeOfOperation(ts);
            return spend;
        }
    }
}