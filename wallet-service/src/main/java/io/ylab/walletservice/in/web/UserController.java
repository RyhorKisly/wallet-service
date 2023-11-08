package io.ylab.walletservice.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.BlankBodyFieldsException;
import io.ylab.walletservice.core.exceptions.SizeLengthException;
import io.ylab.walletservice.core.exceptions.InvalidLoginException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for operations with user
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Loggable
@Tag(name = "Users", description = "For operation with users")
public class UserController {

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
     * Define a field with a type {@link IUserService} for further aggregation
     */
    private final IUserService userService;

    /**
     * Define a field with a type {@link UserMapper} for further aggregation
     */
    private final UserMapper userMapper;

    /** Called by the server to allow a controller to handle a GET request. should be accessed just for ADMIN.
     * @param id of user for finding exact entity
     * @return status and {@link UserDTO}
     */
     @GetMapping("/{id}")
     @Operation(summary = "Information about user by Id")
     @Parameter(description = "Id of user")
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
     public ResponseEntity<UserDTO> find(
             @PathVariable Long id
     ) {
         UserEntity userEntity = userService.get(id);
         UserDTO userDTO = userMapper.toDTO(userEntity);
         return new ResponseEntity<>(userDTO, HttpStatus.OK);
     }

    /** Called by the server to allow a controller to handle a GET request of user who has token and exist in db.
     * @return status and {@link UserDTO}
     */
    @GetMapping("/me")
    @Operation(summary = "Information about user with token")
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
    public ResponseEntity<UserDTO> findMe(
            HttpSession session
    ) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserEntity userEntity = userService.get(userDTO.getLogin());
        UserDTO foundUserDto = userMapper.toDTO(userEntity);
        return new ResponseEntity<>(foundUserDto, HttpStatus.OK);
    }

    /**
     * Called by the server to allow a controller to handle a POST request.
     * @param userCreateDTO fore saving user.
     * @return status and {@link UserDTO}
     */
    @PostMapping
    @Operation(summary = "Save user")
    @Parameter(description = "DTO for saving user", content = {@Content(mediaType = "application/json")})
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
    public ResponseEntity<UserDTO> save(
            @RequestBody UserCreateDTO userCreateDTO
    ) {
        validate(userCreateDTO);
        UserEntity userEntity = userService.createByUser(userCreateDTO);
        UserDTO userDTO = userMapper.toDTO(userEntity);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * Validate {@link UserCreateDTO} obtained by parsing the json
     * @param dto for validating
     * @throws BlankBodyFieldsException if dto's field empty or null
     * @throws SizeLengthException if dto's password has wrong length
     * @throws InvalidLoginException if dto's login has structure different from xxx@xxx.xxx
     */
    private void validate(UserCreateDTO dto) {
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
