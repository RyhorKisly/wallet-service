package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import io.ylab.walletservice.service.factory.UserAuthenticationServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Servlet for authorize user
 */
@WebServlet(urlPatterns = "/login")
@Loggable
public class AuthorizationServlet extends HttpServlet {

    /**
     * Define a field with a type {@link IUserAuthenticationService} for further aggregation
     */
    private final IUserAuthenticationService authenticationService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Define a field with a type {@link Validator} for further aggregation
     */
    private final Validator validator;

    /**
     * Constructor for initializing classes from field
     */
    public AuthorizationServlet() {
        this.authenticationService = UserAuthenticationServiceFactory.getInstance();
        this.objectMapper = new ObjectMapper();
        validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try{
            PrintWriter writer = resp.getWriter();
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");

            UserAuthenticationDTO dto = objectMapper.readValue(req.getInputStream(), UserAuthenticationDTO.class);
            validateOrThrow(writer, objectMapper, validator.validate(dto));

            String token = authenticationService.authorize(dto);
            resp.setHeader("Authorization", token);
            writer.write("Your BEARER token: " + token);

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
    private void validateOrThrow(PrintWriter writer, ObjectMapper objectMapper, Set<ConstraintViolation<UserAuthenticationDTO>> violations) throws JsonProcessingException {
        if(!violations.isEmpty()) {
            for (ConstraintViolation<UserAuthenticationDTO> violation : violations) {
                writer.write(objectMapper.writeValueAsString(violation.getMessage()));
            }
            throw new ValidationException();
        }
    }
}
