package io.ylab.walletservice.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.mappers.AccountMapper;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.service.api.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for operations with account
 */
@RestController
@AllArgsConstructor
@Loggable
@Tag(name = "Accounts", description = "Opperations with accounts")
public class AccountController {

    /**
     * Define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * Define a field with a type {@link AccountMapper} for further interaction
     */
    private final AccountMapper accountMapper;

    /**
     * Called by the server to allow a controller to handle a GET request.
     * @param userId for finding account for exact user
     * @return status and {@link AccountDTO}
     */
    @GetMapping("/users/account/{userId}")
    @Operation(summary = "Get account")
    @Parameter(description = "Get account by Id", content = {@Content(mediaType = "application/json")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Not authorized",
                    content = @Content)
    })
    public ResponseEntity<AccountDTO> find(
            @PathVariable Long userId
    ) {
        AccountEntity accountEntity = accountService.getByUser(userId);
        AccountDTO accountDTO = accountMapper.toDTO(accountEntity);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

}
