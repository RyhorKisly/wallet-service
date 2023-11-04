package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Mapper for Audit
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {

    /**
     * Convert entity to dto
     *
     * @param auditEntity audit
     * @return {@link AuditDTO}
     */
    @Mapping(target = "dtCreate", qualifiedByName = "toLong")
    AuditDTO toDTO(AuditEntity auditEntity);

    /**
     * Convert list audit entity to dto
     *
     * @param entities list of {@link AuditEntity}
     * @return list of {@link AuditDTO}
     */
    List<AuditDTO> toDTOSet(List<AuditEntity> entities);

    /**
     * Method for converting LocalDateTime to Long
     *
     * @param localDateTime for converting
     * @return {@link Long}
     */
    @Named("toLong")
    static Long toLong(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}


