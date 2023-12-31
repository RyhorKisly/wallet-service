package io.ylab.sharedwalletresource.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer account data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {

    /**
     * Message where we write specific action performed by the user.
     */
    private String text;

    /**
     * Class instance creation time
     */
    private Long dtCreate;
}
