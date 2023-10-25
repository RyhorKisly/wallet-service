package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.core.exceptions.TransactionFailedException;
import io.ylab.walletservice.core.exceptions.WrongAccountIdException;
import io.ylab.walletservice.core.mappers.TransactionSetMapper;
import io.ylab.walletservice.core.mappers.TransactionSetMapperImpl;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.api.ITransactionService;
import io.ylab.walletservice.service.factory.TransactionServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Servlet for operations with transactions
 */
@WebServlet(urlPatterns = "/users/transaction")
@Loggable
public class TransactionServlet extends HttpServlet {

    /**
     * Define a field with a type {@link ITransactionService} for further aggregation
     */
    private final ITransactionService transactionService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Define a field with a type {@link Validator} for further aggregation
     */
    private final Validator validator;

    /**
     * Define a field with a type {@link TransactionSetMapper} for further aggregation
     */
    private final TransactionSetMapper transactionSetMapper;

    /**
     * Constructor for initializing classes from field
     */
    public TransactionServlet() {
        this.transactionService = TransactionServiceFactory.getInstance();
        this.objectMapper = new ObjectMapper();
        this.validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
        this.transactionSetMapper = new TransactionSetMapperImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        try {
            Long accountId = Long.parseLong(req.getParameter("accountId"));

            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("user");

            Set<TransactionEntity> entities = transactionService.get(accountId, userDTO.getId());

                Set<TransactionDTO> dTOs = transactionSetMapper.toDTOSet(entities);
                writer.write(objectMapper.writeValueAsString(dTOs));

        } catch(NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch(NotUniqueException | ValidationException | WrongAccountIdException | TransactionFailedException ex) {
            writer.write(objectMapper.writeValueAsString(ex.getMessage()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        try{
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("user");

            TransactionDTO dto = objectMapper.readValue(req.getInputStream(), TransactionDTO.class);
            validateOrThrow(writer, objectMapper, validator.validate(dto));

            transactionService.create(dto, userDTO.getId());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch(NotUniqueException | ValidationException | WrongAccountIdException | TransactionFailedException ex) {
                writer.write(objectMapper.writeValueAsString(ex.getMessage()));
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
    private void validateOrThrow(PrintWriter writer, ObjectMapper objectMapper, Set<ConstraintViolation<TransactionDTO>> violations) throws JsonProcessingException {
        if(!violations.isEmpty()) {
            for (ConstraintViolation<TransactionDTO> violation : violations) {
                writer.write(objectMapper.writeValueAsString(violation.getMessage()));
            }
            throw new ValidationException();
        }
    }
}
