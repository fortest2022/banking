package com.bis.banking.converters;

import com.bis.banking.dto.AccountDTO;
import com.bis.banking.repository.po.AccountPO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account po to dto converter.
 */
public class AccountPOToDTOConverter {
    /**
     * Convert list.
     *
     * @param source the source
     * @return the list
     */
    public static List<AccountDTO> convert(List<AccountPO> source) {
        if (source == null) {
            return null;
        }
        List<AccountDTO> target = new ArrayList<>();
        source.forEach(s -> target.add(convert(s)));
        return target;
    }

    /**
     * Convert account dto.
     *
     * @param source the source
     * @return the account dto
     */
    public static AccountDTO convert(AccountPO source) {
        if (source == null) {
            return null;
        }
        AccountDTO target = new AccountDTO();
        target.setAccountNumber(source.getAccountNumber());
        target.setCurrency(source.getCurrency());
        target.setOwnerName(source.getOwnerName());
        target.setBalance(source.getBalance());
        return target;
    }
}
