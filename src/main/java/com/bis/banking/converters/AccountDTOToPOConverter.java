package com.bis.banking.converters;

import com.bis.banking.dto.AccountDTO;
import com.bis.banking.repository.po.AccountPO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account dto to po converter.
 */
public class AccountDTOToPOConverter {
    /**
     * Convert list.
     *
     * @param source the source
     * @return the list
     */
    public static List<AccountPO> convert(List<AccountDTO> source) {
        if (source == null) {
            return null;
        }
        List<AccountPO> target = new ArrayList<>();
        source.forEach(s -> target.add(convert(s)));
        return target;
    }

    /**
     * Convert account po.
     *
     * @param source the source
     * @return the account po
     */
    public static AccountPO convert(AccountDTO source) {
        if (source == null) {
            return null;
        }
        AccountPO target = new AccountPO();
        target.setAccountNumber(source.getAccountNumber());
        target.setCurrency(source.getCurrency());
        target.setOwnerName(source.getOwnerName());
        return target;
    }
}
