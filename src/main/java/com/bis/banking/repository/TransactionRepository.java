package com.bis.banking.repository;

import com.bis.banking.repository.po.TransactionPO;
import com.bis.banking.repository.po.TransactionStatusPO;

/**
 * The interface Transaction repository.
 */
public interface TransactionRepository {
    /**
     * Create transaction.
     *
     * @param transactionPO the transaction po
     */
    void createTransaction(TransactionPO transactionPO);

    /**
     * Update status.
     *
     * @param transactionNumber the transaction number
     * @param processing        the processing
     */
    void updateStatus(String transactionNumber, TransactionStatusPO processing);
}
