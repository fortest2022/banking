package com.bis.banking.repository.po;

/**
 * The enum Transaction status po.
 */
public enum TransactionStatusPO {
    /**
     * Created transaction status po.
     */
    CREATED,
    /**
     * Processing transaction status po.
     */
    PROCESSING,
    /**
     * Received by external system transaction status po.
     */
    RECEIVED_BY_EXTERNAL_SYSTEM,
    /**
     * Completed transaction status po.
     */
    COMPLETED,
    /**
     * Declined transaction status po.
     */
    DECLINED
}
