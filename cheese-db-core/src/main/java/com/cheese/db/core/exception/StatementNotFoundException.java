package com.cheese.db.core.exception;

/**
 * @author sobann
 */
public class StatementNotFoundException extends DevBaseException {

    public StatementNotFoundException(String statementId) {
        super("No MappedStatement witch id is " +statementId + " has been found");
    }
}
