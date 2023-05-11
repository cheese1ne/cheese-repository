package com.cheese.db.core.exception;

/**
 * MappedStatement 无法获取时抛出
 *
 * @author sobann
 */
public class StatementNotFoundException extends DevBaseException {

    public StatementNotFoundException(String statementId) {
        super("No MappedStatement witch id is " +statementId + " has been found");
    }
}
