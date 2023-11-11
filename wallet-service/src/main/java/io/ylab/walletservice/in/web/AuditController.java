package io.ylab.walletservice.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.sharedwalletresource.core.dto.AuditDTO;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.mappers.AuditMapper;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.service.api.IAuditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for operations with audits
 */
@RestController
@AllArgsConstructor
@Loggable
@Tag(name = "Audit", description = "Find all audit")
public class AuditController {

    /**
     * Define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;

    /**
     * Define a field with a type {@link AuditMapper} for further interaction
     */
    private final AuditMapper auditMapper;

    /**
     * Called by the server to allow a controller to handle a GET request. Find all audits.
     * @return status and {@link List<AuditDTO>}
     */
    @GetMapping("/users/audit")
    @Operation(summary = "Get audit")
    @Parameter(description = "Get all audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",
                    description = "Not authorized",
                    content = @Content)
    })
    public ResponseEntity<List<AuditDTO>> findAll() {
        List<AuditEntity> auditsEntity = auditService.getAll();
        List<AuditDTO> auditsDTO = auditMapper.toDTOSet(auditsEntity);
        return new ResponseEntity<>(auditsDTO, HttpStatus.OK);

    }

}
