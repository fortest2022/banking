package com.bis.banking.repository.po;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * The type Account po.
 */
public class AccountPO {


    private int id;
    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;
    private Currency currency;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets account number.
     *
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets account number.
     *
     * @param accountNumber the account number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets owner name.
     *
     * @return the owner name
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Sets owner name.
     *
     * @param ownerName the owner name
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
     * The type Fields.
     */
    public static class Fields {
        /**
         * The constant ID_FIELD.
         */
        public static final String ID_FIELD = "id";
        /**
         * The constant ACCOUNT_NUMBER_FIELD.
         */
        public static final String ACCOUNT_NUMBER_FIELD = "account_number";
        /**
         * The constant OWNER_NAME_FIELD.
         */
        public static final String OWNER_NAME_FIELD = "owner_name";
        /**
         * The constant BALANCE_FIELD.
         */
        public static final String BALANCE_FIELD = "balance";
        /**
         * The constant CURRENCY_FIELD.
         */
        public static final String CURRENCY_FIELD = "currency";
    }
}
