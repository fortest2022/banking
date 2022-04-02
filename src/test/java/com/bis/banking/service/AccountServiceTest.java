package com.bis.banking.service;

import com.bis.banking.dto.AccountDTO;
import com.bis.banking.repository.AccountRepository;
import com.bis.banking.repository.po.AccountPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository mockAccountRepository;

    private AccountService accountServiceUnderTest;

    @BeforeEach
    void setUp() {
        accountServiceUnderTest = new AccountService(mockAccountRepository);
    }

    @Test
    void testListAccounts() {
        // Setup
        // Configure AccountRepository.listAccounts(...).
        final AccountPO accountPO = new AccountPO();
        accountPO.setId(0);
        accountPO.setAccountNumber("accountNumber");
        accountPO.setOwnerName("ownerName");
        accountPO.setBalance(new BigDecimal("0.00"));
        accountPO.setCurrency(Currency.getInstance("USD"));
        final List<AccountPO> accountPOS = List.of(accountPO);
        when(mockAccountRepository.listAccounts()).thenReturn(accountPOS);

        // Run the test
        final List<AccountDTO> result = accountServiceUnderTest.listAccounts();

        // Verify the results

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testListAccounts_AccountRepositoryReturnsNoItems() {
        // Setup
        when(mockAccountRepository.listAccounts()).thenReturn(Collections.emptyList());

        // Run the test
        final List<AccountDTO> result = accountServiceUnderTest.listAccounts();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetBalance() {
        // Setup
        // Configure AccountRepository.getBalance(...).
        final AccountPO accountPO = new AccountPO();
        accountPO.setId(0);
        accountPO.setAccountNumber("accountNumber");
        accountPO.setOwnerName("ownerName");
        accountPO.setBalance(new BigDecimal("20.00"));
        accountPO.setCurrency(Currency.getInstance("USD"));
        when(mockAccountRepository.getBalance("accountNumber")).thenReturn(accountPO);

        // Run the test
        final AccountDTO result = accountServiceUnderTest.getBalance("accountNumber");

        // Verify the results
        assertNotNull(result.getAccountNumber());
        assertEquals(new BigDecimal("20.00"), result.getBalance());
    }

    @Test
    void testCreateAccount() {
        // Setup
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setOwnerName("ownerName");
        accountDTO.setCurrency(Currency.getInstance("USD"));

        // Configure AccountRepository.getAccountInfo(...).
        final AccountPO accountPO = new AccountPO();
        accountPO.setId(0);
        accountPO.setOwnerName("ownerName");
        accountPO.setCurrency(Currency.getInstance("USD"));
        when(mockAccountRepository.getAccountInfo(any())).thenReturn(accountPO);

        // Run the test
        accountServiceUnderTest.createAccount(accountDTO);

        // Verify the results
        verify(mockAccountRepository).createAccount(any(AccountPO.class));
        assertNotNull(accountDTO.getAccountNumber());
    }
}
