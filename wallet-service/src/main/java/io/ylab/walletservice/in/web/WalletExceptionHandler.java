package io.ylab.walletservice.in.web;

import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.enums.ErrorType;
import io.ylab.walletservice.core.errors.ErrorResponse;
import io.ylab.walletservice.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class for handling exceptions and showing to user exact message and status which we want to show.
 */
@Loggable
@RestControllerAdvice
public class WalletExceptionHandler {

    /**
     * Basic error response if exception haven't cached and have status 500
     */
    private static final String SERVER_ERROR = "Internal server Error. Please, contact support!";

    /**
     * Exceptions in this method cached and have messages about error.
     * This method handle exceptions which throws when user did mistake in request or entered wrong data
     * @param ex exception with data about error
     * @return {@link ErrorResponse} with message and 400 status
     */
    @ExceptionHandler({
            BlankBodyFieldsException.class,
            SizeLengthException.class,
            InvalidLoginException.class,
            NotUniqueException.class,
            NotExistUserException.class,
            WrongAccountIdException.class
    })
    public ResponseEntity<?> handleInvalidArgument(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setLogRef(ErrorType.ERROR);
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exceptions in this method cached and have messages about error.
     * This method handle exceptions which throws when actions occurred through no fault of the user
     * @param ex exception with data about error
     * @return {@link ErrorResponse} with message and 500 status
     */
    @ExceptionHandler({
            TransactionFailedException.class
    })
    public ResponseEntity<?> innerCachedErrors(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setLogRef(ErrorType.ERROR);
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    /**
//     * Exceptions in this method haven't cached in app, and we will write common message to user and status 500
//     * @param ex exception with data about error
//     * @return {@link ErrorResponse} with message and 500 status
//     */
//    @ExceptionHandler({
//            Exception.class
//    })
//    public ResponseEntity<?> handleInnerError(Exception ex) {
//        ErrorResponse response = new ErrorResponse(
//                ErrorType.ERROR,
//                SERVER_ERROR
//        );
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}