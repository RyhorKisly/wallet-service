package io.ylab.walletservice.in.web;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.BlankBodyFieldsException;
import io.ylab.walletservice.core.mappers.TransactionMapper;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.api.ITransactionService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller for operations with transactions
 */
@RestController
@AllArgsConstructor
@Loggable
public class TransactionController {

    /**
     * Message if transactionID null or empty
     */
    private static final String TRANSACTION_ID_MANDATORY = "Transaction id is mandatory!";

    /**
     * Message if accountId null or empty
     */
    private static final String ACCOUNT_ID_MANDATORY = "Account id is mandatory!";

    /**
     * Message if accountId null or empty
     */
    private static final String SUM_OF_TRANSACTION_MANDATORY = "Sum of transactId is mandatory!";

    /**
     * Define a field with a type {@link ITransactionService} for further aggregation
     */
    private final ITransactionService transactionService;

    /**
     * Define a field with a type {@link TransactionMapper} for further aggregation
     */
    private final TransactionMapper transactionMapper;

    /** Called by the server to allow a controller to handle a GET request. should be accessed just for ADMIN.
     * @param session to get token Holder
     * @param accountId of userAccount for finding exact transactions for this user
     * @return status and {@link UserDTO}
     */
    @GetMapping("/users/transaction/{accountId}")
    public ResponseEntity<Set<TransactionDTO>> findAll(
            HttpSession session,
            @PathVariable Long accountId
    ) {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Set<TransactionEntity> entities = transactionService.get(accountId, userDTO.getId());
        Set<TransactionDTO> dTOs = transactionMapper.toDTOSet(entities);

        return new ResponseEntity<>(dTOs, HttpStatus.OK);
    }

    @PostMapping("/users/transaction")
    public ResponseEntity<TransactionDTO> create(
            HttpSession session,
            @RequestBody TransactionDTO dto
    ) {
        validate(dto);
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            TransactionEntity entity = transactionService.create(dto, userDTO.getId());
            TransactionDTO createdDTO = transactionMapper.toDTO(entity);

        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    /**
     * Validate {@link TransactionDTO} obtained by parsing the json
     * @param dto for validating
     * @throws BlankBodyFieldsException if dto's field empty or null
     */
    private void validate(TransactionDTO dto) {
        if(dto.getTransactionId() == null || dto.getTransactionId().isEmpty()) {
            throw new BlankBodyFieldsException(TRANSACTION_ID_MANDATORY);
        } else if(dto.getAccountId() == null) {
            throw new BlankBodyFieldsException(ACCOUNT_ID_MANDATORY);
        } else if(dto.getSumOfTransaction() == null) {
            throw new BlankBodyFieldsException(SUM_OF_TRANSACTION_MANDATORY);

        }
    }

}
