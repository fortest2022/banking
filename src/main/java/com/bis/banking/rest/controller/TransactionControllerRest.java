package com.bis.banking.rest.controller;

import com.bis.banking.converters.TransactionROToDtoConverter;
import com.bis.banking.rest.ro.TransactionRO;
import com.bis.banking.service.TransactionService;
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
import java.util.Map;

/**
 * The type Transaction controller rest.
 */
@RestController
@RequestMapping("api/v1")
@Api
public class TransactionControllerRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountControllerRest.class);

    private final TransactionService transactionService;

    /**
     * Instantiates a new Transaction controller rest.
     *
     * @param transactionService the transaction service
     */
    @Autowired
    public TransactionControllerRest(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create transaction.
     *
     * @param transactionRO the transaction ro
     */
    @PostMapping(value = "/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createTransaction(@RequestBody TransactionRO transactionRO) {
        LOGGER.debug("Received transaction from {} to {} with type {}", transactionRO.getSourceAccountNumber()
                , transactionRO.getTargetAccountNumber(), transactionRO.getType());
        transactionService.createTransaction(TransactionROToDtoConverter.convert(transactionRO));

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
