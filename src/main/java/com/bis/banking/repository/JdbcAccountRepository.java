package com.bis.banking.repository;

import com.bis.banking.repository.po.AccountPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Jdbc account repository.
 */
@Repository
public class JdbcAccountRepository implements AccountRepository {
    private static final String SELECT_ALL = "select id,  account_number, owner_name, currency " +
            "from banking.accounts where active = true";
    private static final String INSERT_ACCOUNT = "insert into banking.accounts " +
            "(account_number, owner_name, currency) values (:account_number, :owner_name, :currency)";
    private static final String UPDATE_ACCOUNT_BALANCE = "update banking.accounts set balance = :balance where account_number = :account_number";
    private static final String DEACTIVATE = "update banking.accounts set active = false " +
            "where account_number = :account_number";
    private static final String GET_BALANCE = "select account_number, balance, currency from banking.accounts where account_number = :account_number and active = true";
    private static final String GET_ACCOUNT_INFO = "select id, account_number, owner_name, currency from banking.accounts  where account_number = :account_number and active = true";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Jdbc account repository.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
    public JdbcAccountRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createAccount(AccountPO accountPO) {
        Map<String, Object> params = new HashMap<>();
        params.put(AccountPO.Fields.ACCOUNT_NUMBER_FIELD, accountPO.getAccountNumber());
        params.put(AccountPO.Fields.CURRENCY_FIELD, accountPO.getCurrency().getCurrencyCode());
        params.put(AccountPO.Fields.OWNER_NAME_FIELD, accountPO.getOwnerName());
        jdbcTemplate.update(INSERT_ACCOUNT, params);
    }

    @Override
    public void deactivateAccount(String accountNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put(AccountPO.Fields.ACCOUNT_NUMBER_FIELD, accountNumber);
        jdbcTemplate.update(DEACTIVATE, params);

    }

    @Override
    public List<AccountPO> listAccounts() {
        return jdbcTemplate.query(SELECT_ALL, new AccountMapper());
    }

    @Override
    public AccountPO getBalance(String accountNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put(AccountPO.Fields.ACCOUNT_NUMBER_FIELD, accountNumber);
        return jdbcTemplate.queryForObject(GET_BALANCE, params, new RowMapper<AccountPO>() {
            @Override
            public AccountPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                AccountPO result = new AccountPO();
                result.setAccountNumber(rs.getString(AccountPO.Fields.ACCOUNT_NUMBER_FIELD));
                result.setCurrency(Currency.getInstance(rs.getString(AccountPO.Fields.CURRENCY_FIELD)));
                result.setBalance(rs.getBigDecimal(AccountPO.Fields.BALANCE_FIELD));
                return result;
            }
        });
    }

    @Override
    public AccountPO getAccountInfo(String accountNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put(AccountPO.Fields.ACCOUNT_NUMBER_FIELD, accountNumber);
        return jdbcTemplate.queryForObject(GET_ACCOUNT_INFO, params, new AccountMapper());
    }

    @Override
    public void updateBalance(String accountNumber, BigDecimal balance) {
        Map<String, Object> params = new HashMap<>();
        params.put(AccountPO.Fields.ACCOUNT_NUMBER_FIELD, accountNumber);
        params.put(AccountPO.Fields.BALANCE_FIELD, balance);
        jdbcTemplate.update(UPDATE_ACCOUNT_BALANCE, params);
    }

    private static final class AccountMapper implements RowMapper<AccountPO> {

        @Override
        public AccountPO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AccountPO result = new AccountPO();
            result.setAccountNumber(rs.getString(AccountPO.Fields.ACCOUNT_NUMBER_FIELD));
            result.setCurrency(Currency.getInstance(rs.getString(AccountPO.Fields.CURRENCY_FIELD)));
            result.setId(rs.getInt(AccountPO.Fields.ID_FIELD));
            result.setOwnerName(rs.getString(AccountPO.Fields.OWNER_NAME_FIELD));
            return result;
        }
    }
}
