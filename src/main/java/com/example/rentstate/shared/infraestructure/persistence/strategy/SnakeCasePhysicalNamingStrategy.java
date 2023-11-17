package com.example.rentstate.shared.infraestructure.persistence.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

public class SnakeCasePhysicalNamingStrategy implements PhysicalNamingStrategy {

    /**
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return toSnakeCase(this.toPlural(identifier));
    }

    /**
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    private Identifier toSnakeCase(final Identifier identifier){
        if(identifier == null){
            return null;
        }
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
        return Identifier.toIdentifier(newName);
    }
    private Identifier toPlural(final Identifier identifier){
        final String newName = pluralize(identifier.getText());
        return Identifier.toIdentifier(newName);
    }
}

