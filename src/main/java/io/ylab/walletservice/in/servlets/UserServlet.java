package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.core.mappers.UserMapperImpl;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.dao.factory.UserDaoFactory;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IUserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Servlet for operations with user
 */
@WebServlet(urlPatterns = "/users")
@Loggable
public class UserServlet extends HttpServlet {

    /**
     * Define a field with a type {@link IUserService} for further aggregation
     */
    private final IUserService userService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Define a field with a type {@link UserMapper} for further aggregation
     */
    private final UserMapper userMapper;

    /**
     * Define a field with a type {@link Validator} for further aggregation
     */
    private final Validator validator;

    /**
     * Constructor for initializing classes from field
     */
    public UserServlet() {
        this.userService = new UserService(UserDaoFactory.getInstance());
        this.objectMapper = new ObjectMapper();
        this.userMapper = new UserMapperImpl();
        validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = resp.getWriter();

            Long id = Long.parseLong(req.getParameter("id"));

            UserEntity userEntity = userService.get(id);

            if(userEntity == null) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                UserDTO userDTO = userMapper.toDTO(userEntity);
                writer.write(objectMapper.writeValueAsString(userDTO));
            }

        } catch(NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try{
        PrintWriter writer = resp.getWriter();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        UserCreateDTO dto = objectMapper.readValue(req.getInputStream(), UserCreateDTO.class);

        validateOrThrow(writer, objectMapper, validator.validate(dto));

        userService.createByUser(dto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    } catch (InvalidFormatException | NotUniqueException | ValidationException ex) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check if violation empty and if not - write json messages about failures in validation
     * @param writer for writing message if validation failed
     * @param objectMapper for interpret message as json
     * @param violations store information about validation
     * @throws JsonProcessingException if something wrong with writing value with objectMapper
     */
    private void validateOrThrow(PrintWriter writer, ObjectMapper objectMapper, Set<ConstraintViolation<UserCreateDTO>> violations) throws JsonProcessingException {
        if(!violations.isEmpty()) {
            for (ConstraintViolation<UserCreateDTO> violation : violations) {
                writer.write(objectMapper.writeValueAsString(violation.getMessage()));
            }
            throw new ValidationException();
        }
    }

}
