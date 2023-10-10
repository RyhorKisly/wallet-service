package io.ylab.walletservice.in;

import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.service.api.IAccountService;

/**
 * Class fo interaction between {@link IAccountService} and {@link Menu}
 */
public class AccountGate {

    /**
     * define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * Used for passing an instance of a {@link IAccountService} from outside
     * @param accountService passed to the constructor to establish Aggregation
     */
    public AccountGate(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * find entity by number of the account and login of the user
     * @param login find entity by user login
     * @return entity from {@link IAccountService}
     */
    public AccountEntity getAccount(String login) {
        return accountService.get(login);
    }
}
