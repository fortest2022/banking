package com.bis.banking.converters;

import com.bis.banking.dto.AccountDTO;
import com.bis.banking.rest.ro.AccountRO;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * The type Account ro to dto converter.
 */
public class AccountROToDtoConverter {
    /**
     * Convert list.
     *
     * @param source the source
     * @return the list
     */
    public static List<AccountDTO> convert(List<AccountRO> source) {
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
    public static AccountDTO convert(AccountRO source) {
        if (source == null) {
            return null;
        }
        AccountDTO target = new AccountDTO();
        target.setCurrency(Currency.getInstance(source.getCurrency()));
        target.setOwnerName(source.getOwnerName());
        target.setBalance(source.getBalance());
        return target;
    }
}
