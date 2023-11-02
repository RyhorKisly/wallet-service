package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.dao.entity.AccountEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for Accounts
 */
@Mapper
public interface AccountMapper {

    /**
     * Convert accountEntity to accountDTO
     * @param accountEntity account
     * @return AccountDTO
     */
    AccountDTO toDTO(AccountEntity accountEntity);
}
