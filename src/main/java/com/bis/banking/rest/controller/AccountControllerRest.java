package com.bis.banking.rest.controller;

import com.bis.banking.converters.AccountDTOToROConverter;
import com.bis.banking.converters.AccountROToDtoConverter;
import com.bis.banking.dto.AccountDTO;
import com.bis.banking.rest.ro.AccountRO;
import com.bis.banking.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Account controller rest.
 */
@RestController
@RequestMapping("api/v1")
@Api

public class AccountControllerRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountControllerRest.class);

    private final AccountService accountService;

    /**
     * Instantiates a new Account controller rest.
     *
     * @param accountService the account service
     */
    @Autowired
    public AccountControllerRest(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create account account ro.
     *
     * @param accountRO the account ro
     * @return the account ro
     */
    @ApiOperation(value = "create_account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @PostMapping(value = "/accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountRO createAccount(
            @RequestBody AccountRO accountRO) {
        AccountDTO accountDTO = accountService.createAccount(AccountROToDtoConverter.convert(accountRO));
        LOGGER.debug("Triggered AccountRestController.accountInput");
        return AccountDTOToROConverter.convert(accountDTO);

    }

    /**
     * Gets balance.
     *
     * @param accountNumber the account number
     * @return the balance
     */
    @ApiOperation(value = "get_balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @GetMapping("/accounts/{accountNumber}")
    public AccountRO getBalance(@PathVariable String accountNumber) {
        return AccountDTOToROConverter.convert(accountService.getBalance(accountNumber));
    }

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     */
    @ApiOperation(value = "list_accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @GetMapping("/accounts")
    public List<AccountRO> getAllAccounts() {

        return AccountDTOToROConverter.convert(accountService.listAccounts());
    }

    /**
     * Deactivate account.
     *
     * @param accountNumber the account number
     */
    @ApiOperation(value = "deactivate_account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @DeleteMapping("/accounts/{accountNumber}")
    public void deactivateAccount(@PathVariable String accountNumber) {
        accountService.deactivateAccount(accountNumber);
    }

    /**
     * Handle validation exceptions map.
     *
     * @param ex the ex
     * @return the map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}

