package com.bis.banking.converters;

import com.bis.banking.dto.AccountDTO;
import com.bis.banking.rest.ro.AccountRO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account dto to ro converter.
 */
public class AccountDTOToROConverter {
    /**
     * Convert list.
     *
     * @param source the source
     * @return the list
     */
    public static List<AccountRO> convert(List<AccountDTO> source) {
        if (source == null) {
            return null;
        }
        List<AccountRO> target = new ArrayList<>();
        source.forEach(s -> target.add(convert(s)));
        return target;
    }

    /**
     * Convert account ro.
     *
     * @param source the source
     * @return the account ro
     */
    public static AccountRO convert(AccountDTO source) {
        if (source == null) {
            return null;
        }
        AccountRO target = new AccountRO();
        target.setAccountNumber(source.getAccountNumber());
        target.setCurrency(source.getCurrency().getCurrencyCode());
        target.setOwnerName(source.getOwnerName());
        target.setBalance(source.getBalance());
        return target;
    }
}
