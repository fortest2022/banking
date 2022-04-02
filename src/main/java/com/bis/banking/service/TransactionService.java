package com.bis.banking.service;

import com.bis.banking.converters.TransactionDTOToPOConverter;
import com.bis.banking.dto.TransactionDTO;
import com.bis.banking.repository.AccountRepository;
import com.bis.banking.repository.TransactionRepository;
import com.bis.banking.repository.po.AccountPO;
import com.bis.banking.repository.po.TransactionStatusPO;
import com.bis.banking.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Transaction service.
 */
@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    // This executor is intentionally just single threaded.
    // In real application we should have mechanism to process transactions in correct order
    // in multithreaded environment.
    // Plus mechanism for async communication to external banks\payment systems is needed.
    // For now this executor just pretends to be external system and updates database directly.
    private ExecutorService EXTERNAL_TRANSACTION_PROCESSOR = Executors.newSingleThreadExecutor();

    /**
     * Instantiates a new Transaction service.
     *
     * @param transactionRepository the transaction repository
     * @param accountRepository     the account repository
     */
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Create transaction.
     *
     * @param transactionDTO the transaction dto
     */
    @Transactional
    public void createTransaction(TransactionDTO transactionDTO) {
        transactionDTO.setTransactionNumber("TR" + CommonUtils.generateNumber());
        transactionRepository.createTransaction(TransactionDTOToPOConverter.convert(transactionDTO));
        switch (transactionDTO.getType()) {
            case CASH_DEPOSIT -> processDeposit(transactionDTO);
            case CASH_WITHDRAWAL -> processWithdrawal(transactionDTO);
            case EXTERNAL_TRANSFER -> processExternalTransfer(transactionDTO);
            case INTERNAL_TRANSFER -> processInternalTransfer(transactionDTO);

        }

    }

    private void processInternalTransfer(TransactionDTO transactionDTO) {
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.PROCESSING);
        AccountPO target = accountRepository.getBalance(transactionDTO.getTargetAccountNumber());
        AccountPO source = accountRepository.getBalance(transactionDTO.getSourceAccountNumber());
        BigDecimal updatedSourceBalance = source.getBalance().subtract(transactionDTO.getAmount());
        BigDecimal updatedTargetBalance = target.getBalance().add(transactionDTO.getAmount());
        if (!target.getCurrency().equals(source.getCurrency())) {
            LOGGER.warn("Source {} and target {} accounts have different currency {} and {}"
                    , source.getAccountNumber(), target.getAccountNumber(), source.getCurrency(), target.getCurrency());
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
            return;
        }
        if (updatedSourceBalance.compareTo(new BigDecimal(0)) < 0) {
            LOGGER.warn("Not enough money on {} to complete transaction {}", source.getAccountNumber()
                    , transactionDTO.getTransactionNumber());
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
            return;
        }
        accountRepository.updateBalance(transactionDTO.getSourceAccountNumber(), updatedSourceBalance);
        accountRepository.updateBalance(transactionDTO.getTargetAccountNumber(), updatedTargetBalance);
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.COMPLETED);

        LOGGER.info("Transaction {} for {} {} completed", transactionDTO.getTransactionNumber()
                , transactionDTO.getAmount(), transactionDTO.getCurrency().getCurrencyCode());
    }

    private void processExternalTransfer(TransactionDTO transactionDTO) {
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.PROCESSING);

        EXTERNAL_TRANSACTION_PROCESSOR.submit(() -> {
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.RECEIVED_BY_EXTERNAL_SYSTEM);
            AccountPO source = accountRepository.getBalance(transactionDTO.getSourceAccountNumber());
            BigDecimal updatedSourceBalance = source.getBalance().subtract(transactionDTO.getAmount());
            if (!transactionDTO.getCurrency().equals(source.getCurrency())) {
                LOGGER.warn("Account {} and transaction {} are in different currencies {} and {}"
                        , source.getAccountNumber(), transactionDTO.getTransactionNumber()
                        , source.getCurrency(), transactionDTO.getCurrency());
                transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
                return;
            }
            if (updatedSourceBalance.compareTo(new BigDecimal(0)) < 0) {
                LOGGER.warn("Not enough money on {} to complete transaction {}", source.getAccountNumber()
                        , transactionDTO.getTransactionNumber());
                transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
                return;
            }
            accountRepository.updateBalance(transactionDTO.getSourceAccountNumber(), updatedSourceBalance);
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.COMPLETED);
            try {
                //Just emulating asynchronous comunication with other system
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                LOGGER.error("Interupted exception", e);
            }
            LOGGER.info("Transaction {} for {} {} completed", transactionDTO.getTransactionNumber()
                    , transactionDTO.getAmount(), transactionDTO.getCurrency().getCurrencyCode());
        });
    }

    private void processWithdrawal(TransactionDTO transactionDTO) {
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.PROCESSING);
        AccountPO source = accountRepository.getBalance(transactionDTO.getSourceAccountNumber());
        BigDecimal updatedSourceBalance = source.getBalance().subtract(transactionDTO.getAmount());
        if (!transactionDTO.getCurrency().equals(source.getCurrency())) {
            LOGGER.warn("Account {} and transaction {} are in different currencies {} and {}"
                    , source.getAccountNumber(), transactionDTO.getTransactionNumber()
                    , source.getCurrency(), transactionDTO.getCurrency());
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
            return;
        }
        if (updatedSourceBalance.compareTo(new BigDecimal(0)) < 0) {
            LOGGER.warn("Not enough money on {} to complete transaction {}", source.getAccountNumber()
                    , transactionDTO.getTransactionNumber());
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
            return;
        }
        accountRepository.updateBalance(transactionDTO.getSourceAccountNumber(), updatedSourceBalance);
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.COMPLETED);

        LOGGER.info("Transaction {} for {} {} completed", transactionDTO.getTransactionNumber()
                , transactionDTO.getAmount(), transactionDTO.getCurrency().getCurrencyCode());
    }

    private void processDeposit(TransactionDTO transactionDTO) {
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.PROCESSING);
        AccountPO target = accountRepository.getBalance(transactionDTO.getTargetAccountNumber());
        BigDecimal updatedTargetBalance = target.getBalance().add(transactionDTO.getAmount());
        if (!transactionDTO.getCurrency().equals(target.getCurrency())) {
            LOGGER.warn("Account {} and transaction {} are in different currencies {} and {}"
                    , target.getAccountNumber(), transactionDTO.getTransactionNumber()
                    , target.getCurrency(), transactionDTO.getCurrency());
            transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.DECLINED);
            return;
        }
        accountRepository.updateBalance(transactionDTO.getTargetAccountNumber(), updatedTargetBalance);
        transactionRepository.updateStatus(transactionDTO.getTransactionNumber(), TransactionStatusPO.COMPLETED);

        LOGGER.info("Transaction {} for {} {} completed", transactionDTO.getTransactionNumber()
                , transactionDTO.getAmount(), transactionDTO.getCurrency().getCurrencyCode());
    }
}
