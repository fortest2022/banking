package com.bis.banking.converters;

import com.bis.banking.dto.TransactionDTO;
import com.bis.banking.dto.TransactionStatusDTO;
import com.bis.banking.dto.TransactionTypeDTO;
import com.bis.banking.rest.ro.TransactionRO;

import java.util.Currency;

/**
 * The type Transaction ro to dto converter.
 */
public class TransactionROToDtoConverter {
    /**
     * Convert transaction dto.
     *
     * @param source the source
     * @return the transaction dto
     */
    public static TransactionDTO convert(TransactionRO source) {
        if (source == null) {
            return null;
        }
        TransactionDTO target = new TransactionDTO();
        target.setAmount(source.getAmount());
        target.setCurrency(Currency.getInstance(source.getCurrency()));
        if (source.getStatus() != null) {
            target.setStatus(TransactionStatusDTO.valueOf(source.getStatus().name()));
        }
        target.setTargetAccountNumber(source.getTargetAccountNumber());
        if (source.getType() != null) {
            target.setType(TransactionTypeDTO.valueOf(source.getType().name()));
        }
        target.setAmount(source.getAmount());
        target.setSourceAccountNumber(source.getSourceAccountNumber());
        target.setTransactionNumber(source.getTransactionNumber());
        return target;
    }
}
