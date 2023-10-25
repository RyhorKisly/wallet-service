package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.mappers.AccountMapper;
import io.ylab.walletservice.core.mappers.AccountMapperImpl;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for operations with account
 */
@WebServlet(urlPatterns = "/users/account")
@Loggable
public class AccountServlet extends HttpServlet {

    /**
     * Define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Define a field with a type {@link AccountMapper} for further aggregation
     */
    private final AccountMapper accountMapper;

    /**
     * Constructor for initializing classes from field
     */
    public AccountServlet() {
        this.accountService = AccountServiceFactory.getInstance();
        this.objectMapper = new ObjectMapper();
        this.accountMapper = new AccountMapperImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = resp.getWriter();

            Long userId = Long.parseLong(req.getParameter("userId"));

            AccountEntity accountEntity = accountService.getByUser(userId);
            AccountDTO accountDTO = accountMapper.toDTO(accountEntity);

            writer.write(objectMapper.writeValueAsString(accountDTO));

        } catch(NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
