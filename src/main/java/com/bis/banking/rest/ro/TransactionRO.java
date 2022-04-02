package com.bis.banking.rest.ro;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Transaction ro.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRO {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private TransactionStatusRO status;
    private TransactionTypeRO type;
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
    public TransactionTypeRO getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(TransactionTypeRO type) {
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
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TransactionStatusRO getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TransactionStatusRO status) {
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

    @Override
    public String toString() {
        return "TransactionRO{" +
                "sourceAccountNumber='" + sourceAccountNumber + '\'' +
                ", targetAccountNumber='" + targetAccountNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                ", status=" + status +
                ", type=" + type +
                ", transactionNumber='" + transactionNumber + '\'' +
                '}';
    }
}
