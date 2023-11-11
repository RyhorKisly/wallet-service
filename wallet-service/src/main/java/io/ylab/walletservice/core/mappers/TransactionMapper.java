package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for Transactions
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Convert entity to dto
     * @param entity transaction
     * @return {@link TransactionDTO}
     */
    TransactionDTO toDTO(TransactionEntity entity);

    /**
     * Convert set of entities to dTOs
     * @param entities transactions
     * @return set of {@link TransactionDTO}
     */
    Set<TransactionDTO> toDTOSet(Set<TransactionEntity> entities);

}
