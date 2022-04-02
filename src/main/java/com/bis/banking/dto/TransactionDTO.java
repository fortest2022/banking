package com.bis.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * The type Transaction dto.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private TransactionStatusDTO status;
    private TransactionTypeDTO type;
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
    public TransactionTypeDTO getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(TransactionTypeDTO type) {
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
    public TransactionStatusDTO getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TransactionStatusDTO status) {
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
}
