package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.dao.factory.UserDaoFactory;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IUserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for getting user with token
 */
@WebServlet(urlPatterns = "/users/me")
@Loggable
public class UserMeServlet extends HttpServlet {

    /**
     * Define a field with a type {@link IUserService} for further aggregation
     */
    private final IUserService userService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructor for initializing classes from field
     */
    public UserMeServlet() {
        this.userService = new UserService(UserDaoFactory.getInstance());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {

            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("user");

            if(userService.get(userDTO.getId()) == null) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write(objectMapper.writeValueAsString(userDTO));
            }

        } catch(NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
