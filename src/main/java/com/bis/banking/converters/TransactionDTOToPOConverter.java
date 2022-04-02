package com.bis.banking.converters;

import com.bis.banking.dto.TransactionDTO;
import com.bis.banking.repository.po.TransactionPO;
import com.bis.banking.repository.po.TransactionStatusPO;
import com.bis.banking.repository.po.TransactionTypePO;

/**
 * The type Transaction dto to po converter.
 */
public class TransactionDTOToPOConverter {
    /**
     * Convert transaction po.
     *
     * @param source the source
     * @return the transaction po
     */
    public static TransactionPO convert(TransactionDTO source) {
        if (source == null) {
            return null;
        }
        TransactionPO target = new TransactionPO();
        target.setAmount(source.getAmount());
        target.setCurrency(source.getCurrency());
        if (source.getStatus() != null) {
            target.setStatus(TransactionStatusPO.valueOf(source.getStatus().name()));
        }
        target.setTargetAccountNumber(source.getTargetAccountNumber());
        if (source.getType() != null) {
            target.setType(TransactionTypePO.valueOf(source.getType().name()));
        }
        target.setAmount(source.getAmount());
        target.setSourceAccountNumber(source.getSourceAccountNumber());
        target.setTransactionNumber(source.getTransactionNumber());
        return target;
    }
}
