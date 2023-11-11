package io.ylab.walletservice.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.BlankBodyFieldsException;
import io.ylab.walletservice.core.exceptions.InvalidLoginException;
import io.ylab.walletservice.core.exceptions.SizeLengthException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for authentication operations with user
 */
@RestController
@AllArgsConstructor
@Loggable
@Tag(name = "Authentication", description = "Registration and authorization users")
public class AuthenticationController {

    /**
     * Pattern for validating login like email xxx@xxx.xxx
     */
    private static final String EMAIL_PATTERN = "[^@]+@[^@]+\\.[^@.]+";

    /**
     * Message if entered login has wrong pattern.
     */
    private static final String WRONG_LOGIN_PATTERN = "Login should be like email: xxx@xxx.xxx";

    /**
     * Message if login null or empty
     */
    private static final String LOGIN_MANDATORY = "Login is mandatory!";

    /**
     * Message if password null or empty
     */
    private static final String PASSWORD_MANDATORY = "Passowrd is mandatory!";

    /**
     * Message if password length is wrong
     */
    private static final String WRONG_PASSWORD_LENGTH = "Password should be more than 4 or less than 20 symbols";

    /**
     * Define a field with a type {@link IUserAuthenticationService} for further aggregation
     */
    private final IUserAuthenticationService authenticationService;

    /**
     * Define a field with a type {@link UserMapper} for further aggregation
     */
    private final UserMapper userMapper;

    /** Called by the server to allow a controller to handle a POST request to register user.
     * @return status and {@link UserDTO}
     */
    @PostMapping("/register")
    @GetMapping("/users/transaction/{accountId}")
    @Operation(summary = "Register user")
    @Parameter(description = "Registration user with login and password", content = {@Content(mediaType = "application/json")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Not authorized",
                    content = @Content)
    })
    public ResponseEntity<UserDTO> register(
            @RequestBody UserAuthenticationDTO dto
    ) {
        validate(dto);
            UserEntity userEntity = authenticationService.register(dto);
            UserDTO userDTO = userMapper.toDTO(userEntity);

            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    /** Called by the server to allow a controller to handle a POST request to authorize user.
     * @return status and {@link UserDTO}
     */
    @PostMapping("/login")
    @Operation(summary = "Authorize user")
    @Parameter(description = "Authorize user with login and password", content = {@Content(mediaType = "application/json")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Authorized",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Not authorized",
                    content = @Content)
    })
    public ResponseEntity<UserDTO> authorize(
            @RequestBody UserAuthenticationDTO dto
    ) {
        validate(dto);
        return ResponseEntity.ok()
                .header("Authorization", authenticationService.authorize(dto)).build();
    }

    /**
     * Validate {@link UserAuthenticationDTO} obtained by parsing the json
     * @param dto for validating
     * @throws BlankBodyFieldsException if dto's field empty or null
     * @throws SizeLengthException if dto's password has wrong length
     * @throws InvalidLoginException if dto's login has structure different from xxx@xxx.xxx
     */
    private void validate(UserAuthenticationDTO dto) {
        if(dto.getLogin() == null || dto.getLogin().isEmpty()) {
            throw new BlankBodyFieldsException(LOGIN_MANDATORY);
        } else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new BlankBodyFieldsException(PASSWORD_MANDATORY);
        } else if (dto.getPassword().length() <= 4 || dto.getPassword().length() >= 20) {
            throw new SizeLengthException(WRONG_PASSWORD_LENGTH);
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(dto.getLogin());
        if(!matcher.matches()) {
            throw new InvalidLoginException(WRONG_LOGIN_PATTERN);
        }
    }
}
