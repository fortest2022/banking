package com.bis.banking.repository.po;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * The type Transaction po.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionPO {

    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private TransactionStatusPO status;
    private TransactionTypePO type;
    private String transactionNumber;

    /**
     * Gets transaction number.
     *
     * @return the transaction number
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Sets transaction number.
     *
     * @param transactionNumber the transaction number
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public TransactionTypePO getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(TransactionTypePO type) {
        this.type = type;
    }

    /**
     * Gets source account number.
     *
     * @return the source account number
     */
    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    /**
     * Sets source account number.
     *
     * @param sourceAccountNumber the source account number
     */
    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    /**
     * Gets target account number.
     *
     * @return the target account number
     */
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    /**
     * Sets target account number.
     *
     * @param targetAccountNumber the target account number
     */
    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TransactionStatusPO getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TransactionStatusPO status) {
        this.status = status;
    }

    /**
     * Gets last updated.
     *
     * @return the last updated
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets last updated.
     *
     * @param lastUpdated the last updated
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * The type Fields.
     */
    public static class Fields {
        /**
         * The constant FIELD_ID.
         */
        public static final String FIELD_ID = "id";
        /**
         * The constant FIELD_FROM_ACCOUNT_NUMBER.
         */
        public static final String FIELD_FROM_ACCOUNT_NUMBER = "from_account_number";
        /**
         * The constant FIELD_TO_ACCOUNT_NUMBER.
         */
        public static final String FIELD_TO_ACCOUNT_NUMBER = "to_account_number";
        /**
         * The constant FIELD_AMOUNT.
         */
        public static final String FIELD_AMOUNT = "amount";
        /**
         * The constant FIELD_CREATION.
         */
        public static final String FIELD_CREATION = "creation_time";
        /**
         * The constant FIELD_TYPE.
         */
        public static final String FIELD_TYPE = "type";
        /**
         * The constant FIELD_STATUS.
         */
        public static final String FIELD_STATUS = "status";
        /**
         * The constant FIELD_TRANSACTION_NUMBER.
         */
        public static final String FIELD_TRANSACTION_NUMBER = "transaction_number";
    }
}
