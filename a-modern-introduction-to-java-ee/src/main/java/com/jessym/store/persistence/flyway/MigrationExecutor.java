package com.jessym.store.persistence.flyway;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class MigrationExecutor {

    @Resource(lookup = "java:jboss/datasources/PostgresDS")
    DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).schemas("public").load();
        flyway.migrate();
    }

}
