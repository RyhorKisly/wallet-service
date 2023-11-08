package io.ylab.walletservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Transfer account data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about account")
public class AccountDTO {

    /**
     * Used like ID of account in order to save in storage place and show in menu.
     */
    @Schema(description = "Id of account")
    private Long id;

    /**
     * Current account balance. Used for transfer the original balance value to the storage place.
     */
    @Schema(description = "Balance on account")
    private BigDecimal balance;

    /**
     * Identifies that the account belongs to a specific user.
     */
    @Schema(description = "Id of user")
    private Long userId;

}
