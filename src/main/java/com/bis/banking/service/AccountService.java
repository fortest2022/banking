package com.bis.banking.service;

import com.bis.banking.converters.AccountDTOToPOConverter;
import com.bis.banking.converters.AccountPOToDTOConverter;
import com.bis.banking.dto.AccountDTO;
import com.bis.banking.repository.AccountRepository;
import com.bis.banking.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Account service.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Instantiates a new Account service.
     *
     * @param accountRepository the account repository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * List accounts list.
     *
     * @return the list
     */
    public List<AccountDTO> listAccounts() {
        return AccountPOToDTOConverter.convert(accountRepository.listAccounts());
    }

    /**
     * Deactivate account.
     *
     * @param accountNumber the account number
     */
    public void deactivateAccount(String accountNumber) {
        accountRepository.deactivateAccount(accountNumber);
    }

    /**
     * Gets balance.
     *
     * @param accountNumber the account number
     * @return the balance
     */
    public AccountDTO getBalance(String accountNumber) {
        return AccountPOToDTOConverter.convert(accountRepository.getBalance(accountNumber));
    }

    /**
     * Create account account dto.
     *
     * @param accountDTO the account dto
     * @return the account dto
     */
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        //generate account number
        accountDTO.setAccountNumber("AK" + CommonUtils.generateNumber());
        accountRepository.createAccount(AccountDTOToPOConverter.convert(accountDTO));
        return AccountPOToDTOConverter.convert(accountRepository.getAccountInfo(accountDTO.getAccountNumber()));
    }

}
