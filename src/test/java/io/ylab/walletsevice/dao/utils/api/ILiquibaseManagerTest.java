package io.ylab.walletsevice.dao.utils.api;

/**
 * For using changelogs with liquibase to do generic operation with test db
 */
public interface ILiquibaseManagerTest {

    /**
     * Method for creating schema and tables in test db
     */
    void migrateDbCreate();

    /**
     * Method for dropping all data in all tables in test db
     */
    void migrateDbDrop();
}
