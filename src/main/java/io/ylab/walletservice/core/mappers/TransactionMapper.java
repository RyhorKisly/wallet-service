package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for Transactions
 */
@Mapper
public interface TransactionMapper {

    /**
     * Convert entity to dto
     * @param entity transaction
     * @return dto
     */
    TransactionDTO toDTO(TransactionEntity entity);

}
