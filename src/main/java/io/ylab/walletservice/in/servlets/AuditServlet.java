package io.ylab.walletservice.in.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.mappers.AuditSetMapper;
import io.ylab.walletservice.core.mappers.AuditSetMapperImpl;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.factory.AuditServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Servlet for operations with audits
 */
@WebServlet(urlPatterns = "/users/audit")
@Loggable
public class AuditServlet extends HttpServlet {

    /**
     * Define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper;

    /**
     * Define a field with a type {@link AuditSetMapper} for further aggregation
     */
    private final AuditSetMapper auditSetMapper;

    /**
     * Constructor for initializing classes from field
     */
    public AuditServlet() {
        this.auditService = AuditServiceFactory.getInstance();
        this.objectMapper = new ObjectMapper();
        this.auditSetMapper = new AuditSetMapperImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = resp.getWriter();

            Set<AuditEntity> auditsEntity = auditService.getAll();

            Set<AuditDTO> auditsDTO = auditSetMapper.toDTOSet(auditsEntity);
                writer.write(objectMapper.writeValueAsString(auditsDTO));

        } catch(NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException | IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
