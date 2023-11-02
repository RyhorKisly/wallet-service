package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for set of Transactions
 */
@Mapper(uses = {TransactionMapper.class})
public interface TransactionSetMapper {

    /**
     * Convert set of entities to dTOs
     * @param entities transactions
     * @return SetTransactionDTOs
     */
    Set<TransactionDTO> toDTOSet(Set<TransactionEntity> entities);
}
