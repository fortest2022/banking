package com.bis.banking.service;

import com.bis.banking.dto.TransactionDTO;
import com.bis.banking.dto.TransactionStatusDTO;
import com.bis.banking.dto.TransactionTypeDTO;
import com.bis.banking.repository.AccountRepository;
import com.bis.banking.repository.TransactionRepository;
import com.bis.banking.repository.po.AccountPO;
import com.bis.banking.repository.po.TransactionPO;
import com.bis.banking.repository.po.TransactionStatusPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository mockTransactionRepository;
    @Mock
    private AccountRepository mockAccountRepository;

    private TransactionService transactionServiceUnderTest;

    @BeforeEach
    void setUp() {
        transactionServiceUnderTest = new TransactionService(mockTransactionRepository, mockAccountRepository);
    }

    @Test
    void testCreateTransaction() {
        // Setup
        final TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setType(TransactionTypeDTO.INTERNAL_TRANSFER);
        transactionDTO.setSourceAccountNumber("sourceAccountNumber");
        transactionDTO.setTargetAccountNumber("targetAccountNumber");
        transactionDTO.setAmount(new BigDecimal("1000.00"));
        transactionDTO.setCurrency(Currency.getInstance("USD"));
        // Configure AccountRepository.getBalance(...).
        final AccountPO accountTarget = new AccountPO();
        accountTarget.setId(0);
        accountTarget.setAccountNumber("accountNumber");
        accountTarget.setOwnerName("ownerName");
        accountTarget.setBalance(new BigDecimal("9999.00"));
        accountTarget.setCurrency(Currency.getInstance("USD"));
        when(mockAccountRepository.getBalance("targetAccountNumber")).thenReturn(accountTarget);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            accountTarget.setBalance((BigDecimal)args[1]);
            return null;
        }).when(mockAccountRepository).updateBalance(eq("targetAccountNumber"), any(BigDecimal.class));

        final AccountPO accountSource = new AccountPO();
        accountSource.setId(0);
        accountSource.setAccountNumber("accountNumber");
        accountSource.setOwnerName("ownerName");
        accountSource.setBalance(new BigDecimal("9999.00"));
        accountSource.setCurrency(Currency.getInstance("USD"));
        when(mockAccountRepository.getBalance("sourceAccountNumber")).thenReturn(accountSource);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            accountSource.setBalance((BigDecimal)args[1]);
            return null;
        }).when(mockAccountRepository).updateBalance(eq("sourceAccountNumber"), any(BigDecimal.class));

        // Run the test
        transactionServiceUnderTest.createTransaction(transactionDTO);

        // Verify the results

        assertNotNull(accountSource.getBalance());
        assertNotNull(accountTarget.getBalance());
        assertEquals(new BigDecimal("8999.00"), accountSource.getBalance());
        assertEquals(new BigDecimal("10999.00"), accountTarget.getBalance());
       }
}
