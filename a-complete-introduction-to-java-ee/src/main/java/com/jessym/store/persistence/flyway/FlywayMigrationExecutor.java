package com.jessym.store.persistence.flyway;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

/**
 * The <code>@TransactionManagement(TransactionManagementType.BEAN)</code> annotation lets Wildfly know
 * that automatic container-wide transaction management should be disabled for this EJB,
 * allowing this bean to execute otherwise "forbidden" SQL queries like "\set AUTOCOMMIT off"
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class FlywayMigrationExecutor {

    @Resource(lookup = "java:jboss/datasources/PostgresDS")
    DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas("public")
                .load();
        flyway.migrate();
    }

}
