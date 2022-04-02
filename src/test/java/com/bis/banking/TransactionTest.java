package com.bis.banking;

import com.bis.banking.rest.ro.AccountRO;
import com.bis.banking.rest.ro.TransactionRO;
import com.bis.banking.rest.ro.TransactionTypeRO;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

import static junit.framework.TestCase.*;

/**
 * The type Transaction test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionTest {
    /**
     * The constant postgreSQLContainer.
     */
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresTestContainer.getInstance();
    /**
     * The Test rest template.
     */
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int randomServerPort = 0;
    private String account1;
    private String account2;

    /**
     * Create accounts.
     */
    @Test
    @Order(1)
    public void createAccounts() {
        AccountRO sourceAccount = new AccountRO();
        sourceAccount.setCurrency("EUR");
        sourceAccount.setOwnerName("Capt Sparrow");
        ResponseEntity<AccountRO> responseSource = testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", sourceAccount, AccountRO.class);

        this.account2 = responseSource.getBody().getAccountNumber();

        AccountRO targetAccount = new AccountRO();
        targetAccount.setCurrency("EUR");
        targetAccount.setOwnerName("Capt Barbosa");
        ResponseEntity<AccountRO> responseTarget = testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", targetAccount, AccountRO.class);
        this.account1 = responseTarget.getBody().getAccountNumber();
    }

    /**
     * Test deposit cash.
     */
    @Test
    @Order(2)
    public void testDepositCash() {


        //deposit cash
        TransactionRO transactionRO = new TransactionRO();
        transactionRO.setCurrency("EUR");
        transactionRO.setAmount(new BigDecimal(10000));
        transactionRO.setTargetAccountNumber(account1);
        transactionRO.setType(TransactionTypeRO.CASH_DEPOSIT);
        testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/transactions", transactionRO, TransactionRO.class);

        //check balance
        ResponseEntity<AccountRO> balanceResponse = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + account1, AccountRO.class);
        AccountRO balance = balanceResponse.getBody();
        assertNotNull(balance.getAccountNumber());
        assertNotNull(balance.getBalance());
        assertEquals(balance.getOwnerName(), balance.getOwnerName());
        assertTrue(balance.getBalance().compareTo(transactionRO.getAmount()) == 0);
    }

    /**
     * Test withdraw cash.
     */
    @Test
    @Order(3)
    public void testWithdrawCash() {


        //withdraw cash
        TransactionRO transactionRO = new TransactionRO();
        transactionRO.setCurrency("EUR");
        transactionRO.setAmount(new BigDecimal(900));
        transactionRO.setSourceAccountNumber(account1);
        transactionRO.setType(TransactionTypeRO.CASH_WITHDRAWAL);
        testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/transactions", transactionRO, TransactionRO.class);

        //check balance
        ResponseEntity<AccountRO> balanceResponse = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + account1, AccountRO.class);
        AccountRO balance = balanceResponse.getBody();
        assertNotNull(balance.getAccountNumber());
        assertNotNull(balance.getBalance());
        assertEquals(balance.getOwnerName(), balance.getOwnerName());
        assertTrue(balance.getBalance().compareTo(new BigDecimal(9100)) == 0);
    }

    /**
     * Test internal transfer.
     */
    @Test
    @Order(4)
    public void testInternalTransfer() {


        //withdraw cash
        TransactionRO transactionRO = new TransactionRO();
        transactionRO.setCurrency("EUR");
        transactionRO.setAmount(new BigDecimal(1100));
        transactionRO.setSourceAccountNumber(account1);
        transactionRO.setTargetAccountNumber(account2);
        transactionRO.setType(TransactionTypeRO.INTERNAL_TRANSFER);
        testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/transactions", transactionRO, TransactionRO.class);

        //check balance account1
        ResponseEntity<AccountRO> balanceResponse1 = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + account1, AccountRO.class);
        AccountRO balance1 = balanceResponse1.getBody();
        assertNotNull(balance1.getAccountNumber());
        assertNotNull(balance1.getBalance());
        assertTrue(balance1.getBalance().compareTo(new BigDecimal(8000)) == 0);

        //check balance account2
        ResponseEntity<AccountRO> balanceResponse2 = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + account2, AccountRO.class);
        AccountRO balance2 = balanceResponse2.getBody();
        assertNotNull(balance2.getAccountNumber());
        assertNotNull(balance2.getBalance());
        assertTrue(balance2.getBalance().compareTo(new BigDecimal(1100)) == 0);
    }

    /**
     * Test external transfer.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    @Order(5)
    public void testExternalTransfer() throws InterruptedException {


        //withdraw cash
        TransactionRO transactionRO = new TransactionRO();
        transactionRO.setCurrency("EUR");
        transactionRO.setAmount(new BigDecimal(800));
        transactionRO.setSourceAccountNumber(account1);
        transactionRO.setType(TransactionTypeRO.EXTERNAL_TRANSFER);
        testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/transactions", transactionRO, TransactionRO.class);

        //this transaction is asynchronous, so we waiting to be sure that it is processed
        Thread.sleep(10000L);

        //check balance
        ResponseEntity<AccountRO> balanceResponse = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + account1, AccountRO.class);
        AccountRO balance = balanceResponse.getBody();
        assertNotNull(balance.getAccountNumber());
        assertNotNull(balance.getBalance());
        assertEquals(balance.getOwnerName(), balance.getOwnerName());
        assertTrue(balance.getBalance().compareTo(new BigDecimal(7200)) == 0);
    }
}
