package io.ylab.walletservice.in;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.dto.UserLoginDTO;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class fo interaction between {@link IUserAuthenticationService} and {@link Menu}
 */
@RequiredArgsConstructor
public class AuthenticationGate {

    /**
     * Basic Wrong data message if user entered data incorrectly
     */
    private static final String WRONG_DATA = "The information entered incorrectly. Try again";

    /**
     * Print if user was registered successfully
     */
    private static final String REGISTERED_SUCCESS = "You have successfully registered.";

    /**
     * Print if user fail registration
     */
    private static final String REGISTERED_FAIL = "Fail registration.";

    /**
     * Print that user have to enter param or enter stop
     */
    private static final String ENTER_PARAM = "Enter %s or enter \"stop\" to stop program: ";

    /**
     * Print if ussr logged in successfully
     */
    private static final String GREETING = "Welcome! You have successfully logged in!";

    /**
     * Print if user entered empty data
     */
    private static final String CANT_BE_EMPTY_DATA = "Data can't be empty! Try again!";

    /**
     * param for log in user
     */
    private static final String PASSWORD = "password";

    /**
     * param for log in user
     */
    private static final String LOGIN = "login";

    /**
     * param for stopping app
     */
    private static final String STOP = "stop";

    /**
     * Basic Error message for user
     */
    private static final String ERROR = "Error";

    /**
     * define a field with a type {@link IUserAuthenticationService} for further aggregation
     */
    private final IUserAuthenticationService authenticationService;

    /**
     * Register user in the system. User enter login and password and after that
     * user can log in into the account
     * With registration in up is created Account with number and assigned to registered user
     * @param reader Reads text from a character-input stream
     */
    public void register(BufferedReader reader) {
        String login = enterParam(reader, LOGIN);
        String password = enterParam(reader, PASSWORD);
        UserCreateDTO userCreateDTO = new UserCreateDTO(
                login,
                UserRole.USER,
                password
        );
        UserEntity userEntity = authenticationService.register(userCreateDTO);
        if(userEntity.getId() == null) {
            System.out.println(REGISTERED_FAIL);
            return;
        }
        System.out.println(REGISTERED_SUCCESS);
    }

    /**
     * Authorize user in system entering login and password.
     * @param reader Reads text from a character-input stream
     * @return {@link UserDTO} for farther using in app after authorization
     */
    public UserDTO authorize(BufferedReader reader) {
        String login = enterParam(reader, LOGIN);
        String password = enterParam(reader, PASSWORD);

        UserEntity userEntity = authenticationService.authorize(
                new UserLoginDTO(login, password)
        );
        if(userEntity != null) {
            System.out.println();
            System.out.println(GREETING);
            return convertToDTO(userEntity);
        } else {
            return null;
        }
    }

    /**
     * Used to show which one params user have to enter in console
     * Used to do code more readable.
     * @param reader Reads text from a character-input stream
     * @param param say to method which one param exactly user will enter next
     * @return String of user entered param (login, password or "stop")
     */
    private String enterParam(BufferedReader reader, String param) {
        while (true) {
            try {
                try {
                    System.out.printf(ENTER_PARAM, param);
                    String inParam = reader.readLine();
                    if (inParam.equals(STOP)) {
                        System.exit(0);
                    }
                    if(inParam.isEmpty()) {
                        System.out.println(CANT_BE_EMPTY_DATA);
                    } else {
                        return inParam;
                    }
                } catch(IllegalArgumentException ex) {
                    System.out.println(WRONG_DATA);
                    }
            } catch (IOException e) {
                System.out.println(ERROR);
                System.exit(0);
            }
        }
    }

    /**
     * Convert {@link UserEntity} to {@link UserDTO}
     * @param userEntity used to fulfill {@link UserDTO} params
     * @return {@link UserDTO}
     */
    private UserDTO convertToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setLogin(userEntity.getLogin());
        userDTO.setUserRole(userEntity.getRole());
        return userDTO;
    }

}
