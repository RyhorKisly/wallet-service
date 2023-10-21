package io.ylab.walletservice;

import io.ylab.walletservice.in.AuthenticationGate;
import io.ylab.walletservice.in.AccountGate;
import io.ylab.walletservice.in.TransactionalGate;
import io.ylab.walletservice.in.Menu;
import io.ylab.walletservice.in.AuditGate;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import io.ylab.walletservice.service.factory.AuditServiceFactory;
import io.ylab.walletservice.service.factory.TransactionServiceFactory;
import io.ylab.walletservice.service.factory.UserAuthenticationServiceFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class for program execution
 * @author Grigoriy Kislyi
 */
public class Main {

    /**
     * Here start point of the program
     * @param args command line values
     */
    public static void main(String[] args) {
        //todo во всех тестах сделать как в тесте юзер дао и потом улучшить сами тесты
        // осталось 3 теста (authentication, Transactional, User services)

        //todo delete methods delete in daos


        //todo добвить документацию

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        AuthenticationGate authenticationGate = new AuthenticationGate(UserAuthenticationServiceFactory.getInstance());
        TransactionalGate transactionalGate = new TransactionalGate(AccountServiceFactory.getInstance(), TransactionServiceFactory.getInstance());
        AccountGate accountGate = new AccountGate(AccountServiceFactory.getInstance());
        AuditGate auditGate = new AuditGate(AuditServiceFactory.getInstance());

        Menu menu = new Menu(authenticationGate, transactionalGate, accountGate, auditGate);
        menu.start(reader);

    }
}