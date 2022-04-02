package com.bis.banking.repository;

import com.bis.banking.repository.po.AccountPO;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Account repository.
 */
public interface AccountRepository {
    /**
     * Create account.
     *
     * @param accountPO the account po
     */
    void createAccount(AccountPO accountPO);

    /**
     * Deactivate account.
     *
     * @param accountNumber the account number
     */
    void deactivateAccount(String accountNumber);

    /**
     * List accounts list.
     *
     * @return the list
     */
    List<AccountPO> listAccounts();

    /**
     * Gets balance.
     *
     * @param accountNumber the account number
     * @return the balance
     */
    AccountPO getBalance(String accountNumber);

    /**
     * Gets account info.
     *
     * @param accountNumber the account number
     * @return the account info
     */
    AccountPO getAccountInfo(String accountNumber);

    /**
     * Update balance.
     *
     * @param sourceAccountNumber  the source account number
     * @param updatedSourceBalance the updated source balance
     */
    void updateBalance(String sourceAccountNumber, BigDecimal updatedSourceBalance);
}
