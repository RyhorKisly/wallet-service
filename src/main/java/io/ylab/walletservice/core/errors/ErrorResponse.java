package io.ylab.walletservice.core.errors;

import io.ylab.walletservice.core.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class for collecting error and interaction with WalletExceptionHandler.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * Here we define which type of error try to collect
     */
    private ErrorType logRef;

    /**
     * Here we define message which we want to show to user
     */
    private String message;
}
