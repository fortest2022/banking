package com.bis.banking;

import com.bis.banking.rest.ro.AccountRO;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import static junit.framework.TestCase.*;

/**
 * The type Accounts integration test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountsIntegrationTest {
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

    /**
     * Test create account.
     */
    @Test
    @Order(1)
    public void testCreateAccount() {
        AccountRO account = new AccountRO();
        account.setCurrency("EUR");
        account.setOwnerName("Capt Sparrow");
        ResponseEntity<AccountRO> response = testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", account, AccountRO.class);
        AccountRO created = response.getBody();
        assertNotNull(created.getAccountNumber());
        assertNull(created.getBalance());
        assertEquals(created.getCurrency(), account.getCurrency());
        assertEquals(created.getOwnerName(), account.getOwnerName());

    }

    /**
     * Test get all accounts.
     */
    @Test
    @Order(2)
    public void testGetAllAccounts() {
        AccountRO account = new AccountRO();
        account.setCurrency("EUR");
        account.setOwnerName("Capt Barbosa");
        testRestTemplate
                .postForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", account, AccountRO.class);
        ResponseEntity<AccountRO[]> response = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", AccountRO[].class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        AccountRO[] accounts = response.getBody();
        assertNotNull(accounts);
        assertEquals(2, accounts.length);
        for (AccountRO created : accounts) {
            assertNotNull(created.getAccountNumber());
        }

    }

    /**
     * Deactivate all accounts.
     */
    @Test
    @Order(3)
    public void deactivateAllAccounts() {
        ResponseEntity<AccountRO[]> responseOld = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", AccountRO[].class);
        assertEquals(responseOld.getStatusCode(), HttpStatus.OK);
        AccountRO[] accountsOld = responseOld.getBody();
        assertNotNull(accountsOld);
        assertEquals(2, accountsOld.length);
        for (AccountRO accountOld : accountsOld) {
            testRestTemplate
                    .delete("http://localhost:" + randomServerPort + "/banking/api/v1/accounts/" + accountOld.getAccountNumber());
        }
        ResponseEntity<AccountRO[]> responseNew = testRestTemplate
                .getForEntity("http://localhost:" + randomServerPort + "/banking/api/v1/accounts", AccountRO[].class);
        assertEquals(responseNew.getStatusCode(), HttpStatus.OK);
        AccountRO[] accountsNew = responseNew.getBody();
        assertNotNull(accountsNew);
        assertEquals(0, accountsNew.length);
    }
    // tests
}
