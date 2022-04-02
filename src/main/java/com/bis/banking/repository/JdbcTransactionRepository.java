package com.bis.banking.repository;

import com.bis.banking.repository.po.TransactionPO;
import com.bis.banking.repository.po.TransactionStatusPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Jdbc transaction repository.
 */
@Repository
public class JdbcTransactionRepository implements TransactionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final static String CREATE_TRANSACTION = "insert into banking.transactions (from_account_number, to_account_number, amount, type, transaction_number) " +
            "values (:from_account_number, :to_account_number, :amount, :type, :transaction_number)";
    private final static String UPDATE_TRANSACTION_STATUS = "update banking.transactions set status = :status where transaction_number = :transaction_number";

    /**
     * Instantiates a new Jdbc transaction repository.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
    public JdbcTransactionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTransaction(TransactionPO transactionPO) {
        Map<String, Object> params = new HashMap<>();
        params.put(TransactionPO.Fields.FIELD_TRANSACTION_NUMBER, transactionPO.getTransactionNumber());
        params.put(TransactionPO.Fields.FIELD_AMOUNT, transactionPO.getAmount());
        params.put(TransactionPO.Fields.FIELD_FROM_ACCOUNT_NUMBER, transactionPO.getSourceAccountNumber());
        params.put(TransactionPO.Fields.FIELD_TO_ACCOUNT_NUMBER, transactionPO.getTargetAccountNumber());
        params.put(TransactionPO.Fields.FIELD_TYPE, transactionPO.getType().name());
        jdbcTemplate.update(CREATE_TRANSACTION, params);
    }

    @Override
    public void updateStatus(String transactionNumber, TransactionStatusPO status) {
        Map<String, Object> params = new HashMap<>();
        params.put(TransactionPO.Fields.FIELD_STATUS, status.name());
        params.put(TransactionPO.Fields.FIELD_TRANSACTION_NUMBER, transactionNumber);
        jdbcTemplate.update(UPDATE_TRANSACTION_STATUS, params);
    }
}
